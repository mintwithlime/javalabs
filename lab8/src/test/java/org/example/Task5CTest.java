package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Task5CTest {

    @Test
    void compressesExampleFromTask() {
        String text = "hellowoooorld";
        String expected = "hel2owo4rld";

        assertEquals(expected, Task5C.compressWithString(text));
        assertEquals(expected, Task5C.compressWithStream(text));
    }

    @Test
    void textWithoutRepeatsStaysTheSame() {
        assertEquals("abc", Task5C.compressWithString("abc"));
        assertEquals("abc", Task5C.compressWithStream("abc"));
    }

    @Test
    void longRunGivesMultiDigitCount() {
        String text = "aaaaaaaaaaaa";

        assertEquals("a12", Task5C.compressWithString(text));
        assertEquals("a12", Task5C.compressWithStream(text));
    }

    @Test
    void compressesRunsAtStartAndEnd() {
        assertEquals("a2bc2", Task5C.compressWithString("aabcc"));
        assertEquals("a2bc2", Task5C.compressWithStream("aabcc"));
    }

    @Test
    void lineBreaksAreKeptAndCompressed() {
        String text = "a\n\nb";
        String expected = "a\n2b";

        assertEquals(expected, Task5C.compressWithString(text));
        assertEquals(expected, Task5C.compressWithStream(text));
    }

    @Test
    void emptyTextGivesEmptyResult() {
        assertEquals("", Task5C.compressWithString(""));
        assertEquals("", Task5C.compressWithStream(""));
    }

    @Test
    void nullTextThrows() {
        assertThrows(IllegalArgumentException.class, () -> Task5C.compressWithString(null));
        assertThrows(IllegalArgumentException.class, () -> Task5C.compressWithStream(null));
    }
}