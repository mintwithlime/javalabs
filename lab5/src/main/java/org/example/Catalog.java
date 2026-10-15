package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private final String name;
    private final List<Book> books;
    private final List<IssueRecord> history = new ArrayList<>();
    private int nextId = 1;

    public Catalog(String name, List<Book> books) {
        this.name = name;
        this.books = new ArrayList<>(books);
    }

    public class IssueRecord {
        private final int id;
        private final Book book;
        private final String reader;
        private final LocalDate issueDate;
        private LocalDate returnDate;

        public IssueRecord(Book book, String reader, LocalDate issueDate) {
            if (!books.contains(book)) {
                throw new IllegalArgumentException("Book is not in catalog: " + book.getTitle());
            }

            for (IssueRecord record : history) {
                if (record.book.equals(book) && !record.isReturned()) {
                    throw new IllegalStateException("Book is already issued: " + book.getTitle());
                }
            }

            this.id = nextId++;
            this.book = book;
            this.reader = reader;
            this.issueDate = issueDate;
            this.returnDate = null;
            history.add(this);
        }


        public Book getBook() {
            return book;
        }

        public int getId() {
            return id;
        }

        public String getReader() {
            return reader;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public LocalDate getIssueDate() {
            return issueDate;
        }

        public void returnBook(LocalDate date) {
            if (this.isReturned()) {
                throw new IllegalStateException("Book is already returned: " + book.getTitle());
            }
            if (date.isBefore(issueDate)) {
                throw new IllegalArgumentException("Return date cannot be before issue date");
            }

            this.returnDate = date;
        }

        public boolean isReturned() {
            return returnDate != null;
        }


        @Override
        public String toString() {
            return "#%d %s -> %s, issued %s, %s".formatted(
                    id, book.getTitle(), reader, issueDate,
                    isReturned() ? "returned on " + returnDate : "not returned");
        }
    }

    public IssueRecord issueBook(Book book, String reader,
                                 LocalDate date) {
        return new IssueRecord(book, reader, date);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public String getName() {
        return name;
    }

    public List<IssueRecord> getHistory() {
        return List.copyOf(this.history);
    }

    public List<IssueRecord> findRecords(RecordFilter filter) {
        return history.stream()
                .filter(filter::test)
                .toList();
    }
}