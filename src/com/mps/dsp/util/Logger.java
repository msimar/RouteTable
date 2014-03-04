package com.mps.dsp.util;

//import java.util.Date;

public final class Logger {
	
	public static final boolean DEBUG = true;
	public static final String LTAG = "RouteApp"; //= Logger.class.getSimpleName();
	
	//private Date timestamp = new Date();
	
	private Logger() {
		// TODO Auto-generated constructor stub
	}
	
	public static void d(String message){
		if( DEBUG ) System.out.println(message);
	}
	
	public static void d(String tag, String message){
		if( DEBUG ) System.out.println(tag + ": " + message);
	}
	
	public static void d(String tag, int message){
		if( DEBUG ) System.out.println(tag + ": " + message);
	}
	
	public static void e(String tag, String message){
		if( DEBUG ) System.err.println(tag + ": " + message);
	}
	
	public static void e(String message){
		if( DEBUG ) System.err.println(LTAG + ": " + message);
	}
}
