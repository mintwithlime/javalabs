package model;

public class SemiPreciousStone extends Stone {
    private final double hardnessMohs; // Mohs Hardness Scale (7.0)
    private final String originRegion;

    public SemiPreciousStone(String name, double weight, double price, int transparency,
                             double hardnessMohs, String originRegion) {
        super(name, weight, price, transparency);
        this.hardnessMohs = hardnessMohs;
        this.originRegion = originRegion;
    }


    public double getHardnessMohs() {
        return this.hardnessMohs;
    }

    public String getOriginRegion() {
        return this.originRegion;
    }


    @Override
    public String toString() {
        return "SemiPreciousStone {" +
                "name= '" + getName() + '\'' +
                ", weight= " + getWeight() + " ct" +
                ", price per ct= " + getPrice() +
                ", transparency= " + getTransparency() +
                ", hardnessMohs= " + hardnessMohs +
                ", originRegion= '" + originRegion + '\'' +
                '}';
    }
}
