import java.util.ArrayList;
import java.util.Scanner;

/*
6. Найти все p-значные числа из заданной последовательности натуральных чисел, в
записи которых цифры упорядочены, и подсчитать их количество.
*/

public class Task6 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int count = 0;

        System.out.println("Enter numbers (enter 0 for exit):");
        while (true) {
            int num = in.nextInt();
            if (num == 0) {
                break;
            }

            numbers.add(num);
        }

        System.out.println("Amount of digits in target numbers: ");
        int p = in.nextInt();
        in.close();


        for (Integer number : numbers) {
            if (isValidNumber(number,p)) {
                System.out.printf("Found example of target number: %d\n", number);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No target numbers found");
        } else {
            System.out.printf("Amount of target numbers: %d", count);
        }
    }

    public static boolean isValidNumber(int number, int p) {
        if (number <= 0 || (int) Math.log10(number) + 1 != p) {
            return false;
        }

        boolean isAscending = true;
        boolean isDescending = true;

        int rightDigit = number % 10;
        number /= 10;

        while (number > 0) {
            int leftDigit = number % 10;

            if (leftDigit > rightDigit) {
                isAscending = false;
            }

            if (leftDigit < rightDigit) {
                isDescending = false;
            }

            if (!isAscending && !isDescending) {
                return false;
            }

            rightDigit = leftDigit;
            number /= 10;
        }

        return true;
    }
}