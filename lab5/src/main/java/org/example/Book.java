package org.example;

public class Book {
    private final String title;

    protected Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}