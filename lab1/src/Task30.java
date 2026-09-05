import java.util.ArrayList;
import java.util.Scanner;
/*
30. Найти все числа-палиндромы из заданной последовательности чисел при
возведении которых в квадрат получают также числа-палиндромы. Число называется
палиндромом, если его запись читается одинаково слева направо и справа налево,
например, 12321.
*/

public class Task30 {
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
            if (isPalindromeSquared(number)) {
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

    public static boolean isPalindrome(int number) {
        if (number < 0 || (number % 10 == 0 && number != 0)) {
            return false;
        }

        int lastHalfReversed = 0;

        while (number > lastHalfReversed) {
            lastHalfReversed = lastHalfReversed*10 + number % 10;
            number /= 10;
        }

        return number == lastHalfReversed || number == lastHalfReversed / 10;
    }

    public static boolean isPalindromeSquared(int number) {
        if (!isPalindrome(number)) {
            return false;
        }

        int squared = number * number;

        return isPalindrome(squared);
    }
}
