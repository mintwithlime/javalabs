package org.example;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task6A {
    public static void main(String[] args) {
        String text = "Wow. Cool.\nAmazing and fun.\nOk";
        System.out.println(processWithString(text, 0, '.', 2));
        System.out.println();
        System.out.println(processWithStream(text, 0, '.', 2));
        System.out.println();
        System.out.println(processWithString(text, 1, '$', 2));
        System.out.println();
        System.out.println(processWithStream(text, 1, '$', 2));
    }

    //A) 6. В зависимости от признака (0 или 1) в каждой строке текста
    // удалить указанный символ везде, где он встречается,
    // или вставить его после k-гo символа.

    static String processLine(String line, int flag, char symbol, int k) {
        return switch (flag) {
            case 0 -> line.replaceAll(Pattern.quote(String.valueOf(symbol)), "");
            case 1 -> {
                if (k < 0) {
                    throw new IllegalArgumentException("k cannot be negative");
                }
                yield line.replaceFirst("^(.{" + k + "})",
                        "$1" + Matcher.quoteReplacement(String.valueOf(symbol)));
            }
            default -> throw new IllegalArgumentException("flag can only be 0 or 1");
        };
    }

    //By String methods
    static String processWithString(String text, int flag, char symbol, int k) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }

        String[] lines = text.split("\\R", -1);
        for (int i = 0; i < lines.length; i++) {
            lines[i] = processLine(lines[i], flag, symbol, k);
        }
        return String.join("\n", lines);
    }

    //By Stream
    static String processWithStream(String text, int flag, char symbol, int k) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }

        return Arrays.stream(text.split("\\R", -1))
                .map(line -> processLine(line, flag, symbol, k))
                .collect(Collectors.joining("\n"));
    }
}