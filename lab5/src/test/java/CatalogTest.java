import org.example.Book;
import org.example.Catalog;
import org.example.RecordFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogTest {
    private static final LocalDate OCT_1 = LocalDate.of(2026, 10, 1);
    private static final LocalDate OCT_15 = LocalDate.of(2026, 10, 15);

    private Book hobbit;
    private Book dune;
    private Catalog catalog;

    @BeforeEach
    void setUp() {
        hobbit = new Book("The Hobbit");
        dune = new Book("Dune");
        catalog = new Catalog("City Library", List.of(hobbit, dune));
    }

    // ---------- catalog ----------

    @Test
    void getNameReturnsName() {
        assertEquals("City Library", catalog.getName());
    }

    @Test
    void catalogCopiesBookList() {
        List<Book> books = new ArrayList<>(List.of(hobbit));
        Catalog lib = new Catalog("Lib", books);

        books.add(dune);

        assertThrows(IllegalArgumentException.class,
                () -> lib.issueBook(dune, "Ivanov", OCT_1));
    }

    @Test
    void addedBookCanBeIssued() {
        Book cleanCode = new Book("Clean Code");
        catalog.addBook(cleanCode);

        Catalog.IssueRecord record = catalog.issueBook(cleanCode, "Ivanov", OCT_1);

        assertEquals(cleanCode, record.getBook());
    }

    // ---------- issuing ----------

    @Test
    void issueBookCreatesRecordWithGivenData() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_1);

        assertEquals(1, record.getId());
        assertEquals(hobbit, record.getBook());
        assertEquals("Ivanov", record.getReader());
        assertEquals(OCT_1, record.getIssueDate());
        assertNull(record.getReturnDate());
        assertFalse(record.isReturned());
    }

    @Test
    void issueBookAssignsSequentialIds() {
        Catalog.IssueRecord first = catalog.issueBook(hobbit, "Ivanov", OCT_1);
        Catalog.IssueRecord second = catalog.issueBook(dune, "Petrov", OCT_1);

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void issueBookThrowsWhenBookNotInCatalog() {
        Book unknown = new Book("Unknown");

        assertThrows(IllegalArgumentException.class,
                () -> catalog.issueBook(unknown, "Ivanov", OCT_1));
    }

    @Test
    void issueBookThrowsWhenBookAlreadyIssued() {
        catalog.issueBook(hobbit, "Ivanov", OCT_1);

        assertThrows(IllegalStateException.class,
                () -> catalog.issueBook(hobbit, "Petrov", OCT_15));
    }

    @Test
    void failedIssueDoesNotConsumeId() {
        catalog.issueBook(hobbit, "Ivanov", OCT_1);
        assertThrows(IllegalStateException.class,
                () -> catalog.issueBook(hobbit, "Petrov", OCT_1));

        Catalog.IssueRecord next = catalog.issueBook(dune, "Sidorov", OCT_1);

        assertEquals(2, next.getId());
    }

    @Test
    void returnedBookCanBeIssuedAgain() {
        Catalog.IssueRecord first = catalog.issueBook(hobbit, "Ivanov", OCT_1);
        first.returnBook(OCT_15);

        Catalog.IssueRecord second = catalog.issueBook(hobbit, "Petrov", OCT_15);

        assertEquals("Petrov", second.getReader());
        assertEquals(2, catalog.getHistory().size());
    }

    @Test
    void failedIssueIsNotAddedToHistory() {
        assertThrows(IllegalArgumentException.class,
                () -> catalog.issueBook(new Book("Unknown"), "Ivanov", OCT_1));

        assertTrue(catalog.getHistory().isEmpty());
    }

    // ---------- returning ----------

    @Test
    void returnBookSetsReturnDate() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_1);

        record.returnBook(OCT_15);

        assertTrue(record.isReturned());
        assertEquals(OCT_15, record.getReturnDate());
    }

    @Test
    void returnBookOnSameDayIsAllowed() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_1);

        record.returnBook(OCT_1);

        assertTrue(record.isReturned());
    }

    @Test
    void returnBookTwiceThrows() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_1);
        record.returnBook(OCT_15);

        assertThrows(IllegalStateException.class, () -> record.returnBook(OCT_15));
    }

    @Test
    void returnBookBeforeIssueDateThrows() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_15);

        assertThrows(IllegalArgumentException.class, () -> record.returnBook(OCT_1));
    }

    // ---------- history ----------

    @Test
    void historyKeepsRecordsInIssueOrder() {
        Catalog.IssueRecord first = catalog.issueBook(hobbit, "Ivanov", OCT_1);
        Catalog.IssueRecord second = catalog.issueBook(dune, "Petrov", OCT_1);

        assertEquals(List.of(first, second), catalog.getHistory());
    }

    @Test
    void historyCannotBeModifiedFromOutside() {
        catalog.issueBook(hobbit, "Ivanov", OCT_1);
        List<Catalog.IssueRecord> history = catalog.getHistory();

        assertThrows(UnsupportedOperationException.class, history::clear);
    }

    // ---------- search ----------

    @Test
    void findRecordsReturnsNotReturnedBooks() {
        Catalog.IssueRecord first = catalog.issueBook(hobbit, "Ivanov", OCT_1);
        first.returnBook(OCT_15);
        Catalog.IssueRecord second = catalog.issueBook(dune, "Petrov", OCT_1);

        List<Catalog.IssueRecord> result = catalog.findRecords(new RecordFilter() {
            @Override
            public boolean test(Catalog.IssueRecord record) {
                return !record.isReturned();
            }
        });

        assertEquals(List.of(second), result);
    }

    @Test
    void findRecordsByReader() {
        Catalog.IssueRecord first = catalog.issueBook(hobbit, "Petrov", OCT_1);
        catalog.issueBook(dune, "Ivanov", OCT_1);

        List<Catalog.IssueRecord> result =
                catalog.findRecords(record -> record.getReader().equals("Petrov"));

        assertEquals(List.of(first), result);
    }

    @Test
    void findRecordsReturnsEmptyListWhenNothingMatches() {
        catalog.issueBook(hobbit, "Ivanov", OCT_1);

        assertTrue(catalog.findRecords(record -> false).isEmpty());
    }

    // ---------- toString ----------

    @Test
    void toStringShowsNotYetForUnreturnedBook() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_1);

        assertEquals("#1 The Hobbit -> Ivanov, issued 2026-10-01, not returned",
                record.toString());
    }

    @Test
    void toStringShowsReturnDate() {
        Catalog.IssueRecord record = catalog.issueBook(hobbit, "Ivanov", OCT_1);
        record.returnBook(OCT_15);

        assertEquals("#1 The Hobbit -> Ivanov, issued 2026-10-01, returned on 2026-10-15",
                record.toString());
    }
}