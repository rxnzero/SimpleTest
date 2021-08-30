package com.dhlee.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BiConsumerTest {

	public BiConsumerTest() {
	}
	
	private static void printSingle() {
		final Consumer<String> printSingle
			= (val1) -> System.out.println(val1);
		Stream<String> techs = Stream.of("java", "j2ee", "openapi", "bigdata");
		techs.forEach(printSingle);
	}
	
	private static void supplier() {
		Supplier<Double> doubleSupplier1 
			= ( ) -> Math.random();
		DoubleSupplier	doubleSupplier2 = Math::random;
		
		System.out.println("doubleSupplier1 = " + doubleSupplier1.get());
		System.out.println("doubleSupplier2 = " + doubleSupplier2.getAsDouble());
	}
	
	private static void basic() {
		final BiConsumer<String, String> concat 
			= (val1, val2) -> System.out.println(val1 + " " + val2);
		concat.accept("Hello", "BiConsumer");	
	}
	
	private static void printMap() {
		final Map<Integer, String> map = new HashMap<>();
		map.put(1, "Java");
		map.put(2, "j2ee");
		map.put(3, "eai");
		map.put(4, "openapi");
		
		final BiConsumer<Integer, String> print 
			= (val1, val2) -> System.out.println(val1 + " " + val2);
		map.forEach(print);
	}
	
	private static void andThen() {
		final BiConsumer<Integer, Integer> addition
			= (val1, val2) -> System.out.println("Sum : " + (val1 + val2) );
		final BiConsumer<Integer, Integer> subtraction
			= (val1, val2) -> System.out.println("subtraction : " + (val1 - val2) );
		addition.andThen(subtraction).accept(10, 5);
	}
	public static void main(String[] args) {
		System.out.println("BiConsumerTest START.");
		
		System.out.println(">> printSingle");
		printSingle();
		System.out.println(">> supplier");
		supplier();

		System.out.println(">> basic");
		basic();
		System.out.println(">> printMap");
		printMap();
		System.out.println(">> andThen");
		andThen();
	}
	
	
	

}
