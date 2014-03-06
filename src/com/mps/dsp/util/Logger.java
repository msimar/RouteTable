package com.mps.dsp.util;

//import java.util.Date;

/**
 * A Logger class to log events in the App.
 * 
 * @author msingh
 *
 */
public final class Logger {
	
	/**
	 * Advance debugging flag to call hierarchy in the App
	 */
	public static final boolean DEBUG = false;
	
	/**
	 * A common logger tag for the App
	 */
	public static final String LTAG = "RouteApp"; //= Logger.class.getSimpleName();
	
	//private Date timestamp = new Date();
	
	/**
	 * Creates a Logger Object
	 */
	private Logger() {
		// private constructor to avoid instances
	}
	
	/**
	 * Always publish debug log to the console 
	 * @param message the string log message
	 */
	public static void console(String message){
		System.out.println(message);
	}
	
	/**
	 * As per debug flag, publish debug log to the console without tag 
	 * @param message the string log message
	 */
	public static void d(String message){
		if( DEBUG ) System.out.println(message);
	}
	
	/**
	 * As per debug flag, publish debug log to the console with parameter tag 
	 * @param message the string log message
	 * @param tag the string log tag
	 */
	public static void d(String tag, String message){
		if( DEBUG ) System.out.println(tag + ": " + message);
	}
	
	/**
	 * As per debug flag, publish debug log to the console with parameter tag 
	 * @param message the integer log message
	 * @param tag the string log tag
	 */
	public static void d(String tag, int message){
		if( DEBUG ) System.out.println(tag + ": " + message);
	}
	
	/**
	 * As per debug flag, publish error log to the console with parameter tag 
	 * @param message the string log message
	 * @param tag the string log tag
	 */
	public static void e(String tag, String message){
		if( DEBUG ) System.err.println(tag + ": " + message);
	}
	
	/**
	 * As per debug flag, publish error log to the console with global tag 
	 * @param message the string log message
	 */
	public static void e(String message){
		if( DEBUG ) System.err.println(LTAG + ": " + message);
	}
}
