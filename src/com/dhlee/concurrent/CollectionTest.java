package com.dhlee.concurrent;

import java.util.Vector;

public class CollectionTest {

	public CollectionTest() {

	}

	public static void main(String[] args) {
		testVector();		
	}
	
	private static void testVector() {
		Vector<SimpleVO> newVector = new Vector<>();
		Vector<SimpleVO> oldVector = new Vector<>();
		
		SimpleVO simple1 = new SimpleVO("id-1","name-1");
		SimpleVO simple2 = new SimpleVO("id-1","name-1");
		
		newVector.add(simple1);
		oldVector.add(simple1);
		
		if(!newVector.contains(simple1)) newVector.add(simple1);
		if(oldVector.contains(simple2)) oldVector.remove(simple2);
		
		System.out.println("newVector.size = " + newVector.size());
		System.out.println("oldVector.size = " + oldVector.size());
		
	}
	
}
