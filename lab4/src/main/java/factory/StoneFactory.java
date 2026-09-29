package factory;

import model.Stone;

public abstract class StoneFactory {
    public abstract Stone createStone(String name, double weight, int price,
                                      int transparency, String extra1, String extra2);
}
