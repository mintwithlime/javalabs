package factory;

import exception.InvalidStoneDataException;
import model.Clarity;
import model.PreciousStone;

public class PreciousStoneFactory extends StoneFactory {

    @Override
    public PreciousStone createStone(String name, double weight, int price, int transparency,
                                     String extra1, String extra2) {
        try {
            Clarity clarity = Clarity.valueOf(extra1);
            boolean hasCertificate = parseBoolean(extra2);
            return new PreciousStone(name, weight,price,transparency,clarity,hasCertificate);
        } catch (IllegalArgumentException err) {
            throw new InvalidStoneDataException("Invalid precious stone data for '" + name + "': " + err.getMessage(), err);
        }
    }

    private boolean parseBoolean(String string) {
        if (string.equalsIgnoreCase("true")) return true;
        if (string.equalsIgnoreCase("false")) return false;
        throw new IllegalArgumentException("Invalid boolean value: " + string);
    }
}
