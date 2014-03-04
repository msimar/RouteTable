package com.mps.dsp.util;

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

	public static int hash(int key) {
		key = ((key >> 16) ^ key) * 0x45d9f3b;
		key = ((key >> 16) ^ key) * 0x45d9f3b;
		key = ((key >> 16) ^ key);
		return key;
	}
}
