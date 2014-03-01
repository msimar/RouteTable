package com.mps.dsp.test;

import java.util.HashSet;

import com.mps.dsp.core.Util;

public class HashTest {
	
	static HashSet<Integer> hset = new HashSet<Integer>();
	
	public static void main(String[] args) {
//		System.out.println(Util.hash(845)%1024);
//		System.out.println(Util.hash(1024)%1024);
//		System.out.println(Util.hash(2)%1024);
//		System.out.println(Util.hash(67)%1024);
//		System.out.println(Util.hash(517)%1024);
//		System.out.println(Util.hash(634)%1024);
		int dupCounter = 0;
		for(int i = 0; i <1024; i++)
		{
			int val = Util.hash(i) % 37;
			System.out.println(val);
			
			if( !hset.add(val) ){
				System.out.println("-------- dup " + val );
				dupCounter++;
			}
		}
		
		System.out.println("-------- dupCounter " + dupCounter );
	}
}	
