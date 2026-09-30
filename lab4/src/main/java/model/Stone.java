package model;

public abstract class Stone {
    private final String name;
    private final double weight; // in carats
    private final double price; // for 1 carat
    private final int transparency; // from 1 to 5

    protected Stone(String name, double weight, double price, int transparency) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.transparency = transparency;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getPrice() {
        return this.price;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public double getTotalPrice() {
        return this.price * this.weight;
    }
}

