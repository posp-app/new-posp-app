package com.redcard.posp.support.command;

public interface ICommandHandler {
	
	public static final String CMD_C = "C";

	public static final String CMD_G = "G";
	
	public static final String CMD_M = "M";
	
	public static final String CMD_P = "P";
	
	public String handler(String cmd);
}
