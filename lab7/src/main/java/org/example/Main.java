package org.example;

import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ToDoubleFunction;

public class Main {
    public static void main(String[] args) {
        //TASK 6 Написать функциональный интерфейс с методом,
        // который принимает три дробных числа: a, b, c и возвращает тоже дробное число.
        // Написать реализацию такого интерфейса в виде лямбда-выражения,
        // которое возвращает дискриминант.
        TriDoubleFunction discriminant =
                (a, b, c) -> b * b - 4 * a * c;

        ToDoubleFunction<double[]> discriminant2 =
                (array) -> array[1] * array[1] - 4 * array[0] * array[2];

        System.out.println(discriminant.apply(1, -3, 2));
        System.out.println(discriminant2.applyAsDouble(new double[]{1, -3, 2}));

        //TASK 18 Продемонстрировать работу лямбда-выражения,
        // которое получает входным параметром целое
        // число x и вычисляет количество вхождений заданной цифры в этом числе.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter digit: ");
        int digit = scanner.nextInt();
        if (digit < 0 || digit > 9) {
            System.out.println("Invalid input");
            return;
        }

        DigitCounter counter = value -> countDigit(value, digit);

        System.out.print("Enter number: ");
        int x = scanner.nextInt();
        System.out.println(counter.count(x));

        IntUnaryOperator counter2 = value -> countDigit(value, digit);
        System.out.println(counter2.applyAsInt(x));

        //TASK 30 Последовательность координат вершин многоугольника задана массивом чисел.
        // Определить, лежит ли точка внутри многоугольника.
        double[] square = {0, 0, 4, 0, 4, 4, 0, 4};

        PointChecker checker = (px, py) -> isInside(square, px, py);
        BiPredicate<Double, Double> checker2 = (px, py) -> isInside(square, px, py);

        System.out.println(checker.contains(2, 2));
        System.out.println(checker.contains(5, 2));
        System.out.println(checker2.test(2.0, 2.0));
        System.out.println(checker2.test(5.0, 2.0));
    }

    static int countDigit(int x, int digit) {
        int count = 0;
        int n = Math.abs(x);
        if (n == 0) {
            return digit == 0 ? 1 : 0;
        }

        while (n > 0) {
            if (n % 10 == digit) {
                count++;
            }
            n /= 10;
        }
        return count;
    }

    static boolean isInside(double[] polygon, double x, double y) {
        if (polygon == null || polygon.length % 2 != 0 || polygon.length < 6) {
            throw new IllegalArgumentException("polygon must contain at least 3 vertices (x, y pairs)");
        }

        int n = polygon.length / 2;
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon[2 * i];
            double yi = polygon[2 * i + 1];
            double xj = polygon[2 * j];
            double yj = polygon[2 * j + 1];

            if ((yi > y) != (yj > y)) {
                double xCross = xi + (y - yi) * (xj - xi) / (yj - yi);
                if (x < xCross) {
                    inside = !inside;
                }
            }
        }
        return inside;
    }
}

