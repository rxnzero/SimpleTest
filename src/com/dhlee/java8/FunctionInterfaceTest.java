package com.dhlee.java8;

public class FunctionInterfaceTest {

	public FunctionInterfaceTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		 Consumer<Integer> c = count -> {
	            for (int i = 0; i < count; i++)
	                System.out.println(i);
	        };
	        c.accept(10);
	        
	        Consumer<String> c2 = message -> {
	            System.out.println(message + " too");
	        };
	        c2.accept("Hi");

	}

}
