package model;

public class PreciousStone extends Stone {
    private Clarity clarityGrade;
    private boolean hasCertificate;

    public PreciousStone(String name, double weight, double price, int transparency,
                         Clarity clarityGrade, boolean hasCertificate) {
        super(name, weight, price, transparency);
        this.clarityGrade = clarityGrade;
        this.hasCertificate = hasCertificate;
    }

    public Clarity getClarityGrade() {
        return this.clarityGrade;
    }

    public boolean isHasCertificate() {
        return this.hasCertificate;
    }

    @Override
    public String toString() {
        return "PreciousStone {" +
                "name= '" + getName() + '\'' +
                ", weight= " + getWeight() + " ct" +
                ", price per ct= " + getPrice() +
                ", transparency= " + getTransparency() +
                ", clarityGrade= " + clarityGrade +
                ", hasCertificate= " + hasCertificate +
                '}';
    }
}