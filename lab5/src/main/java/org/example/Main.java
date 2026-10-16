package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Book hobbit = new Book("The Hobbit");
        Book dune = new Book("Dune");
        Book cleanCode = new Book("Clean Code");

        Catalog catalog = new Catalog("Library", List.of(hobbit, dune, cleanCode));
        System.out.println("Catalog: " + catalog.getName());

        Catalog.IssueRecord first = catalog.issueBook(hobbit, "Ya", LocalDate.of(2026, 10, 1));
        first.returnBook(LocalDate.of(2026, 10, 15));

        catalog.issueBook(hobbit, "Ne Ya", LocalDate.of(2026, 10, 20));
        catalog.issueBook(dune, "On", LocalDate.of(2026, 10, 5));

        Catalog.IssueRecord cleanCodeRecord =
                catalog.issueBook(cleanCode, "Ne Ya", LocalDate.of(2026, 9, 25));
        cleanCodeRecord.returnBook(LocalDate.of(2026, 10, 2));

        try {
            catalog.issueBook(hobbit, "Ona", LocalDate.of(2026, 10, 22));
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nFull history:");
        catalog.getHistory().forEach(System.out::println);

        List<Catalog.IssueRecord> notReturned = catalog.findRecords(new RecordFilter() {
            @Override
            public boolean test(Catalog.IssueRecord record) {
                return !record.isReturned();
            }
        });
        System.out.println("\nNot returned:");
        notReturned.forEach(System.out::println);

        List<Catalog.IssueRecord> petrovRecords = catalog.findRecords(record -> record.getReader().equals("Ne Ya"));
        System.out.println("\nNe Ya's records:");
        petrovRecords.forEach(System.out::println);

        List<Catalog.IssueRecord> sorted = new ArrayList<>(catalog.getHistory());
        sorted.sort(new Comparator<Catalog.IssueRecord>() {
            @Override
            public int compare(Catalog.IssueRecord a, Catalog.IssueRecord b) {
                return b.getIssueDate().compareTo(a.getIssueDate());
            }
        });
        System.out.println("\nSorted by issue date (newest first):");
        sorted.forEach(System.out::println);
    }
}