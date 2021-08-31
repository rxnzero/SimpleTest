package com.dhlee.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalTest {

	public OptionalTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
//		emptyTest();
//		stringOptions( "12345" );
//		stringOptions( null );
		
		List<String> list = null;
		System.out.println(">> NULL"); 
		listOptions(list); 
		
		String[] arr = {"hello", "Oprions"};
		System.out.println(">> ArrayList");
		list = new ArrayList( Arrays.asList(arr) );
		listOptions(list);
	}
	
	public static void emptyTest() {
		Optional<String> optional = Optional.empty();
		System.out.println(optional); // Optional.empty
		System.out.println(optional.isPresent()); // false		
	}

	public static void stringOptions(String value) {
		Optional<String> optional = Optional.ofNullable(value);
		String result = optional.orElse("other"); // 값이 없다면 "other" 를 리턴
		System.out.println(result);
	}
	
	public static void listOptions(List<String> list) {
		List<String> listOpt = Optional.ofNullable(list).orElseGet(() -> new ArrayList<>());
		listOpt .stream().forEach(System.out::println);
	}
}
