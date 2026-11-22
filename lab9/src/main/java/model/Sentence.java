package model;

import java.util.List;

public class Sentence {
    private final List<Lexeme> lexemes;

    public Sentence(List<Lexeme> lexemes) {
        if (lexemes == null ||  lexemes.isEmpty()) {
            throw new IllegalArgumentException("lexemes cannot be null or empty");
        }
        this.lexemes = List.copyOf(lexemes);
    }

    public List<Lexeme> getLexemes() {
        return lexemes;
    }

    public List<Word> getWords() {
        return lexemes
                .stream()
                .filter(l -> l instanceof Word)
                .map(l -> (Word) l)
                .toList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme lexeme = lexemes.get(i);
            if (i > 0 && lexeme.hasSpaceBefore()) {
                sb.append(' ');
            }
            sb.append(lexeme.getValue());
        }

        return sb.toString();
    }
}
