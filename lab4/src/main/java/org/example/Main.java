package org.example;

import io.StoneFileReader;
import model.Stone;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        StoneFileReader reader = new StoneFileReader();
        List<Stone> stones = reader.readStones(Path.of("src/main/resources/stones.txt"));
        stones.forEach(System.out::println);
    }
}