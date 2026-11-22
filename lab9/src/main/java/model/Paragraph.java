package model;

import java.util.List;
import java.util.stream.Collectors;

public class Paragraph {
    private final List<Sentence> sentences;

    public Paragraph(List<Sentence> sentences) {
        if (sentences == null || sentences.isEmpty()) {
            throw new IllegalArgumentException("sentences must not be null or empty");
        }
        this.sentences = List.copyOf(sentences);
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public List<Word> getWords() {
        return sentences
                .stream()
                .flatMap(s -> s.getWords().stream())
                .toList();
    }

    @Override
    public String toString() {
        return sentences
                .stream()
                .map(Sentence::toString)
                .collect(Collectors.joining(" "));
    }
}
