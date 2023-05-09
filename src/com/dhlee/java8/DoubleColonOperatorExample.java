package com.dhlee.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class SomeClass {
	 
    public static void log(List<String> items) {
        items.forEach(e -> System.out.println(e));
    }
}

class SomeClass2 {
    public int getRandomResult() {
        return new Random().nextInt(1000);
    }
}

class PersonV1 {
    UUID id;
    String name;
     
    public PersonV1(UUID pid, String pname) {
        this.id = pid;
        this.name = pname;
    }
     
    public UUID getId() {
        return id;
    }
     
    public String getName() {
        return name;
    }
     
    public void print() {
        System.out.println("person id= " + this.id + ", person name= " + this.name);
    }
}

class Subject {
    
    String name;
     
    public Subject(String sname) {
        this.name = sname;
    }
     
    public String getName() {
        return name;
    }
}

public class DoubleColonOperatorExample {

	public DoubleColonOperatorExample() {

	}

	public static void main(String[] args) {
		System.out.println(">> Start consumer example...");
        List<String> items = Arrays.asList("physics", "chemistry", "maths", "zoology", "biology");
        
        // referencing method to List<String> type consumer interface
        Consumer<List<String>> consumer1 = SomeClass::log;
        consumer1.accept(items);    // calling consumer method

        System.out.println(">> Start supplier example...");
        SomeClass2 object = new SomeClass2();
        // reference method to Integer type supplier interface
        Supplier<Integer> supplier = object::getRandomResult;
        System.out.println(supplier.get());     // calling supplier method
        
        System.out.println(">> Start arbitrary object of a particular type example...");
        List<PersonV1> persons = Arrays.asList(
                new PersonV1(UUID.randomUUID(), "Person1"),
                new PersonV1(UUID.randomUUID(), "Person2"),
                new PersonV1(UUID.randomUUID(), "Person3"),
                new PersonV1(UUID.randomUUID(), "Person4"));
         
        persons.forEach(PersonV1::print);
        
        System.out.println(">> Start constructor example...");
        Function<String, Subject> function = (subject) -> new Subject(subject);
        
        String subjectName = function.apply("maths").getName();
         
        System.out.println(subjectName);
	}

}
