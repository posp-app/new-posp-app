package com.redcard.posp.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.redcard.posp.common.CommonUtil;

public final class IDBuilder {

	private int systemSequence = 3;
	
	private String batchSequence = "000000";
	
	private static String configFile = ApplicationContextInit.MESSAGE_FORMAT_PATH+"\\"+ApplicationContextInit.ID_CACHE_FILE;
	
	private static IDBuilder instance = null;
	
	private Properties  proper = null;
	
	private IDBuilder() {
		init();
	}
	
	private void init() {
		InputStream in = null;
		try {
			//String configPath = this.getClass().getResource(CONFIG_FILE).getPath();
			//log.debug("message config file :"+configPath);
			in = new FileInputStream(configFile);
			proper = new Properties();
			proper.load(in);
			systemSequence = Integer.parseInt(proper.getProperty("systemSequence"));
			batchSequence = proper.getProperty("batchSequence");
		}catch (java.io.IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException("load file error!");
			
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void persistent() {
		try {
			OutputStream os = new FileOutputStream(configFile);			
			proper.store(os,configFile);
			os.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static IDBuilder getInstance() {
		if (instance == null) {
			instance = new IDBuilder();
		}
		return instance;
	}
	
	/**
	 *pos系统流水号
	 * @return
	 */
	public synchronized String getSystemSequence() {
		systemSequence = systemSequence >= 999999?1:systemSequence+1;
		String ss= Integer.toString(systemSequence);
		proper.setProperty("systemSequence", ss);
		persistent();
		return CommonUtil.addLeftZero(ss,6);
		//return "001122";
	}
	
	/**
	 * 批次号
	 * @return
	 */
	public String getBatchSequence() {
		return batchSequence;
	}
	
	public synchronized void setBatchSequence(String bs) {
		batchSequence = bs;
		proper.setProperty("batchSequence", batchSequence);
		persistent();
	}
}
