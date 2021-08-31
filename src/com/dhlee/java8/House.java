package com.dhlee.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
enum Type {
    COTTAGE,
    COLONIAL,
    FARMHOUSE,
    FRENCH_COUNTRY,
    CAPE_GOD
}
 
public class House {
    private final int id;
    private final String name;
    private final String type;
 
    private House(final int id, final String name, final String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
 
    public static List<House> createHouses() {
        final List<House> houses = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final Type type = Type.values()[new Random().nextInt(5)];
            houses.add(new House(i, "house-" + i, type.name()));
        }
        return houses;
    }
 
    public int getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public String getType() {
        return type;
    }
 
    @Override
    public String toString() {
        return "House {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
