package org.example;

import java.util.*;
import java.util.stream.Collectors;

//18. В тексте определить все согласные буквы, встречающиеся не более чем в двух словах.
public class Task18A {
    private static final String CONSONANTS = "[b-df-hj-np-tv-z]";

    public static void main(String[] args) {
        String text = "Dad went out for \n Some milk today";
        System.out.println(findWithString(text));
        System.out.println();
        System.out.println(findWithStream(text));
    }

    static Set<Character> distinctConsonants(String word) {
        Set<Character> consonants = new HashSet<>();
        String lower = word.toLowerCase();

        for (char c : lower.toCharArray()) {
            if (String.valueOf(c).matches(CONSONANTS)) {
                consonants.add(c);
            }
        }
        return consonants;
    }

    //With String
    static List<Character> findWithString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }

        String[] words = text.split("\\P{L}+");
        Map<Character, Integer> countWords = new HashMap<>();
        List<Character> consonants = new ArrayList<>();

        for (String word : words) {
            Set<Character> chars = distinctConsonants(word);
            for (Character c : chars) {
                countWords.put(c, countWords.getOrDefault(c, 0) + 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : countWords.entrySet()) {
            if (entry.getValue() <= 2) {
                consonants.add(entry.getKey());
            }
        }

        Collections.sort(consonants);
        return consonants;
    }

    //With Stream
    static List<Character> findWithStream(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }

        return Arrays.stream(text.split("\\P{L}+"))
                .flatMap(word -> distinctConsonants(word).stream())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() <= 2)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }
}