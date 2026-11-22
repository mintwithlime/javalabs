package org.example;

import model.Text;
import parserAndService.TextFileHandler;
import parserAndService.TextParser;
import parserAndService.TextService;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    private static final Path INPUT_PATH = Path.of("src/main/resources/input.txt");
    private static final Path OUTPUT_PATH = Path.of("output.txt");

    public static void main(String[] args) {
        try {
            String content = TextFileHandler.read(INPUT_PATH);

            Text text = TextParser.parseText(content);
            String result = TextService.wordsByFirstLetter(text);

            System.out.println(result);
            TextFileHandler.write(OUTPUT_PATH, result);
            System.out.println("\nResult saved to " + OUTPUT_PATH.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid text: " + e.getMessage());
        }
    }
}