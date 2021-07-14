package com.dihu.IO;

import com.dihu.classes.Player;

import java.util.List;
import java.util.Scanner;
import java.lang.String;

public class Console {
    private static Scanner sc = new Scanner(System.in);

    public static int getOption(int start, int end) {
        while (true) {
            System.out.print(Color.YELLOW + "Select an Option: " + Color.RESET);
            int option = getPositiveInt();
            if (option >= start && option <= end) {
                System.out.println("");
                return option;
            } else if (option != -1) {
                return -1;
            }
        }
    }

    public static String getPlayerName() {
        System.out.print(Color.YELLOW+"Enter Player Name: "+Color.RESET);
        return getLine();
    }

    public static String getCountryName() {
        System.out.print(Color.YELLOW+"Enter Country Name: "+Color.RESET);
        return getLine();
    }

    public static String getClubName() {
        System.out.print(Color.YELLOW+"Enter Club Name: "+Color.RESET);
        return getLine();
    }

    public static String getPlayerPosition() {
        System.out.print(Color.YELLOW+"Enter Player's Position: "+Color.RESET);
        return getString();
    }

    public static int getPlayerAge() {

        int n = -1;
        while (n == -1) {
            System.out.print(Color.YELLOW+"Enter Player's Age: "+Color.RESET);
            n = getPositiveInt();
        }
        return n;
    }

    public static double getPlayerHeight() {
        double n = -1;
        while (n == -1) {
            System.out.print(Color.YELLOW+"Enter Player's Height: "+Color.RESET);
            n = getPositiveDouble();
        }
        return n;
    }

    public static int getPlayerNumber() {
        int n = -1;
        while (n == -1) {
            System.out.print(Color.YELLOW+"Enter Player's Number: "+Color.RESET);
            n = getPositiveInt();
        }
        return n;
    }

    public static double getPlayerWeeklySalary() {
        double n = -1;
        while (n == -1) {
            System.out.print(Color.YELLOW+"Enter Player's Weekly Salary: "+Color.RESET);
            n = getPositiveDouble();
        }
        return n;
    }

    public static String getString() {
        String temp = sc.next();
        sc.nextLine();
        return temp;
    }

    public static String getLine() {
        return sc.nextLine().trim();
    }

    public static double getPositiveDouble() {
        try {
            double temp = sc.nextDouble();
            sc.nextLine();
            if (temp <= 0) {
                printError("Must be a Positive double");
                return -1;
            }
            return temp;
        } catch (Exception e) {
            printError("Must be a double");
            sc.nextLine();
            return -1;
        }
    }

    public static int getPositiveInt() {
        try {
            int temp = sc.nextInt();
            sc.nextLine();
            if (temp <= 0) {
                printError("Must be a Positive integer");
                return -1;
            }
            return temp;
        } catch (Exception e) {
            printError("Must be an integer");
            sc.nextLine();
            return -1;
        }
    }

    public static void printError(String error) {
        System.out.print(Color.RED);
        System.out.print("⚠ Error: " + error);
        System.out.println(Color.RESET );
    }

    public static void printSuccess(String message) {
        System.out.print(Color.GREEN);
        System.out.print("\uD83D\uDCD9Success: " + message);
        System.out.println(Color.RESET );
    }

    public static void printPlayers(List<Player> playerList) {
        for (Player p : playerList) {
            System.out.println(p);
        }
    }

    public static void closeScanner() {
        sc.close();
    }

    // Stop taking further input until user presses Enter 
    public static void pauseScanner() {
        System.out.print("\n"+Color.YELLOW+"Press enter to continue...."+Color.RESET);
        sc.nextLine();
        System.out.println("");
    }
}
