package com.mps.dsp.core;

/**
 * 
 * hash(key) :: Stackoveflow Reference ::
 * http://stackoverflow.com/questions/664014
 * /what-integer-hash-function-are-good-that-accepts-an-integer-hash-key
 * 
 * http://people.cs.umass.edu/~arun/653/lectures/L10.pdf
 * 
 * @author msingh
 * 
 */
public class Util {

	public static final int INVALID_INDEX = -1;
	
	public static final int MAX_NODE = 16;

	public static final int MIN_IDENTIFIER = 0;

	public static final int MAX_IDENTIFIER = MAX_NODE;

	/**
	 * Each node maintains a routing table with (at most) m entries (where N=2^m)
	 * called finger table
	 * 
	 * 4 = 2^m => its 2. 
	 */
	public static final int MAX_ROUTE_TABLE_ENTRIES = 4; 

	public static int hash(int key) {
		key = ((key >> 16) ^ key) * 0x45d9f3b;
		key = ((key >> 16) ^ key) * 0x45d9f3b;
		key = ((key >> 16) ^ key);
		return key;
	}
}
