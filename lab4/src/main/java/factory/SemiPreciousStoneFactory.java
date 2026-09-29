package factory;

import exception.InvalidStoneDataException;
import model.SemiPreciousStone;

public class SemiPreciousStoneFactory extends StoneFactory {

    @Override
    public SemiPreciousStone createStone(String name, double weight, int price, int transparency,
                                         String extra1, String extra2) {
        try {
            double hardnessMohs = Double.parseDouble(extra1);
            return new SemiPreciousStone(name, weight, price, transparency, hardnessMohs, extra2);
        } catch (IllegalArgumentException err) {
            throw new InvalidStoneDataException("Invalid precious stone data for '" + name + "': " + err.getMessage(), err);
        }
    }
}