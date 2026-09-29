package factory;

import exception.InvalidStoneDataException;
import model.SemiPreciousStone;

public class SemiPreciousStoneFactory extends StoneFactory {

    @Override
    public SemiPreciousStone createStone(String nameStr, String weightStr, String priceStr,
                                         String transparencyStr, String extra1, String extra2) {
        try {
            String name = requireNonBlank(nameStr, "name");
            double weight = parseWeight(weightStr);
            double price = parsePrice(priceStr);
            int transparency = parseTransparency(transparencyStr);
            double hardnessMohs = Double.parseDouble(extra1);
            String region = requireNonBlank(extra2, "origin region");

            if (hardnessMohs < 1 || hardnessMohs > 10) {
                throw new IllegalArgumentException("hardness out of range: " + hardnessMohs);
            }

            return new SemiPreciousStone(name, weight, price, transparency, hardnessMohs, region);
        } catch (IllegalArgumentException err) {
            throw new InvalidStoneDataException("Invalid semi-precious stone data for '" + nameStr + "': " + err.getMessage(), err);
        }
    }
}