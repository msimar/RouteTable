package com.mps.dsp.core;

import java.util.Date;

public class Logger {
	
	public static final boolean DEBUG = true;
	public static final String LTAG = "RouteApp"; //= Logger.class.getSimpleName();
	
	private Date timestamp = new Date();
	
	private Logger() {
		// TODO Auto-generated constructor stub
	}
	
	public final static Logger INSTANCE = new Logger();
	
	public void d(String tag, String message){
		if( DEBUG ) System.out.println(timestamp + "  " + tag + ": " + message);
	}
	
	public void d(String tag, int message){
		if( DEBUG ) System.out.println(timestamp + "  " + tag + ": " + message);
	}
	
	public void e(String tag, String message){
		if( DEBUG ) System.out.println(timestamp + "  " + tag + ": " + message);
	}
}
