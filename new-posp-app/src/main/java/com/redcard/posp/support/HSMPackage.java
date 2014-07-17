package com.redcard.posp.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.redcard.posp.common.TypeConvert;

public class HSMPackage {

    private final static Logger logger = LoggerFactory.getLogger(HSMPackage.class);

    public static byte[] makeRequestData(Vector vecData)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        Iterator ite = vecData.iterator();
        while (ite.hasNext()) {
            String strFieldValue = (String) ite.next();
            byteArrayRetData.write(strFieldValue.getBytes());
        }

        return byteArrayRetData.toByteArray();
    }

    public static byte[] makeRequestHeader(byte[] byteData)
            throws IOException {
        int nSize = byteData.length;
        byte[] bRet = new byte[2];
        bRet[0] = (byte) (0xFF & nSize >> 8);
        bRet[1] = (byte) (0xFF & nSize);

        return bRet;
    }

    public static byte[] makeHexRequestHeader(byte[] byteData)
            throws IOException {
        int nSize = byteData.length / 2;
        byte[] bRet = new byte[2];
        bRet[0] = (byte) (0xFF & nSize >> 8);
        bRet[1] = (byte) (0xFF & nSize);

        return bRet;
    }

    /**
     * 加密一个明文的PIN request
     * 命令：	加密一个文本PIN的明文。
     * 注意：	HSM必须处于授权状态。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“BA”。
     * PIN	LH	左对齐的、明文形式的文本PIN，后面附加字符“X’F”直至已加密PIN的长度。其值在安全配置“CS”命令中设置（范围为5至13）。
     * 账号	12N	账号中去除校验位的最右12位。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestBA(String strPassword, String strCard12)
            throws IOException {
        String strCommand = "BA";
        Vector vecData = new Vector(3);
        vecData.add(strCommand);
        StringBuilder sb = new StringBuilder();
        sb.append(strPassword).append("FFFFFFF".substring(0, 7 - strPassword.length()));
        vecData.add(sb.toString());
        vecData.add(strCard12);
        return makeRequestData(vecData);
    }

    /**
     * 加密一个明文的PIN request
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“BB”。
     * 错误代码	2N	00：无错误。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 17：不在授权状态。
     * PIN	L N	LMK对（02-03）下加密的PIN。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseBB(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(3);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 7));

        return vecData;
    }

    /**
     * 解密一个已加密的PIN request
     * 命令：	解密一个已加密的PIN。
     * 注意：	此命令仅当设置时已选择才有效。
     * HSM必须处于授权状态。
     * 参考数可以用于请求数据处理。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“NG”。
     * 账号	12N	账号中去除校验位的最右12位。
     * PIN	L N	LMK对（02-03）下加密的PIN。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestNG(String strPassword, String strCard12)
            throws IOException {
        String strCommand = "NG";
        Vector vecData = new Vector(3);
        vecData.add(strCommand);
        vecData.add(strCard12);
        vecData.add(strPassword);
        return makeRequestData(vecData);
    }

    /**
     * 解密一个已加密的PIN response
     * 命令：	解密一个已加密的PIN。
     * 注意：	此命令仅当设置时已选择才有效。
     * HSM必须处于授权状态。
     * 参考数可以用于请求数据处理。
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“NH”。
     * 错误代码	2N	00：无错误。
     * 13：LMK错误；报告给管理员。
     * 14：加密PIN错误。
     * 15：输入数据错。
     * 17：不在授权状态。
     * PIN	LH	左对齐的、明文形式的文本PIN，后面附加字符“X’F”直至已加密PIN的长度。
     * 参考数	12N	通过LMK对（18-19）下加密帐号导出的参考数。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseNH(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(3);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 7).replaceAll("F", ""));

        vecData.addElement(new String(bBuffer, 11, 12));
        return vecData;
    }

    /**
     * 将PIN从LMK翻译到ZPK request
     * 命令：	将PIN块从LMK下加密翻译到ZPK下加密。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“JG”。
     * 目的ZPK 	16H或1A+32H或1A+48H	将要加密PIN块的ZPK；LMK对（06-07）下加密。
     * PIN块格式	2N	PIN块的格式代码。
     * 账号	12N	账号中去除校验位的最右12位。
     * PIN	L N或L H	LMK对（02-03）下加密的PIN。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestJG(String strEncPIK, String strEncPassword, String strCard12)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "JG";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strEncPIK.getBytes());
        byteArrayRetData.write("01".getBytes());
        byteArrayRetData.write(strCard12.getBytes());
        byteArrayRetData.write(strEncPassword.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 将PIN从LMK翻译到ZPK response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“JH”。
     * 错误代码	2N	00：无错误。
     * 11：ZPK奇偶校验错。
     * 12：用户存储区中没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 14：来自主机的PIN错误。
     * 15：输入数据错。
     * 20：PIN块错误。
     * 21：无效的用户存储索引。
     * 23：无效PIN块格式代码。
     * PIN块	16H	ZPK下加密的PIN块。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseJH(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(3);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        return vecData;
    }

    /**
     * 将PIN从ZPK翻译到LMK request
     * 命令：	将PIN块从ZPK下加密翻译到LMK下加密。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“JE”。
     * 源ZPK 	16H或1A+32H或1A+48H	当前加密PIN块的ZPK；LMK对（06-07）下加密。
     * PIN块	16H	ZPK下加密的源PIN块。
     * PIN块格式	2N	PIN块的格式代码。
     * 账号	12N	账号中去除校验位的最右12位。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestJE(String strEncZPK, String strEncPassword, String strCard12)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "JE";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strEncZPK.getBytes());
        byteArrayRetData.write(strEncPassword.getBytes());
        byteArrayRetData.write("01".getBytes());
        byteArrayRetData.write(strCard12.getBytes());
        return byteArrayRetData.toByteArray();
    }

    /**
     * 将PIN从ZPK翻译到LMK request
     * 命令：	将PIN块从ZPK下加密翻译到LMK下加密。
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“JF”。
     * 错误代码	2N	00：无错误。
     * 10：源ZPK奇偶校验错。
     * 12：用户存储区中没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 20：PIN块错误。
     * 21：无效的用户存储索引。
     * 22：帐号无效。
     * 23：无效PIN块格式代码。
     * 24：PIN小于4位或大于12位。
     * PIN	L N或L H	LMK对（02-03）下加密的PIN。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseJF(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(3);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 7));

        return vecData;
    }

    /**
     * 手工将PIN从一个ZPK翻译到另一个ZPK request
     * <p/>
     * 将PIN块从ZPK下加密翻译到另一个ZPK下加密或者从一种格式转换为另一种格式。如果定义了同样的ZPK，则仅PIN块的格式被翻译；如果定义了同样的PIN块格式，则仅翻译加密密钥。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“CC”。
     * 源ZPK 	16H或1A+32H或1A+48H	当前加密PIN块的ZPK；LMK对（06-07）下加密。
     * 目的ZPK 	16H或1A+32H或1A+48H	将要加密PIN块的ZPK；LMK对（06-07）下加密。
     * 最大PIN长度	2N	值为“12”。强制。
     * 源PIN块	16H	源ZPK下加密的源PIN块。
     * 源PIN块格式	2N	源PIN块的格式代码。
     * 目的PIN块格式	2N	目的PIN块的格式代码。
     * 账号	12N	账号中去除校验位的最右12位。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestCC(String strInEncPIK, String strOutEncPIK, String strEncPassword, String strCard12)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "CC";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strInEncPIK.getBytes());
        byteArrayRetData.write(strOutEncPIK.getBytes());
        byteArrayRetData.write("12".getBytes());
        byteArrayRetData.write(strEncPassword.getBytes());
        byteArrayRetData.write("01".getBytes());
        byteArrayRetData.write("01".getBytes());
        byteArrayRetData.write(strCard12.getBytes());

        return byteArrayRetData.toByteArray();
    }

    public static byte[] makeRequestCC(String strInEncPIK, String strOutEncPIK, String strEncPassword, String strCard12, String strSrcFormat, String strDstFormat)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "CC";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strInEncPIK.getBytes());
        byteArrayRetData.write(strOutEncPIK.getBytes());
        byteArrayRetData.write("12".getBytes());
        byteArrayRetData.write(strEncPassword.getBytes());
        byteArrayRetData.write(strSrcFormat.getBytes());
        byteArrayRetData.write(strDstFormat.getBytes());
        byteArrayRetData.write(strCard12.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 手工将PIN从一个ZPK翻译到另一个ZPK response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“CD”。
     * 错误代码	2N	00：无错误。
     * 10：源ZPK奇偶校验错。
     * 11：目的ZPK奇偶校验错。
     * 12：用户存储区中没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 20：PIN块错误。
     * 21：无效的用户存储索引。
     * 22：帐号无效。
     * 23：无效PIN块格式代码。
     * 24：PIN小于4位或大于12位。
     * PIN长度	2N	所返回PIN的长度。
     * 目的PIN块	16H	目的ZPK下加密的目的PIN块。
     * 目的PIN块格式	2N	格式代码与收到的命令消息中的值相同。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseCD(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(4);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 2));

        vecData.addElement(new String(bBuffer, 6, 16));

        vecData.addElement(new String(bBuffer, 22, 2));

        return vecData;
    }


    /**
     * 将PIN从TPK翻译到ZPK  request
     * 命令：	将PIN块从TPK下加密翻译到ZPK下加密或者从一种格式转换为另一种格式。如果定义了同样的PIN块格式，则仅翻译加密密钥。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“CA”。
     * 源TPK 	16H或1A+32H或1A+48H	当前加密PIN块的TPK；LMK对（14-15）下加密。
     * 目的ZPK 	16H或1A+32H或1A+48H	将要加密PIN块的ZPK；LMK对（06-07）下加密。
     * 最大PIN长度	2N	值为“12”。强制。
     * 源PIN块	16H	源TPK下加密的PIN块。
     * 源PIN块格式	2N	源PIN块的格式代码。
     * 目的PIN块格式	2N	目的PIN块的格式代码。
     * 账号	12N	账号中去除校验位的最右12位。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestCA(String strInEncTPK, String strOutEncZPK, String strEncPassword, String strCard12, String strSrcFormat, String strDstFormat)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "CA";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strInEncTPK.getBytes());
        byteArrayRetData.write(strOutEncZPK.getBytes());
        byteArrayRetData.write("12".getBytes());
        byteArrayRetData.write(strEncPassword.getBytes());
        byteArrayRetData.write(strSrcFormat.getBytes());
        byteArrayRetData.write(strDstFormat.getBytes());
        byteArrayRetData.write(strCard12.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 将PIN从TPK翻译到ZPK  response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“CB”。
     * 密码长度	2N
     * 错误代码	2N	00：无错误。
     * 10：源TPK奇偶校验错。
     * 11：目的ZPK奇偶校验错。
     * 12：用户存储区中没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 20：PIN块错误。
     * 21：无效的用户存储索引。
     * 22：帐号无效。
     * 23：无效PIN块格式代码。
     * 24：PIN小于4位或大于12位。
     * 目的PIN块	16H	目的ZPK下加密的目的PIN块。
     * 目的PIN块格式	2N	格式代码与收到的命令消息中的值相同。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseCB(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(4);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 2));

        vecData.addElement(new String(bBuffer, 6, 16));

        vecData.addElement(new String(bBuffer, 22, 2));

        return vecData;
    }

    /**
     * 产生一对RSA密钥（34）request
     * 命令代码	2	A	值“34”
     * 密钥长度	4	N	比特长度：“0320”－“4096”，应为8的整倍数。
     * 私钥索引	2	N	“00”－“20”：密码机内保存新生成的密钥。
     * “99”：不保存新生成的密钥。
     */
    @Deprecated
    public static byte[] makeRequest34(int nKeyLen, int nKeyIndex)
            throws IOException {
//        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
//        String strCommand = "34";
//        byteArrayRetData.write(strCommand.getBytes());
//        byteArrayRetData.write(StringUtils.Int2String(nKeyLen, 4).getBytes());
//        byteArrayRetData.write(StringUtils.Int2String(nKeyIndex, 2).getBytes());
//
//        return byteArrayRetData.toByteArray();
        return null;
    }

    /**
     * 产生一对RSA密钥（34）response
     * <p/>
     * 响应代码	2	A	“35”
     * 错误代码	2	H
     * 私钥长度	4	N	私钥密文字节数
     * 私钥密文	N	B	用主密钥加密的私钥
     * 公　钥	N	B	ANS.1 DER编码方式―――见附录
     */
    public static Vector parseResponse35(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        String strPrivateKeyLen = new String(bBuffer, 4, 4);
        vecData.addElement(strPrivateKeyLen);
        System.out.println("strPrivateKeyLen:" + strPrivateKeyLen);

        int nPrivateKeyLen = Integer.parseInt(strPrivateKeyLen);
        byte[] bBufferKey = new byte[nPrivateKeyLen];
        System.arraycopy(bBuffer, 8, bBufferKey, 0, nPrivateKeyLen);
        vecData.addElement(bBufferKey);

        int nPublicKeyLen = 140;
        byte[] bBufferPublicKey = new byte[nPublicKeyLen];
        System.out.println("bBufferPublicKey index:" + (8 + nPrivateKeyLen));
        System.out.println("bBufferPublicKey end:" + (8 + nPrivateKeyLen + nPublicKeyLen));
        System.arraycopy(bBuffer, 8 + nPrivateKeyLen, bBufferPublicKey, 0, nPublicKeyLen);
        vecData.addElement(bBufferPublicKey);

        return vecData;
    }

    /**
     * 将ZEK/ZAK从ZMK转为LMK加密 request
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“FK”。
     * 标志	1N	0为ZEK，1为ZAK。
     * ZMK	16H或32H或1A+32H或1A+48H	LMK对（04-05）变种下加密的ZMK。
     * ZEK或ZAK	16H或1A+32H或1A+48H	ZMK下加密的ZEK或ZAK。
     * （标志位为0时为ZEK，1为ZAK）
     * Atalla变量	1N或2N	可选项。Atalla变量；在有Atalla设备的系统中使用。仅当模式为1时才显示该域。
     * 分隔符	1A	可选项。如果显示后面的三个域，则该域必须显示。值为“;”。
     * 如果命令不需要一个可选域，则用一个有效值或0填充。
     * 密钥方案（ZMK）	1A	可选项。ZMK下加密密钥的方案。
     * 密钥方案（LMK）	1A	可选项。LMK下加密密钥的方案。
     * 密钥校验值类型	1A	可选项。密钥校验值计算方式。
     * 0－KCV向后兼容。
     * 1－KCV 6H。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestFI(String strZEKZAKFlag, String makeCardZMK)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "FI";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strZEKZAKFlag.getBytes());

        byteArrayRetData.write(makeCardZMK.getBytes());
        byteArrayRetData.write(";XX0".getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 将ZEK/ZAK从ZMK转为LMK加密 response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“FL”。
     * 错误代码	2N	00：无错误。
     * 10：第一个成份奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * ZEK	16H或1A+32H或1A+48H	LMK30-31对下加密的ZEK。（仅当标志位为0时才有该域）
     * ZAK	16H或1A+32H或1A+48H	LMK26-27对下加密的ZAK。（仅当标志位为1时才有该域）
     * 密钥校验值	16H或6H	用ZPK加密64位0的结果。是16H还是6H取决于KCV的类型选项。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseFJ(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 33));

        vecData.addElement(new String(bBuffer, 37, 33));

        vecData.addElement(new String(bBuffer, 70, 16));

        return vecData;
    }

    public static byte[] makeRequestE0(String strZEK, String strEncFlag, String strData)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "E0";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write("0".getBytes());
        byteArrayRetData.write(strEncFlag.getBytes());
        byteArrayRetData.write("10".getBytes());
        byteArrayRetData.write(strZEK.getBytes());
        byteArrayRetData.write("11000000".getBytes());

        StringBuilder sb = new StringBuilder();
        sb.append("000").append(Integer.toHexString(strData.length() / 2));
        byteArrayRetData.write(sb.substring(sb.length() - 3).getBytes());
        byteArrayRetData.write(strData.getBytes());

        return byteArrayRetData.toByteArray();
    }

    public static Vector parseResponseE1(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 1));

        String strDatalen = new String(bBuffer, 5, 3);
        vecData.addElement(strDatalen);

        int nDataLen = Integer.valueOf(strDatalen, 16).intValue();
        logger.debug("nDataLen:" + nDataLen);
        vecData.addElement(new String(bBuffer, 8, nDataLen * 2));

        return vecData;
    }

    public static byte[] makeRequest98(String strKey, String strKeyType, String strLSData1, String strLSData2, String strLSData3)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "98";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strKey.getBytes());
        byteArrayRetData.write(strKeyType.getBytes());
        byteArrayRetData.write(strLSData1.getBytes());
        byteArrayRetData.write(strLSData2.getBytes());
        byteArrayRetData.write(strLSData3.getBytes());

        return byteArrayRetData.toByteArray();
    }

    public static Vector parseResponse99(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 32));

        return vecData;
    }

    /**
     * 对大消息生成MAC（MAB）request
     * 命令：	对大消息生成MAC（MAB）。
     * 注意：	命令处理二进制数据。如果HSM设置为Async/ASCII处理方式，则应当确保：
     * 主机端口已经通过CS命令设置为8数据比特处理方式。
     * 生成MAC的数据并不包含EM（X’19）或ETX（X’03）。
     * 如果消息块为第一个块或中间块，则其必须为8个字节的倍数。
     * 对于给定数据的值n为推荐的最大值。考虑到相对于整个HSM命令消息大小的整个缓冲区大小，其值可以增至2047（SNA-SDLC系统为1023）。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“MQ”。
     * 消息块号	1N	0：仅一个消息块。
     * 1：第一个消息块。
     * 2：中间消息块。
     * 3：最后消息块。
     * ZAK	16H	LMK对（26-27）下加密的ZAK。
     * IV	16H	初始值，仅当消息块号为2或3时显示。
     * 消息长度	3H	以字节为单位的消息长度。
     * 消息块	nB	明文文本形式的消息块。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestMQ(String strZAK, String strData)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "MQ";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write("0".getBytes());
        byteArrayRetData.write(strZAK.getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append("000").append(Integer.toHexString(strData.length() / 2));
        byteArrayRetData.write(sb.substring(sb.length() - 3).getBytes());
        byteArrayRetData.write(strData.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 对大消息生成MAC（MAB）response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“MR”。
     * 错误代码	2N	00：无错误。
     * 02：ZAK不是单倍长度。
     * 05：无效消息块号。
     * 10：ZAK奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * 27：无效密钥长度。
     * 80：数据长度错。
     * MAB	16H	当消息块号为“1”或“2”时，作为下一块的IV。
     * 当消息块号为“0”或“3”时，用于消息认证。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseMR(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        return vecData;
    }


    /**
     * 用ANSI X9.19方式对大消息生成MAC（MAB）request
     * 命令：	用TAK或ZAK对大消息生成MAC（MAB）。如果密钥为单倍长度，则使用ANSI X9.9 MAC算法；如果密钥为双倍长度，则使用ANSI X9.19 MAC算法。
     * 注意：	命令可以处理二进制数据或者扩展的十六进制数据。如果HSM设置为Async/ASCII处理方式，则应当确保：
     * 主机端口已经通过CS命令设置为8数据比特处理方式。
     * 生成MAC的数据并不包含EM（X’19）或ETX（X’03）。
     * 扩展的十六进制模式使用2个十六进制字符表示一个二进制字节。
     * 如果消息块为第一个块或中间块，则其必须为8个字节的倍数。
     * 在选定消息头长度值n之前必须先考虑HSM缓冲区的大小。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“MS”。
     * 消息块号	1N	0：仅一块。
     * 1：第一块。
     * 2：中间块。
     * 3：最后块。
     * 密钥类型	1N	密钥类型：
     * 0－TAK（终端认证密钥）
     * 1－ZAK（区域认证密钥）
     * 密钥长度	1N	密钥长度：
     * 0－单倍长度DES密钥
     * 1－双倍长度DES密钥
     * 消息类型	1N	消息类型：
     * 0－消息数据为二进制
     * 1－消息数据为扩展十六进制
     * 密钥	16H或32H或1A+32H	对应LMK对下加密的密钥。
     * TAK－LMK对（16-17）下加密
     * ZAK－LMK对（26-27）下加密
     * IV	16H	初始值，仅当消息块号为2或3时显示。
     * 消息长度	4H	将要作MAC的消息长度（若消息类型为二进制，则为随后域长度；若消息类型为扩展十六进制，则为随后域长度的一半）。
     * 消息块	nB或H	二进制或扩展十六进制形式的消息块。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestMS(String strKeyType, String strKeyLenType, String strKey, String strData)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "MS";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write("0".getBytes());
        byteArrayRetData.write(strKeyType.getBytes());
        byteArrayRetData.write(strKeyLenType.getBytes());
        byteArrayRetData.write("0".getBytes());
        byteArrayRetData.write(strKey.getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append("000").append(Integer.toHexString(strData.length()));
        byteArrayRetData.write(sb.substring(sb.length() - 4).getBytes());
        byteArrayRetData.write(strData.getBytes());

        return byteArrayRetData.toByteArray();
    }

    public static byte[] makeRequestMS(String strBlockNum, String strKeyType, String strKeyLenType, String strKey, String strDataBlock, String strIV)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "MS";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strBlockNum.getBytes());
        byteArrayRetData.write(strKeyType.getBytes());
        byteArrayRetData.write(strKeyLenType.getBytes());
        byteArrayRetData.write("0".getBytes());
        byteArrayRetData.write(strKey.getBytes());
        byteArrayRetData.write(strIV.getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append("000").append(Integer.toHexString(strDataBlock.length()));
        byteArrayRetData.write(sb.substring(sb.length() - 4).getBytes());
        byteArrayRetData.write(strDataBlock.getBytes());

        return byteArrayRetData.toByteArray();
    }

    public static byte[] makeRequestMS(String strBlockNum, String strKeyType, String strKeyLenType, String strKey, byte[] bytesDataBlock, String strIV)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "MS";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strBlockNum.getBytes());
        byteArrayRetData.write(strKeyType.getBytes());
        byteArrayRetData.write(strKeyLenType.getBytes());
        byteArrayRetData.write("0".getBytes());
        byteArrayRetData.write(strKey.getBytes());
        byteArrayRetData.write(strIV.getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append("000").append(Integer.toHexString(bytesDataBlock.length));
        byteArrayRetData.write(sb.substring(sb.length() - 4).getBytes());
        byteArrayRetData.write(bytesDataBlock);

        return byteArrayRetData.toByteArray();
    }

    /**
     * 用ANSI X9.19方式对大消息生成MAC（MAB）response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“MT”。
     * 错误代码	2N	00：无错误。
     * 03：无效消息类型代码。
     * 04：无效密钥类型代码。
     * 05：无效消息块号。
     * 06：无效密钥长度代码。
     * 10：ZAK奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * 27：无效密钥长度。
     * 80：数据长度错。
     * MAB	16H	当消息块号为“1”或“2”时，作为下一块的IV。
     * 当消息块号为“0”或“3”时，用于消息认证。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseMT(byte[] bBuffer) {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        return vecData;
    }

    /**
     * 生成一个TMK、TPK或PVK request
     * <p/>
     * 生成一个随机的密钥，并在TMK（TPK或PVK）下和LMK14-15下加密。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“HC”。
     * 当前的TMK、TPK或PVK	16H或1A+32H或1A+48H	LMK对（14-15）下加密的TMK、TPK或PVK。
     * 分隔符	1A	可选项。如果显示后面的三个域，则该域必须显示。值为“;”。
     * 如果命令不需要一个可选域，则用一个有效值或0填充。
     * 密钥方案（TMK）	1A	可选项。TMK下加密密钥的方案。
     * 密钥方案（LMK）	1A	可选项。LMK下加密密钥的方案。
     * 密钥校验值类型	1A	可选项。密钥校验值计算方式。
     * 0－KCV向后兼容。
     * 1－KCV 6H。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestHC(String strTMK)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "HC";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strTMK.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 生成一个TMK、TPK或PVK response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“HD”。
     * 错误代码	2N	00：无错误。
     * 10：第一个成份奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * 当前密钥下加密的新密钥	16H或1A+32H或1A+48H	当前密钥下加密的新密钥。
     * 新密钥（LMK）	16H或1A+32H或1A+48H	LMK下加密的新密钥。
     * 密钥校验值	16H或6H	用ZPK加密64位0的结果。是16H还是6H取决于KCV的类型选项。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseHD(byte[] bBuffer) {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        vecData.addElement(new String(bBuffer, 20, 16));

        return vecData;
    }

    /**
     * 将TMK、TPK或PVK从LMK转为另一TMK、TPK或PVK加密 request
     * 命令：	将TMK、TPK或PVK从LMK对（14-15）下转为另一TMK、TPK或PVK下加密。
     * 注意：	本命令用于将当前的密钥用数据库中的另一密钥替代。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“AE”。
     * 当前TMK、TPK或PVK	16H或1A+32H或1A+48H	LMK对（14-15）下加密的当前TMK、TPK或PVK。
     * 存储TMK、TPK或PVK	16H或1A+32H或1A+48H	LMK对（14-15）下加密的存储TMK、TPK或PVK。
     * Atalla变量	1N或2N	可选项。Atalla变量；在有Atalla设备的系统中使用。仅当模式为1时才显示该域。
     * 分隔符	1A	如果显示后面的三个域，则该域必须显示。值为“;”。
     * 如果命令不需要一个可选域，则用一个有效值或0填充。
     * 密钥方案（TMK）	1A	TMK下加密密钥的方案。
     * 密钥方案（LMK）	1A	LMK下加密密钥的方案。
     * 密钥校验值类型	1A	密钥校验值计算方式。
     * 0－KCV向后兼容。
     * 1－KCV 6H。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestAE(String strKey, String strTMK)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "AE";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strKey.getBytes());
        byteArrayRetData.write(strTMK.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 将TMK、TPK或PVK从LMK转为另一TMK、TPK或PVK加密 response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“AF”。
     * 错误代码	2N	00：无错误。
     * 10：当前TMK、TPK或PVK奇偶校验错。
     * 11：存储TMK、TPK或PVK奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * 当前密钥下加密的存储密钥	16H或1A+32H或1A+48H	当前密钥下加密的存储密钥。
     * 密钥校验值	16H或6H	用ZPK加密64位0的结果。是16H还是6H取决于KCV的类型选项。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseAF(byte[] bBuffer) {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        return vecData;
    }

    /**
     * 生成一个TAK request
     * 命令：	生成一个随机的密钥，然后在TMK（TPK或PVK）和LMK对16-17下加密。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“HA”。
     * TMK	16H或1A+32H或1A+48H	LMK对（14-15）下加密的TMK。
     * 分隔符	1A	可选项。如果显示后面的三个域，则该域必须显示。值为“;”。
     * 如果命令不需要一个可选域，则用一个有效值或0填充。
     * 密钥方案（TMK）	1A	可选项。TMK下加密密钥的方案。
     * 密钥方案（LMK）	1A	可选项。LMK下加密密钥的方案。
     * 密钥校验值类型	1A	可选项。密钥校验值计算方式。
     * 0－KCV向后兼容。
     * 1－KCV 6H。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestHA(String strTMK)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "HA";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strTMK.getBytes());

        return byteArrayRetData.toByteArray();
    }


    /**
     * 生成一个TAK response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“HB”。
     * 错误代码	2N	00：无错误。
     * 10：第一个成份奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * TAK（TMK）	16H或1A+32H或1A+48H	TMK下加密的随机的TAK。
     * TAK（LMK）	16H或1A+32H或1A+48H	LMK对16-17下加密的密钥。
     * 密钥校验值	16H	用ZPK加密64位0的结果。是16H还是6H取决于KCV的类型选项。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseHB(byte[] bBuffer) {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        vecData.addElement(new String(bBuffer, 20, 16));

        return vecData;
    }

    /**
     * 将TAK从LMK转为TMK加密 request
     * 命令：	将TAK从LMK下转为TMK下加密。
     * 注意：	本命令用于向一终端发送密钥。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“AG”。
     * TMK	16H或32H或1A+32H或1A+48H	LMK对（14-15）下加密的TMK。
     * TAK	16H或1A+32H或1A+48H	LMK对（16-17）下加密的TAK。
     * Atalla变量	1N或2N	可选项。Atalla变量；在有Atalla设备的系统中使用。仅当模式为1时才显示该域。
     * 分隔符	1A	可选项。如果显示后面的三个域，则该域必须显示。值为“;”。
     * 如果命令不需要一个可选域，则用一个有效值或0填充。
     * 密钥方案（ZMK）	1A	可选项。ZMK下加密密钥的方案。
     * 密钥方案（LMK）	1A	可选项。LMK下加密密钥的方案。
     * 密钥校验值类型	1A	可选项。密钥校验值计算方式。
     * 0－KCV向后兼容。
     * 1－KCV 6H。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestAG(String strTAK, String strTMK)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "AG";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strTMK.getBytes());
        byteArrayRetData.write(strTAK.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 将TAK从LMK转为TMK加密 response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“AH”。
     * 错误代码	2N	00：无错误。
     * 10：ZMK奇偶校验错。
     * 11：TMK、TPK或PVK奇偶校验错。
     * 12：用户存储区没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效的用户存储索引。
     * TAK	16H或1A+32H或1A+48H	转换后的TAK；TMK下加密。
     * 密钥校验值	16H或6H	用ZPK加密64位0的结果。是16H还是6H取决于KCV的类型选项。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseAH(byte[] bBuffer) {
        Vector vecData = new Vector(4);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        return vecData;
    }

    public static byte[] makeRequestEO(String strPublicKey)
            throws IOException {
        String strCommand = "EO";

        StringBuilder sb = new StringBuilder();
        sb.append(TypeConvert.bytes2HexString(strCommand.getBytes()));
        sb.append(TypeConvert.bytes2HexString("01".getBytes()));
        sb.append("308201").append("0A0282010100");
        sb.append(strPublicKey).append("0203010001");

        return TypeConvert.hexStringToByte(sb.toString());
    }

    public static Vector parseResponseEP(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(4);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        byte[] bytesMAC = new byte[4];
        System.arraycopy(bBuffer, 4, bytesMAC, 0, 4);
        vecData.addElement(bytesMAC);
        System.out.println("bytesMAC:" + new String(bytesMAC));
        System.out.println("bytesMAC:" + TypeConvert.bytes2HexString(bytesMAC));

        return vecData;
    }

    /**
     * 输出一个DES密钥    request
     * 命令：	将DES密钥从LMK下加密转换为公钥下加密。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“GK”。
     * 加密标识	2A	用于加密DES密钥的算法标识。
     * 附加模式标识	2N	用于加密运算中的附加模式标识。
     * DES密钥类型	4N	指示需要的LMK对，包含LMK变种。
     * DES密钥标记	1N	指示DES密钥的标记：
     * 0：单倍长度。
     * 1：双倍长度。
     * 2：三倍长度。
     * DES密钥（LMK）	16H或32H或1A+32H或1A+48H	由DES密钥类型指定的LMK对下加密的DES密钥。
     * 校验值	16H	DES密钥的校验值。
     * MAC	4B	对于公钥和证明数据的MAC，用LMK对36-37计算。
     * 公钥	nB	公钥，用ASN.1格式编码的DER（模、指数的序列）。
     * 证明数据	nA	可选。在MAC计算中附加的数据（也可不包含）。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestGK(String strDESKeyType, String strDESKey, String strDESMAC, byte[] bytesMAC, String strPublicKey)
            throws IOException {
        String strCommand = "GK";

        StringBuffer sb = new StringBuffer();
        sb.append(strCommand).append("0101").append(strDESKeyType).append("1").append(strDESKey).append(strDESMAC);
        String strTemp = sb.toString();
        sb.setLength(0);
        sb.append(TypeConvert.bytes2HexString(strTemp.getBytes()));
        sb.append(TypeConvert.bytes2HexString(bytesMAC));
        sb.append("308201").append("0A0282010100");
        sb.append(strPublicKey).append("0203010001");
        System.out.println("GK:" + sb.toString());

        return TypeConvert.hexStringToByte(sb.toString());
    }

    /**
     * 输出一个DES密钥    response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“GL”。
     * 错误代码	2N	00：无错误。
     * 01：MAC校验失败。
     * 02：校验值校验失败。
     * 04：公钥不符合编码规则。
     * 05：无效的DES密钥类型。
     * 06：无效的加密标识。
     * 07：无效的附加模式标识。
     * 10：密钥奇偶校验错误。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 47：DSP错误；报告给管理员。
     * 76：密钥块长度错误。
     * 初始化值	16H	对DES密钥的初始化值。
     * DES密钥长度	4N	下一个域的字节长度。
     * DES密钥（PK）	nB	公钥下加密的DES密钥。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector parseResponseGL(byte[] bBuffer)
            throws NoSuchElementException {
        Vector vecData = new Vector(6);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        String strDataLen = new String(bBuffer, 20, 4);
        vecData.addElement(strDataLen);
        System.out.println("strDataLen:" + strDataLen);

        int nDataLen = Integer.parseInt(strDataLen);
        byte[] bytesDESKeyData = new byte[nDataLen];
        System.arraycopy(bBuffer, 24, bytesDESKeyData, 0, nDataLen);
        vecData.addElement(bytesDESKeyData);

        return vecData;
    }

    /**
     * 输入一个DES密钥    request
     * 命令：	将DES密钥从公钥下加密转换为LMK下加密。
     * <p/>
     * 消息头	mA	该消息头随后即被无变化地返回给主机。
     * 命令代码	2A	值为“GI”。
     * 加密标识	2A	用于加密DES密钥的算法标识。
     * 附加模式标识	2N	用于加密运算中的附加模式标识。
     * DES密钥类型	4N	指示需要的LMK对，包含LMK变种。
     * 被加密密钥的长度	4N	被加密DES密钥的字节长度。
     * DES密钥（PK）	nB	公钥下加密的DES密钥。
     * 分隔符	1A	分隔符，指示信息数据域的结尾。值为“;”。
     * 私钥标记	2N	指示私钥位置的标记。数字为存储私钥的索引，除了99表示使用由命令中提供的密钥。
     * 私钥长度	4N	在下一个域中的字节长度。
     * 私钥	nB	LMK对34-35下加密的私钥。
     * 分隔符	1A	可选项。如果显示后面的三个域，则该域必须显示。值为“;”。
     * 如果命令不需要一个可选域，则用一个有效值或0填充。
     * 密钥方案（ZMK）	1A	可选项。ZMK下加密密钥的方案。
     * 密钥方案（LMK）	1A	可选项。LMK下加密密钥的方案。
     * 密钥校验值类型	1A	可选项。密钥校验值计算方式。
     * 0－KCV向后兼容。
     * 1－KCV 6H。
     * 终止信息分隔符	1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾	nA	可选项。最大长度为32个字符。
     */
    @Deprecated
    public static byte[] makeRequestGI(String strDESKeyType, String strDESKey, String strPrivateKeyIndex)
            throws IOException {
//        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
//        String strCommand = "GI";
//        byteArrayRetData.write(strCommand.getBytes());
//        byteArrayRetData.write("0101".getBytes());
//        byteArrayRetData.write(strDESKeyType.getBytes());
//        byte[] bytesDESKey = TypeConvert.hexStringToByte(strDESKey);
//        byteArrayRetData.write(StringUtils.Int2String(bytesDESKey.length, 4).getBytes());
//        byteArrayRetData.write(bytesDESKey);
//        byteArrayRetData.write(";".getBytes());
//        byteArrayRetData.write(strPrivateKeyIndex.getBytes());
//        byteArrayRetData.write(";".getBytes());
//        byteArrayRetData.write(TypeConvert.hexStringToByte("585830"));
//
//        return byteArrayRetData.toByteArray();
        return null;
    }

    /**
     * 输入一个DES密钥    response
     * <p/>
     * 消息头	nA	无变化地被返回给主机。
     * 响应代码	2A	值为“GJ”。
     * 错误代码	2N	00：无错误。
     * 03：无效的私钥类型。
     * 04：公钥不符合编码规则。
     * 05：无效的哈希标识。
     * 06：无效的签名标识。
     * 07：无效的附加标识。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 47：DSP错误；报告给管理员。
     * 49：私钥错误；报告给管理员。
     * 74：无效摘要信息语法（仅仅无哈希模式）。
     * 76：密钥块长度错误。
     * 77：明文数据块错误。
     * 78：私钥长度错误。
     * 79：哈希算法对象标识错误。
     * 80：信息长度错。
     * 81：签名长度错。
     * 初始化值	16H	对DES密钥的初始化值。
     * DES密钥（LMK）	16H或32H或1A+32H或1A+48H	由DES密钥类型指定的LMK对下加密的DES密钥。
     * 密钥校验值	16H或6H	对于DES密钥的校验值。
     * 16H还是6H取决于KCV的类型选项。
     * 终止信息分隔符	1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾	nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseGJ(byte[] bBuffer, int nDESKeyLen)
            throws NoSuchElementException {
        Vector vecData = new Vector(6);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, 16));

        vecData.addElement(new String(bBuffer, 20, nDESKeyLen));

        vecData.addElement(new String(bBuffer, 20 + nDESKeyLen, 16));

        return vecData;
    }

    /**
     * 生成密钥 request
     * <p/>
     * <p/>
     * 消息头             mA	该消息头随后即被无变化地返回给主机。
     * 命令代码           2A	值为“A0”。
     * 模式               1H	0－产生密钥。
     * 1－产生密钥并在ZMK下加密。
     * 密钥类型           3H	密钥类型。
     * 密钥方案（LMK）    1A	密钥长度/LMK下加密密钥的方案。
     * ZMK                16H或32H或1A+32H或1A+48H	仅当模式为1时才显示该ZMK域。
     * 密钥方案（ZMK）    1A	加密输出密钥的密钥方案。仅当模式为1时才显示该域。
     * Atalla变量         1N或2N	可选项。Atalla变量；在有Atalla设备的系统中使用。仅当模式为1时才显示该域。
     * 终止信息分隔符     1C	可选项。如果显示消息尾域，则该域必须显示。值为“X’19”。
     * 消息尾             nA	可选项。最大长度为32个字符。
     */
    public static byte[] makeRequestA0(String strActionType, String strKeyType, String strKeyFlag, String strZMK, String strZMKFlag)
            throws IOException {
        ByteArrayOutputStream byteArrayRetData = new ByteArrayOutputStream();
        String strCommand = "A0";
        byteArrayRetData.write(strCommand.getBytes());
        byteArrayRetData.write(strActionType.getBytes());
        byteArrayRetData.write(strKeyType.getBytes());
        byteArrayRetData.write(strKeyFlag.getBytes());
        byteArrayRetData.write(strZMK.getBytes());
        byteArrayRetData.write(strZMKFlag.getBytes());

        return byteArrayRetData.toByteArray();
    }

    /**
     * 生成密钥 response
     * <p/>
     * 消息头              nA	无变化地被返回给主机。
     * 响应代码            2A	值为“A1”。
     * 错误代码            2N	00：无错误。
     * 10：ZMK奇偶校验错。
     * 12：用户存储区中没有装载密钥。
     * 13：LMK错误；报告给管理员。
     * 15：输入数据错。
     * 21：无效用户存储区索引。
     * 密钥（LMK）          16H或1A+32H或1A+48H	LMK加密下的密钥。
     * 密钥（ZMK）          16H或1A+32H或1A+48H	ZMK加密下的密钥。1
     * 密钥校验值           16H	密钥校验值。
     * 终止信息分隔符       1C	仅当命令消息中有时才显示该域。值为“X’19”。
     * 消息尾               nA	仅当命令消息中有时才显示该域。最大长度为32个字符。
     */
    public static Vector<String> parseResponseA1(byte[] bBuffer, int nKeyLen)
            throws NoSuchElementException {
        Vector vecData = new Vector(5);

        vecData.addElement(new String(bBuffer, 0, 2));

        vecData.addElement(new String(bBuffer, 2, 2));

        vecData.addElement(new String(bBuffer, 4, nKeyLen));

        return vecData;
    }

}
