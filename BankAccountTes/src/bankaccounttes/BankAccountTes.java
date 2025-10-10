package BankAccountTes.src.bankaccounttes;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simple Bank Account Management System
 * Allows: Add, Search, Remove, Display, Exit
 * 
 * Improvements:
 * - Proper indentation and method structure
 * - Simplified switch logic
 * - Avoided repeated calls to menu() inside each case
 * - Clear prompts and messages
 * - Used descriptive variable names
 */
public class BankAccountTes {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        int choice;

        do {
            choice = menu();
            switch (choice) {
                case 1:
                    addAccount(accounts);
                    break;
                case 2:
                    searchAccount(accounts);
                    break;
                case 3:
                    removeAccount(accounts);
                    break;
                case 4:
                    displayAccounts(accounts);
                    break;
                case 5:
                    System.out.println("Thank you for using the Bank System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        } while (choice != 5);
    }

    /**
     * Displays the main menu and gets user choice.
     */
    public static int menu() {
        System.out.println("\n=== Bank Account Menu ===");
        System.out.println("1. Add Account");
        System.out.println("2. Search Account");
        System.out.println("3. Remove Account");
        System.out.println("4. Display All Accounts");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        while (!input.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            input.next();
            System.out.print("Enter your choice: ");
        }
        return input.nextInt();
    }

    /**
     * Adds a new bank account.
     */
    public static void addAccount(ArrayList<BankAccount> accounts) {
        input.nextLine(); // clear buffer
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.print("Enter your Account Number: ");
        int accNo = input.nextInt();

        System.out.print("Enter initial balance: ");
        double balance = input.nextDouble();

        BankAccount newAccount = new BankAccount(name, accNo, balance);
        accounts.add(newAccount);
        System.out.println("✅ Account successfully added.");
    }

    /**
     * Searches for an account by account number.
     */
    public static void searchAccount(ArrayList<BankAccount> accounts) {
        System.out.print("Enter account number to search: ");
        int accNo = input.nextInt();

        BankAccount account = search(accounts, accNo);
        if (account != null) {
            System.out.println("🔍 Account found:\n" + account);
        } else {
            System.out.println("❌ Account not found.");
        }
    }

    /**
     * Removes an account by account number.
     */
    public static void removeAccount(ArrayList<BankAccount> accounts) {
        System.out.print("Enter account number to remove: ");
        int accNo = input.nextInt();

        BankAccount account = search(accounts, accNo);
        if (account != null) {
            accounts.remove(account);
            System.out.println("🗑️ Account removed successfully.");
        } else {
            System.out.println("❌ Account not found.");
        }
    }

    /**
     * Displays all accounts.
     */
    public static void displayAccounts(ArrayList<BankAccount> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("⚠️ No accounts available to display.");
            return;
        }
        System.out.println("\n=== Account List ===");
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }

    /**
     * Helper method to search for a bank account.
     */
    public static BankAccount search(ArrayList<BankAccount> accounts, int accNo) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accNo) {
                return account;
            }
        }
        return null;
    }
}
