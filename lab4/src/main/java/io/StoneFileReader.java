package io;

import exception.InvalidStoneDataException;
import factory.PreciousStoneFactory;
import factory.SemiPreciousStoneFactory;
import factory.StoneFactory;
import model.Stone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StoneFileReader {
    private final Map<String, StoneFactory> factories = Map.of(
            "PRECIOUS", new PreciousStoneFactory(),
            "SEMI", new SemiPreciousStoneFactory()
    );

    public List<Stone> readStones(Path path) throws IOException {
        List<Stone> stones = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                continue;
            }

            String[] tokens = Arrays.stream(line.split(";", -1))
                    .map(String::trim)
                    .toArray(String[]::new);

            if (tokens.length != 7) {
                System.out.println("Line " + (i + 1) + " skipped: expected 7 fields, got " + tokens.length);
                continue;
            }

            StoneFactory factory = factories.get(tokens[0]);
            if (factory == null) {
                System.out.println("Line " + (i + 1) + " skipped: unknown stone type '" + tokens[0] + "'");
                continue;
            }

            try {
                Stone stone = factory.createStone(tokens[1], tokens[2], tokens[3],
                        tokens[4], tokens[5], tokens[6]);
                stones.add(stone);
            } catch (InvalidStoneDataException err) {
                System.out.println("Line " + (i + 1) + " skipped: " + err.getMessage());
            }
        }
        return stones;
    }
}
