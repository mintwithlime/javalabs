package parserAndService;

import model.Lexeme;
import model.Paragraph;
import model.PunctuationMark;
import model.Sentence;
import model.Text;
import model.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
    private static final Pattern SPACES = Pattern.compile("[ \\t]+");
    private static final Pattern PARAGRAPH_BREAK = Pattern.compile("\\R+");
    private static final Pattern SENTENCE_BREAK = Pattern.compile("(?<=[.!?][’\"»)]?)\\s+");
    private static final Pattern LEXEME = Pattern.compile("(\\w+(?:['’]\\w+)*)|([^\\s\\w])");

    public static Text parseText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }

        String normalized = SPACES.matcher(text).replaceAll(" ");

        List<Paragraph> paragraphs = Arrays.stream(PARAGRAPH_BREAK.split(normalized))
                .map(String::trim)
                .filter(paragraph -> !paragraph.isEmpty())
                .map(TextParser::parseParagraph)
                .toList();

        return new Text(paragraphs);
    }

    static Paragraph parseParagraph(String paragraph) {
        List<Sentence> sentences = Arrays.stream(SENTENCE_BREAK.split(paragraph))
                .map(String::trim)
                .filter(sentence -> !sentence.isEmpty())
                .map(TextParser::parseSentence)
                .toList();

        return new Paragraph(sentences);
    }

    static Sentence parseSentence(String sentence) {
        Matcher matcher = LEXEME.matcher(sentence);
        List<Lexeme> lexemes = new ArrayList<>();

        while (matcher.find()) {
            int start = matcher.start();
            boolean spaceBefore = start > 0 && sentence.charAt(start - 1) == ' ';

            if (matcher.group(1) != null) {
                lexemes.add(new Word(matcher.group(1), spaceBefore));
            } else {
                lexemes.add(new PunctuationMark(matcher.group(2).charAt(0), spaceBefore));
            }
        }
        return new Sentence(lexemes);
    }
}