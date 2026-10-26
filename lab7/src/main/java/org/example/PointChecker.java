package org.example;

@FunctionalInterface
public interface PointChecker {
    boolean contains(double x, double y);
}