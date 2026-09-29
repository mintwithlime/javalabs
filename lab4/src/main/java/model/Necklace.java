package model;

import java.util.List;

public class Necklace {
    private List<Stone> stones;

    public Necklace(List<Stone> stones) {
        this.stones = List.copyOf(stones);
    }

    public List<Stone> getStones() {
        return this.stones;
    }


}
