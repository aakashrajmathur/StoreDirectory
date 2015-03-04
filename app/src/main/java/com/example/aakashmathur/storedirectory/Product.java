package com.example.aakashmathur.storedirectory;

import java.util.Comparator;

/**
 * Created by aakashmathur on 1/17/15.
 */
public class Product implements Comparable<Product> {
    String name;
    String aisle;

    boolean selected;

    public Product(String name, String aisle, boolean selected){
        this.name = name;
        this.aisle = aisle;
        this.selected = selected;
    }

    @Override
    public int compareTo(Product another) {
        return this.name.compareToIgnoreCase(another.name);
    }
}
