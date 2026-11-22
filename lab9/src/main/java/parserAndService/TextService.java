package parserAndService;

import model.Text;
import model.Word;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TextService {

    public static String wordsByFirstLetter(Text text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }

        Map<Character, List<String>> groups = text.getWords()
                .stream()
                .map(Word::getValue)
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.groupingBy(
                        word -> word.charAt(0),
                        TreeMap::new,
                        Collectors.toList()));

        return groups.values()
                .stream()
                .map(group -> "    " + String.join(" ", group))
                .collect(Collectors.joining("\n"));
    }
}