package org.example;

import io.StoneFileReader;
import model.Necklace;
import model.Stone;
import service.NecklaceService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        StoneFileReader reader = new StoneFileReader();
        List<Stone> stones = reader.readStones(Path.of("src/main/resources/stones.txt"));
        stones.forEach(System.out::println);

        NecklaceService service = new NecklaceService();
        Necklace necklace = service.selectStones(stones, 8000);

        System.out.println("Total weight: " + service.calculateTotalWeight(necklace));
        System.out.println("Total cost: " + service.calculateTotalCost(necklace));
        service.sortByValue(necklace).forEach(System.out::println);
        service.findByTransparency(necklace, 3, 5).forEach(System.out::println);
    }
}