package org.example;

public class Cargo {
    private final double weight;
    private final boolean fragile;
    private final double price;
    private final String description;

    public Cargo(double weight, boolean fragile, double price, String description) {
        if (weight <= 0) throw new IllegalArgumentException("weight cannot be negative or 0");
        if (price <= 0) throw new IllegalArgumentException("price cannot be negative or 0");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("description cannot be null or empty");

        this.weight = weight;
        this.fragile = fragile;
        this.price = price;
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isFragile() {
        return fragile;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
