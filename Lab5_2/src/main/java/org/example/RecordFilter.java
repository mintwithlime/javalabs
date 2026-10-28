package org.example;

public interface RecordFilter {
    boolean test(Catalog.IssueRecord record);
}
