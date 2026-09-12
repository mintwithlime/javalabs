/*18.
Округлить все элементы матрицы до целого числа, в строках с
четным номером — с недостатком, в строках с нечетным номером — с
избытком.*/

import java.util.Random;
import java.util.Scanner;

public class Task18 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int rows;
        int cols;

        System.out.print("Enter amount of rows: ");
        rows = in.nextInt();
        System.out.print("Enter amount of columns: ");
        cols = in.nextInt();

        Random random = new Random();
        double[][] matrix = new double[rows][cols];


        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                matrix[i][k] = random.nextDouble(-15.0, 15.0);
            }
        }

        System.out.println();
        printMatrix(matrix);

        System.out.println();
        System.out.print("Result matrix:\n");
        Task6.printMatrix(roundMatrix(matrix));
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.2f", value);
            }
            System.out.println();
        }
        System.out.println();
    }


    public static int[][] roundMatrix(double[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            if ((i + 1) % 2 == 0){
                for (int k = 0; k < matrix[i].length; k++) {
                    result[i][k] = matrix[i][k] >= 0 ?  (int) matrix[i][k] : (int) matrix[i][k] - 1 ;
                }
            } else {
                for (int k = 0; k < matrix[i].length; k++) {
                    result[i][k] = matrix[i][k] >= 0 ? (int) matrix[i][k] + 1 : (int) matrix[i][k];
                }
            }
        }

        return result;
    }
}
