import model.Text;
import model.Word;
import org.junit.jupiter.api.Test;
import parserAndService.TextParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextParserTest {

    @Test
    void replacesTabsAndSpacesWithOneSpace() {
        Text text = TextParser.parseText("Hello,\t  world.");

        assertEquals("Hello, world.", text.toString());
    }

    @Test
    void restoresTextWithPunctuationAndQuotes() {
        String source = "Hello, world. It’s ‘fun.’ Is it?";

        assertEquals(source, TextParser.parseText(source).toString());
    }

    @Test
    void splitsIntoParagraphsAndSentences() {
        Text text = TextParser.parseText("One. Two!\n\nThree?");

        assertEquals(2, text.getParagraphs().size());
        assertEquals(2, text.getParagraphs().getFirst().getSentences().size());
    }

    @Test
    void splitsSentenceThatEndsWithQuote() {
        Text text = TextParser.parseText("He said ‘go.’ Then he left.");

        assertEquals(2, text.getParagraphs().getFirst().getSentences().size());
    }

    @Test
    void wordWithApostropheIsOneWord() {
        List<String> words = TextParser.parseText("It’s fun, don’t worry.")
                .getWords()
                .stream()
                .map(Word::getValue)
                .toList();

        assertEquals(List.of("It’s", "fun", "don’t", "worry"), words);
    }

    @Test
    void emptyTextThrows() {
        assertThrows(IllegalArgumentException.class, () -> TextParser.parseText("   \n  "));
    }
}