package model;

public class PunctuationMark implements Lexeme {
    private final Symbol mark;
    private final boolean spaceBefore;

    public PunctuationMark(char mark, boolean spaceBefore) {
        if (Character.isLetterOrDigit(mark) || Character.isWhitespace(mark)) {
            throw new IllegalArgumentException("punctuation mark cannot contain letters or digits or space");
        }

        this.mark = new Symbol(mark);
        this.spaceBefore = spaceBefore;
    }

    @Override
    public String getValue() {
        return mark.toString();
    }

    @Override
    public boolean hasSpaceBefore() {
        return spaceBefore;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
