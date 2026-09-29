package model;

public abstract class Stone {
    private String name;
    private double weight; //in carats
    private double price; //for 1 carat
    private int transparency; //form 1 to 5

    public Stone(String model, double weight, double price, int transparency) {
        this.name = model;
        this.weight = weight;
        this.price = price;
        this.transparency = transparency;
    }

    public String getModel() {
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
}

