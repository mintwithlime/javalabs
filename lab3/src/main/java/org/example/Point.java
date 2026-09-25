package org.example;

public class Point {
    private double x;
    private double y;
    final private double E = 0.001;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x=x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distToOrigin() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }   

    public double dist(Point p2) {
        return Math.sqrt((this.x-p2.x)*(this.x-p2.x) + (this.y-p2.y)*(this.y-p2.y));
    }

    public boolean isOnOneStraight(Point p1, Point p2) {
        double area = (p2.x - p1.x) * (this.y - p1.y) - (p2.y - p1.y) * (this.x - p1.x);
        return Math.abs(area) < E;
    }
}
