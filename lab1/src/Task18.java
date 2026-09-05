import java.util.ArrayList;
import java.util.Scanner;

/*
18. В заданной последовательности натуральных чисел для всех трех-, четырех- и
пятизначных чисел определить, образуют ли цифры натурального числа убывающую
последовательность. Вывести соответствующее сообщение.
*/


public class Task18 {
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

        in.close();

        for (Integer number : numbers) {
            if (isValidNumber(number)) {
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

    public static boolean isValidNumber(int number) {
        int len = (int) Math.log10(number) + 1;
        if (number <= 0 || len < 3 || len > 5) {
            return false;
        }

        int rightDigit = number % 10;
        number /= 10;

        while (number > 0) {
            int leftDigit = number % 10;
            if (leftDigit < rightDigit) {
                return false;
            }
            rightDigit = leftDigit;
            number /= 10;
        }

        return true;
    }
}
