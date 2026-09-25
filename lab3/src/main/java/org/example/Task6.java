package org.example;

public class Task6 {
    public static void main(String[] args) {
        Point A = new Point(2.5,3.5);
        Point B = new Point(10.5, 4.5);
        Point C = new Point(6.5, 4.0);
        double dist = A.dist(B);
        double distOrgA = A.distToOrigin();
        double distOrgB = B.distToOrigin();
        boolean isOnC = C.isOnOneStraight(A,B);
        boolean isOnB = B.isOnOneStraight(A,C);
        boolean isOnA = A.isOnOneStraight(C,B);

        System.out.printf("%f, %f, %f\n", dist, distOrgA, distOrgB);
        System.out.printf("%b, %b, %b\n", isOnA, isOnB, isOnC);
    }
}
