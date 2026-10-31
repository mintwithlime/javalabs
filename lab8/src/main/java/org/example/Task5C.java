package org.example;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task5C {
    private static final Pattern RUN = Pattern.compile("(?s)(.)\\1*");

    public static void main(String[] args) {
        String text = "letter better batter hellowooooorld";

        System.out.println(compressWithStream(text));
        System.out.println();
        System.out.println(compressWithString(text));
    }

    static String compressRun(String run) {
        if (run.length() >= 2) {
            return String.valueOf(run.charAt(0)) + run.length();
        }
        return run;
    }

    //With String
    static String compressWithString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }

        Matcher matcher = RUN.matcher(text);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            sb.append(compressRun(matcher.group()));
        }
        return sb.toString();
    }

    //With Stream
    static String compressWithStream(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }

        return RUN.matcher(text)
                .results()
                .map(MatchResult::group)
                .map(Task5C::compressRun)
                .collect(Collectors.joining());
    }
}
