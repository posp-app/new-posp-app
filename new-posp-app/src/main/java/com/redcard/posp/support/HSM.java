package com.redcard.posp.support;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.TypeConvert;

public class HSM {
    private static final Logger logger = LoggerFactory.getLogger(HSM.class);

    private ByteArrayOutputStream _ByteArrayBuffer = new ByteArrayOutputStream();

    private Socket socket = null;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;

    private static String masterIp = "";

    private static int masterPort = 0;

    private static String slaveIp = "";

    private static int slavePort = 0;

    private static String localEncZPK = "";

    public HSM() {
    }

    public HSM(String masterIp, int masterPort, String slaveIp, int slavePort, String localEncZPK) {
        HSM.masterIp = masterIp;
        HSM.masterPort = masterPort;
        HSM.slaveIp = slaveIp;
        HSM.slavePort = slavePort;
        HSM.localEncZPK = localEncZPK;
    }

    public HSM(String masterIp, int masterPort, String slaveIp, int slavePort) {
        this(masterIp, masterPort, slaveIp, slavePort, "");
    }

    public String getMasterIp() {
        return masterIp;
    }

    public void setMasterIp(String masterIp) {
        HSM.masterIp = masterIp;
    }

    public int getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(int masterPort) {
        HSM.masterPort = masterPort;
    }

    public String getSlaveIp() {
        return slaveIp;
    }

    public void setSlaveIp(String slaveIp) {
        HSM.slaveIp = slaveIp;
    }

    public int getSlavePort() {
        return slavePort;
    }

    public void setSlavePort(int slavePort) {
        HSM.slavePort = slavePort;
    }

    public String getLocalEncZPK() {
        return localEncZPK;
    }

    public void setLocalEncZPK(String localEncZPK) {
        HSM.localEncZPK = localEncZPK;
    }

    public void connectHSM() throws IOException {
        try {
            if (masterIp.length() == 0 || masterPort == 0) {
                return;
            }
            socket = new Socket(masterIp, masterPort);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Connect master {}:{} HSM failure", masterIp, masterPort);
            if (slaveIp.length() > 0 && slavePort > 0) {
                try {
                    socket = new Socket(slaveIp, slavePort);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    logger.error("Connect slave {}:{} HSM failure", slaveIp, slavePort);
                    throw e;
                }
            }
            throw e;
        }
        if (socket != null) {
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
        }
    }

    public void closeHSM() throws IOException {
        if (this.outputStream != null) this.outputStream.close();
        if (this.inputStream != null) this.inputStream.close();
        if (this.socket != null) this.socket.close();
    }

    public void send(DataOutputStream output, byte[] bBuffer, int nLength)
            throws IOException {
        output.flush();
        output.write(bBuffer, 0, nLength);
        output.flush();
    }

    public byte[] receive(DataInputStream input)
            throws IOException {
        int nLen = 0;
        byte[] bBufferHeader = new byte[2];
        nLen = input.read(bBufferHeader);
        if (nLen <= 0) return null;

        this._ByteArrayBuffer.reset();

        nLen = bBufferHeader[0] << 8 | 0xFF & bBufferHeader[1];

        byte[] bBufferData = new byte[nLen];
        int nReadTotal = 0;
        int nReadLen = 0;
        do {
            this._ByteArrayBuffer.write(bBufferData, 0, nLen);
            nReadTotal += nReadLen;

            if (nReadTotal >= nLen) break;
        }
        while ((nReadLen = input.read(bBufferData)) > 0);

        return this._ByteArrayBuffer.toByteArray();
    }

