package com.dhlee.java8;

import java.util.Optional;

public class StreamTest {

	public StreamTest() {
	}

	/*
    Stream.findAny() - Returns an optional object containing any one element of the given stream.
    It can also return an empty() optional if the stream is empty.
    Optional<T> findAny()
    */
    private static void findAnyImplementation() {
        System.out.println("----- findAny() implementation -----");
        final Optional<House> optional = House.createHouses()
                .stream()
                .filter(house -> house.getType().equals("COTTAGE"))
                .findAny();
 
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println("No data");
        }
    }
 
    /*
    Stream.findFirst() - Returns an optional object containing first element of the given stream.
    It can also return an empty() optional if the stream is empty.
    Syntax :- Optional<T> findFirst()
    */
    private static void findFirstImplementation() {
        System.out.println("\n----- findFirst() implementation -----");
        final Optional<House> optional = House.createHouses()
                .stream()
                .filter(house -> house.getType().equals("FARMHOUSE"))
                .findFirst();
 
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println("No data");
        }
    }
 
    public static void main(String[] args) {
        findAnyImplementation();
        findFirstImplementation();
    }
}
