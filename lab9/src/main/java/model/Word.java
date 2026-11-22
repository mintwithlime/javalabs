package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Word implements Lexeme {
    private final List<Symbol> symbols;
    private final boolean spaceBefore;

    public Word(String value, boolean hasSpaceBefore) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("word cannot be empty");
        }

        List<Symbol> symbols = new ArrayList<>();
        for (char c : value.toCharArray()) {
            symbols.add(new Symbol(c));
        }

        this.symbols = List.copyOf(symbols);
        this.spaceBefore = hasSpaceBefore;
    }

    public char getFirstLetter() {
        return Character.toLowerCase(symbols.getFirst().getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValue() {
        return symbols
                .stream()
                .map(Symbol::toString)
                .collect(Collectors.joining());
    }

    @Override
    public boolean hasSpaceBefore() {
        return spaceBefore;
    }
}
