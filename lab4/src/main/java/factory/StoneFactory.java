package factory;

import model.Stone;

public abstract class StoneFactory {
    public abstract Stone createStone(String nameStr, String weightStr, String priceStr,
                                      String transparencyStr, String extra1, String extra2);

    protected String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value;
    }

    protected double parseWeight(String weightStr) {
        double weight = Double.parseDouble(weightStr);
        if (weight <= 0) {
            throw new IllegalArgumentException("weight cannot be non-positive: " + weight);
        }
        return weight;
    }

    protected double parsePrice(String priceStr) {
        double price = Double.parseDouble(priceStr);
        if (price <= 0) {
            throw new IllegalArgumentException("price cannot be non-positive: " + price);
        }
        return price;
    }

    protected int parseTransparency(String transpStr) {
        int transparency = Integer.parseInt(transpStr);
        if (transparency < 1 || transparency > 5) {
            throw new IllegalArgumentException("transparency out of range: " + transparency);
        }
        return transparency;
    }
 }
