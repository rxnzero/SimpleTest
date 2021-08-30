package com.dhlee.stream;

import java.util.stream.IntStream;

public class IntStreamTest {

	public IntStreamTest() {
	}

	public static void main(String[] args) {
		IntStream intStreamSingle = IntStream.of(10);
		printIntStream(intStreamSingle);
		
		IntStream intStreamMulti = IntStream.of(1, 10, 20, 30);
		printIntStream(intStreamMulti);
		
		IntStream intStreamRange = IntStream.range(1, 10);
		printIntStream(intStreamRange);
		
		IntStream intStreamRangeCloased = IntStream.rangeClosed(1, 10);
		printIntStream(intStreamRangeCloased);
		
		IntStream intStreamRandom = IntStream.generate( () -> {return(int) Math.random() * 5000;}).limit(5);;
		printIntStream(intStreamRandom);

		IntStream intStreamIter = IntStream.iterate(1000, i -> i + 2000).limit(5);
		printIntStream(intStreamIter);
	}
	
	public static void printIntStream(IntStream intStream) {
		System.out.println("\n>> printIntStream");
		intStream.forEach(i -> System.out.println(i));
	}
}
