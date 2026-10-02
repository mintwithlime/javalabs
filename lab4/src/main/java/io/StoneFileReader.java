package io;

import exception.InvalidStoneDataException;
import factory.PreciousStoneFactory;
import factory.SemiPreciousStoneFactory;
import factory.StoneFactory;
import model.Stone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StoneFileReader {
    private static final Logger logger = LogManager.getLogger(StoneFileReader.class);
    private final Map<String, StoneFactory> factories = Map.of(
            "PRECIOUS", new PreciousStoneFactory(),
            "SEMI", new SemiPreciousStoneFactory()
    );

    public List<Stone> readStones(Path path) throws IOException {
        List<Stone> stones = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);
        logger.info("Read file: {}", path);

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                continue;
            }

            String[] tokens = Arrays.stream(line.split(";", -1))
                    .map(String::trim)
                    .toArray(String[]::new);

            if (tokens.length != 7) {
                logger.warn("Line {} skipped: expected 7 fields, got {}", i + 1, tokens.length);
                continue;
            }

            StoneFactory factory = factories.get(tokens[0]);
            if (factory == null) {
                logger.warn("Line {} skipped: unknown stone type {}", i + 1, tokens[0]);
                continue;
            }

            try {
                Stone stone = factory.createStone(tokens[1], tokens[2], tokens[3],
                        tokens[4], tokens[5], tokens[6]);
                stones.add(stone);
                logger.debug("Line {}: created {}", i + 1, stone);
            } catch (InvalidStoneDataException err) {
                logger.warn("Line {} skipped: {}", i + 1, err.getMessage());
            }
        }
        logger.info("Loaded {} stones from {}", stones.size(), path);
        return stones;
    }
}
