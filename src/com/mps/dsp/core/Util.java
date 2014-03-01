package com.mps.dsp.core;

/**
 * 
 * hash(key) :: Stackoveflow
 * Reference :: http://stackoverflow.com/questions/664014/what-integer-hash-function-are-good-that-accepts-an-integer-hash-key
 * 
 * @author msingh
 *
 */
public class Util {
 
	public static final int MAX_NODE = 1024;

	public static int hash(int key) {
		key = ((key >> 16) ^ key) * 0x45d9f3b;
		key = ((key >> 16) ^ key) * 0x45d9f3b;
		key = ((key >> 16) ^ key);
		return key;
	}
}
