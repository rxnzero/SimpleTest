package com.dhlee.java8;

import java.util.StringJoiner;

public class StringJoinerTest {

	public StringJoinerTest() {
	}

	public static void main(String[] args) {
		simpleJoin() ;
		closedJoin() ;
	}
	
	public static void simpleJoin() {
		StringJoiner stringJoiner = new StringJoiner(",");
		stringJoiner.add("hello");
		stringJoiner.add("string");
		stringJoiner.add("joiner");
		
		System.out.println(stringJoiner.toString());
	}
	
	public static void closedJoin() {
		StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
		stringJoiner.add("hello");
		stringJoiner.add("string");
		stringJoiner.add("joiner");
		
		System.out.println(stringJoiner.toString());
	}
}
