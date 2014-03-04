package com.mps.dsp.config;

/**
 * Static configuration class containing some global variables.
 * @author msingh
 *
 */
public final class Configuration {
	/**
	 * Time to wait before retrying.
	 */
	public final static int retryConnectionTime = 1000;
	
	public final static int MAX_NODE = 8; // should be 1024

	public final static int MIN_IDENTIFIER = 0;

	public final static int MAX_IDENTIFIER = MAX_NODE;
	
	/**
	 * Each node maintains a routing table with (at most) m entries (where
	 * N=2^m) called finger table
	 * 
	 * 4 = 2^m => its 2.
	 */
	public static int MAX_ROUTE_TABLE_ENTRIES;

	public static void config(){
		MAX_ROUTE_TABLE_ENTRIES = (int) log2(MAX_NODE);
	}

	public static double logb(double a, double b) {
		return Math.log(a) / Math.log(b);
	}

	public static double log2(double a) {
		return logb(a, 2);
	}
}
