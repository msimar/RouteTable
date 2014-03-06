package com.mps.dsp.config;

import com.mps.dsp.core.NodeRegistry;

/**
 * Static configuration class containing some global variables.
 * 
 * @author msingh
 *
 */
public final class Configuration {
	/**
	 * Time to wait before retrying.
	 */
	public final static int retryConnectionTime = 1000;
	
	public static int MAX_NODE = -1; // should be 1024

	public final static int MIN_IDENTIFIER = 0;

	public static int MAX_IDENTIFIER = -1;
	
	/**
	 * Each node maintains a routing table with (at most) m entries (where
	 * N=2^m) called finger table
	 * 
	 * 4 = 2^m => its 2.
	 */
	public static int MAX_ROUTE_TABLE_ENTRIES;
	
	/**
	 * Coordinator Backlog as a  requested maximum length of the queue 
	 * of incoming connections
	 */
	public static final int SERVER_BACKLOG = 10; 

	/**
	 * Configure the network structure for the App.
	 * 
	 * Configure MAX_ROUTE_TABLE_ENTRIES as per the 
	 * arithmetic modulo of M, Configure the MAX Nodes
	 */
	public static void config(){
		
		MAX_NODE = NodeRegistry.getInstance().getNodesMap().size();
		
		MAX_IDENTIFIER = MAX_NODE;
		
		MAX_ROUTE_TABLE_ENTRIES = (int) log2(MAX_NODE);
	}

	private static double logb(double a, double b) {
		return Math.log(a) / Math.log(b);
	}

	private static double log2(double a) {
		return logb(a, 2);
	}
}
