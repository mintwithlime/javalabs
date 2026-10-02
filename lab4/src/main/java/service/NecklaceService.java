package service;

import comparator.StoneValueComparator;
import model.Necklace;
import model.Stone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NecklaceService {
    private static final Logger logger = LogManager.getLogger(NecklaceService.class);

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
                logger.debug("Stone added: {}, cost: {}, left: {}", stone.getName(), stone.getTotalPrice(), budget - spent);
            } else {
                logger.debug("Stone skipped: {}, cost: {}, budget left: {}",
                        stone.getName(), stone.getTotalPrice(), budget - spent);
            }
        }
        logger.info("Selected {} of {} stones, spent {} of budget {}",
                selectedStones.size(), stones.size(), spent, budget);
        return new Necklace(selectedStones);
    }
}

