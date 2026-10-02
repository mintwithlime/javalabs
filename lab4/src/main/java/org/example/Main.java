package org.example;

import io.StoneFileReader;
import model.Necklace;
import model.Stone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.NecklaceService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final Path filePath = Path.of("src/main/resources/stones.txt");

    public static void main(String[] args) {
        try {
            StoneFileReader reader = new StoneFileReader();
            List<Stone> stones = reader.readStones(filePath);
            stones.forEach(stone -> logger.info("Loaded: {}", stone));

            NecklaceService service = new NecklaceService();
            Necklace necklace = service.selectStones(stones, 8000);

            logger.info("Total weight: {} ct", service.calculateTotalWeight(necklace));
            logger.info("Total cost: {}", service.calculateTotalCost(necklace));
            service.sortByValue(necklace)
                    .forEach(stone ->
                            logger.info("Sorted by value: {} ({})", stone.getName(), stone.getTotalPrice()));
            service.findByTransparency(necklace, 3, 5)
                    .forEach(stone ->
                            logger.info("Found by transparency: {}, tp: {}", stone.getName(), stone.getTransparency()));
        } catch (IOException err) {
            logger.error("Cannot read file: {}", filePath, err);
        }
    }
}