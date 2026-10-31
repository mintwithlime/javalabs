package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class Task18ATest {

    @Test
    void distinctConsonantsReturnsEachLetterOnce() {
        assertEquals(Set.of('l', 't', 'r'), Task18A.distinctConsonants("Letter"));
    }

    @Test
    void findsConsonantsInAtMostTwoWords() {
        String text = "Bob met Tom at the market";
        List<Character> expected = List.of('b', 'h', 'k', 'r');

        assertEquals(expected, Task18A.findWithString(text));
        assertEquals(expected, Task18A.findWithStream(text));
    }

    @Test
    void countsWordsNotOccurrences() {
        String text = "letter better batter";
        List<Character> expected = List.of('b', 'l');

        assertEquals(expected, Task18A.findWithString(text));
        assertEquals(expected, Task18A.findWithStream(text));
    }

    @Test
    void ignoresCaseAndPunctuation() {
        String text = "Bob, bob! BOB...";

        assertEquals(List.of(), Task18A.findWithString(text));
        assertEquals(List.of(), Task18A.findWithStream(text));
    }

    @Test
    void textWithoutConsonantsGivesEmptyList() {
        assertTrue(Task18A.findWithString("a e i o u").isEmpty());
        assertTrue(Task18A.findWithStream("").isEmpty());
    }

    @Test
    void nullTextThrows() {
        assertThrows(IllegalArgumentException.class, () -> Task18A.findWithString(null));
        assertThrows(IllegalArgumentException.class, () -> Task18A.findWithStream(null));
    }
}