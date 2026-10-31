package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Task6ATest {

    @Test
    void deletesSpecialSymbolEverywhere() {
        String text = "Hello. World.\nJava is fun.";
        String expected = "Hello World\nJava is fun";

        assertEquals(expected, Task6A.processWithString(text, 0, '.', 0));
        assertEquals(expected, Task6A.processWithStream(text, 0, '.', 0));
    }

    @Test
    void insertsSymbolAfterKthCharacterInEveryLine() {
        String text = "hello\nworld\na";
        String expected = "he*llo\nwo*rld\na";

        assertEquals(expected, Task6A.processWithString(text, 1, '*', 2));
        assertEquals(expected, Task6A.processWithStream(text, 1, '*', 2));
    }

    @Test
    void insertsDollarSignLiterally() {
        assertEquals("a$b", Task6A.processWithString("ab", 1, '$', 1));
    }

    @Test
    void bothMethodsKeepTrailingEmptyLines() {
        String text = "abc\n\n";

        assertEquals("ac\n\n", Task6A.processWithString(text, 0, 'b', 0));
        assertEquals("ac\n\n", Task6A.processWithStream(text, 0, 'b', 0));
    }

    @Test
    void invalidFlagThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Task6A.processWithString("abc", 2, 'a', 0));
    }

    @Test
    void negativeKThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Task6A.processWithStream("abc", 1, 'a', -1));
    }
}