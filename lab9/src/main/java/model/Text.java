package model;

import java.util.List;
import java.util.stream.Collectors;

public class Text {
    private final List<Paragraph> paragraphs;

    public Text(List<Paragraph> paragraphs) {
        if (paragraphs == null || paragraphs.isEmpty()) {
            throw new IllegalArgumentException("paragraphs must not be null or empty");
        }
        this.paragraphs = List.copyOf(paragraphs);
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public List<Word> getWords() {
        return paragraphs
                .stream()
                .flatMap(p -> p.getWords().stream())
                .toList();
    }

    @Override
    public String toString() {
        return paragraphs
                .stream()
                .map(Paragraph::toString)
                .collect(Collectors.joining("\n"));
    }
}
