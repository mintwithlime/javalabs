/*6.
Для каждой строки матрицы найти сумму элементов матрицы,
расположенных между первым и вторым положительными элементами
каждой строки. Отсортировать строки матрицы по этой сумме. Строки, в
которых все элементы отрицательные — удалить. Строки у которых только
один положительный элемент — удалить.*/

import java.util.ArrayList;
import java.util.Scanner;

public class Task6 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int rows;
        int cols;
        System.out.print("Enter amount of rows: ");
        rows = in.nextInt();
        System.out.print("Enter amount of columns: ");
        cols = in.nextInt();

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                System.out.printf("Enter element [%d][%d]: ", i, k);
                matrix[i][k] = in.nextInt();
            }
        }

        printMatrix(matrix);

        for (int i = 0; i < rows; i++) {
            System.out.printf("[%d] Result: %d\n", i, countSum(matrix[i]));
        }

        matrix = sortMatrix(matrix);

        System.out.print("Sorted matrix:\n\n");
        printMatrix(matrix);

    }

    public static int countPos(int[] row) {
        int count = 0;
        for (int el : row) {
            if (el > 0) {
                count++;
            }
        }

        return count;
    }

    public static int countSum(int[] row) {
        int sum = 0;
        int positiveSeen = 0;
        int amountOfPos = countPos(row);
        if (amountOfPos < 2) {
            return 0;
        }

        for (int i = 0; i < row.length; i++) {
            if (row[i] > 0 && positiveSeen == 1) {
                return sum;
            }

            if (row[i] > 0 && positiveSeen == 0) {
                positiveSeen++;
                continue;
            }

            if (row[i] <= 0 && positiveSeen == 0) {
                continue;
            }

            sum += row[i];
        }

        return sum;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.printf("%5d", value);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] sortMatrix(int[][] matrix) {
        ArrayList<int[]> newArray = new ArrayList<>();

        for (int[] row : matrix) {
            if (countPos(row) > 1) {
                newArray.add(row);
            }
        }

        int[] sums = new int[newArray.size()];
        for (int i = 0; i < newArray.size(); i++) {
            sums[i] = countSum(newArray.get(i));
        }

        for (int i = 0; i < newArray.size() - 1; i++) {
            for (int j = 0; j < newArray.size() - 1 - i; j++) {
                if (sums[j] > sums[j + 1]) {
                    int[] tempRow = newArray.get(j);
                    newArray.set(j, newArray.get(j + 1));
                    newArray.set(j + 1, tempRow);

                    int tempSum = sums[j];
                    sums[j] = sums[j + 1];
                    sums[j + 1] = tempSum;
                }
            }
        }

        return newArray.toArray(new int[0][]);
    }
}
