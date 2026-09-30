package service;

import comparator.StoneValueComparator;
import model.Necklace;
import model.Stone;

import java.util.ArrayList;
import java.util.List;

public class NecklaceService {
    public double calculateTotalWeight(Necklace necklace) {
        return necklace.getStones().stream()
                .mapToDouble(Stone::getWeight)
                .sum();
    }

    public double calculateTotalCost(Necklace necklace) {
        return necklace.getStones().stream()
                .mapToDouble(Stone::getTotalPrice)
                .sum();
    }

    public List<Stone> sortByValue(Necklace necklace) {
        List<Stone> sortedStones = new ArrayList<>(necklace.getStones());
        sortedStones.sort(new StoneValueComparator().reversed());
        return sortedStones;
    }

    public List<Stone> findByTransparency(Necklace necklace, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min (" + min + ") cannot be greater than max (" + max + ")");
        }

        return necklace.getStones().stream()
                .filter(s -> min <= s.getTransparency() && s.getTransparency() <= max)
                .toList();
    }

        public Necklace selectStones(List<Stone> stones, double budget) {
            if (budget <= 0) {
                throw new IllegalArgumentException("budget cannot be non-positive: " + budget);
            }

            List<Stone> selectedStones = new ArrayList<>();
            double spent = 0;
            for (Stone stone : stones) {
                if (spent + stone.getTotalPrice() <= budget) {
                    selectedStones.add(stone);
                    spent += stone.getTotalPrice();
                }
            }
            return new Necklace(selectedStones);
        }
}