    /**
     * PIN-->LMK加密
     */
    public synchronized String encodePassword(boolean bErrorTry, String strPassword, String strCardNo)
            throws IOException {

       /* if (HSM.masterIp.isEmpty() || HSM.masterPort == 0 || HSM.localEncZPK.isEmpty()) {
            return strPassword;
        }*/

        int nLength = strCardNo.length();
        String strCardNo12 = strCardNo.substring(nLength - 13, nLength - 1);
        try {
            if ((this.outputStream == null) || (this.inputStream == null)) connectHSM();

            byte[] bRequest = HSMPackage.makeRequestBA(strPassword, strCardNo12);

            logger.debug("bRequest:" + new String(bRequest));

            this.outputStream.write(HSMPackage.makeRequestHeader(bRequest));
            this.outputStream.write(bRequest);
            byte[] bResponse = receive(this.inputStream);
            logger.debug("bResponse:" + new String(bResponse));

            Vector vecRet = HSMPackage.parseResponseBB(new String(bResponse).trim().getBytes());
            String strRetCode = (String) vecRet.get(1);
            if (!(strRetCode.equals("00"))) {
                logger.error("HSM command BA/BB return error code " + strRetCode);
                throw new IOException("HSM command BA/BB return error code " + strRetCode);
            }
            String strEncPassword = ((String) vecRet.get(2));
            bRequest = HSMPackage.makeRequestJG(localEncZPK, strEncPassword, strCardNo12);
            logger.debug("bRequest:" + new String(bRequest));

            this.outputStream.write(HSMPackage.makeRequestHeader(bRequest));
            this.outputStream.write(bRequest);
            bResponse = receive(this.inputStream);
            logger.debug("bResponse:" + new String(bResponse));
            vecRet = HSMPackage.parseResponseJH(new String(bResponse).trim().getBytes());
            strRetCode = (String) vecRet.get(1);
            if (!strRetCode.equals("00")) {
                throw new IOException("HSM command JG/JH return error code " + strRetCode);
            }
            return (String) vecRet.get(2);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("HSM command BA/BB return error {} ", e);
            closeHSM();
            connectHSM();
            if (bErrorTry) {
                return encodePassword(false, strPassword, strCardNo);
            }
            throw e;
        }
    }

    /**
     * ZPK-->LMK解密
     */
    public synchronized String decodePassword(boolean bErrorTry, String strEncPassword, String strCardNo)
            throws IOException {
        int nLength = strCardNo.length();
        String strCardNo12 = strCardNo.substring(nLength - 13, nLength - 1);
        try {
            if ((this.outputStream == null) || (this.inputStream == null)) connectHSM();

            byte[] bRequest = HSMPackage.makeRequestJE(localEncZPK, strEncPassword, strCardNo12);
            System.out.println("bRequest:" + new String(bRequest));

            this.outputStream.write(HSMPackage.makeRequestHeader(bRequest));
            this.outputStream.write(bRequest);
            byte[] bResponse = receive(this.inputStream);
            System.out.println("bResponse:" + new String(bResponse));

            Vector vecRet = HSMPackage.parseResponseJF(new String(bResponse).trim().getBytes());
            String strRetCode = (String) vecRet.get(1);
            if (!strRetCode.equals("00")) {
                throw new IOException("HSM command JE/JF return error code " + strRetCode);
            }

            String strEncPIN = (String) vecRet.get(2);

            bRequest = HSMPackage.makeRequestNG(strEncPIN, strCardNo12);
            System.out.println("bRequest:" + new String(bRequest));
            System.out.println("bRequest:" + TypeConvert.bytes2HexString(bRequest));
            this.outputStream.write(HSMPackage.makeRequestHeader(bRequest));
            this.outputStream.write(bRequest);
            bResponse = receive(this.inputStream);
            System.out.println("bResponse:" + new String(bResponse));
            System.out.println("bResponse:" + TypeConvert.bytes2HexString(bResponse));
            vecRet = HSMPackage.parseResponseNH(new String(bResponse).trim().getBytes());
            strRetCode = (String) vecRet.get(1);
            if (!(strRetCode.equals("00"))) {
                logger.error("HSM command NG/NH return error code " + strRetCode);
                throw new IOException("HSM command NG/NH return error code " + strRetCode);
            }
            return (String) vecRet.get(2);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("HSM command NG/NH return error {} ", e);
            closeHSM();
            connectHSM();
            if (bErrorTry) {
                return decodePassword(false, strEncPassword, strCardNo);
            }

            throw e;
        }
    }

    public synchronized static String encodePassword(String strPassword, String strCardNo)
            throws IOException {
        if (HSM.masterIp.isEmpty() || HSM.masterPort == 0 || HSM.localEncZPK.isEmpty()) {
            return strPassword;
        }
        HSM hsm = new HSM();
        String encPassword = "";
        try {
            hsm.connectHSM();
            encPassword = hsm.encodePassword(false, strPassword, strCardNo);
        } catch (IOException e) {
            logger.error("加密密码失败,{}", e);
        } finally {
            try {
                hsm.closeHSM();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encPassword;
    }

    public static void main(String[] args) {
        HSM hsm = new HSM();
//        hsm.setMasterIp("192.168.0.168");
//        hsm.setMasterIp("10.0.0.12");
//        hsm.setMasterPort(8);
        try {
//            hsm.connectHSM();
            String encPassword = hsm.encodePassword(false, "111111", "9800880300000000441");
           // System.out.println(encPassword);
           // System.out.println(hsm.decodePassword(false, "8A9452D0A8ADDAF0", "9800880300000000458"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hsm.closeHSM();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}