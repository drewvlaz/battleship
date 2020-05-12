/* Drew Vlasnik
Period 3
This class will contain useful methods that can be used
in multiple situations */

import java.util.Scanner;

public class UsefulMethods {
    /* Validates the user input y or n
    @param input: String of user input
    @return boolean, true for y and false for n */
    public static boolean validateYesNo(String input) {
        Scanner sc = new Scanner(System.in);
        char converted = input.toLowerCase().charAt(0);

        while (converted != 'y' && converted != 'n') {
            System.out.print("\tERROR - Enter y or n: ");
            converted = sc.nextLine().toLowerCase().charAt(0);
            System.out.println();
        }

        return converted == 'y';
    }

    /* Validates the user input a number greater than 0
    @param input: double of user input
    @return input: double of validated input */
    public static double validateGreaterThanZero(double input) {
        Scanner sc = new Scanner(System.in);
        while (input <= 0) {
            System.out.print("\tERROR - Enter a value greater than 0: ");
            input = sc.nextDouble();
            System.out.println();
        }
        return input;
    }

    /* Validates the user input a number greater than 0
    @param input: int of user input
    @return input: int of validated input */
    public static int validateGreaterThanZero(int input) {
        Scanner sc = new Scanner(System.in);
        while (input <= 0) {
            System.out.print("\tERROR - Enter a value greater than 0: ");
            input = sc.nextInt();
            System.out.println();
        }
        return input;
    }

    /* Validates the user input a positive number
    @param input: int of user input
    @return input: int of validated input */
    public static int validatePositive(int input) {
        Scanner sc = new Scanner(System.in);
        while (input < 0) {
            System.out.print("\tERROR - Enter a positive value: ");
            input = sc.nextInt();
            System.out.println();
        }
        return input;
    }

    /* Validates the user input a positive number
    @param input: int of user input
    @return input: int of validated input */
    public static double validatePositive(double input) {
        Scanner sc = new Scanner(System.in);
        while (input < 0) {
            System.out.print("\tERROR - Enter a positive value: ");
            input = sc.nextDouble();
            System.out.println();
        }
        return input;
    }

    /* Validate input is within a range
    @param input: double of input
    @param lb: double lower bound
    @param ub: double upper bound
    @return input: validated input */
    public static double validateRange(double input, double lb, double ub) {
        Scanner sc = new Scanner(System.in);
        while (input < lb || input > ub) {
            System.out.printf("\tERROR - Enter a value between %f and %f: ", lb, ub);
            input = sc.nextDouble();
            System.out.println();
        }
        return input;
    }

    /* Validate input is within a range
    @param input: int of input
    @param lb: int lower bound
    @param ub: int upper bound
    @return input: validated input */
    public static int validateRange(int input, int lb, int ub) {
        Scanner sc = new Scanner(System.in);
        while (input < lb || input > ub) {
            System.out.printf("\tERROR - Enter a value between %d and %d: ", lb, ub);
            input = sc.nextInt();
            System.out.println();
        }
        return input;
    }

    public static int validateGreaterThan(int input, int val) {
        Scanner sc = new Scanner(System.in);
        while (input <= val) {
            System.out.printf("\tERROR - Enter a value greater than %d: ", val);
            input = sc.nextInt();
            System.out.println();
        }
        return input;
    }

    public static int validateLessThan(int input, int val) {
        Scanner sc = new Scanner(System.in);
        while (input >= val) {
            System.out.printf("\tERROR - Enter a value less than %d: ", val);
            input = sc.nextInt();
            System.out.println();
        }
        return input;
    }

    public static String validateNotEmpty(String input) {
        Scanner sc = new Scanner(System.in);
        while (input.isEmpty()) {
            System.out.print("\tERROR - Enter a value: ");
            input = sc.nextLine();
            System.out.println();
        }
        return input;
    }

    /* Determines whether a num is prime
    @param num: int to check as prime
    @return boolean of whether num is prime */
    public static boolean isPrime(int num) {
        for (int i=2; i<(int)Math.sqrt(num)+1; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /* Center text in space specified
    @param length of text field
    @return centered text */
    public static String center(int len, String text) {
        String centered = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        double middle = centered.length() / 2;
        double start = middle - (len / 2);
        double end = start + len; 

        return centered.substring((int)start, (int)end);
    }
}
