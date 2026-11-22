package model;

public class Symbol {
    private final char value;

    public Symbol(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public boolean isLetter() {
        return Character.isLetter(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
