package POS.Common;

import java.util.Scanner;

public class ConsoleHelper {
    private final Scanner scanner;

    public ConsoleHelper() {
        this.scanner = new Scanner(System.in);
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) scanner.next();
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    public double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) scanner.next();
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int readMenuChoice(String prompt, int min, int max) {
        int choice;
        do {
            choice = readInt(prompt);
            if (choice < min || choice > max) {
                System.out.println("Invalid choice. Please select between " + min + " and " + max + ".");
            }
        } while (choice < min || choice > max);
        return choice;
    }
}
