

import java.util.Scanner;

/**
 * A simple airline seat reservation system.
 * Handles 10 seats: 1–5 for First Class, 6–10 for Economy.
 * 
 * Improvements made:
 * - Clearer seat assignment logic.
 * - No unnecessary recursion.
 * - Avoided System.exit() calls (clean exit instead).
 * - Removed redundant seat checks inside loops.
 * - Improved readability and modular design.
 * - Added input validation.
 */
public class Airline {

    private final boolean[] seats = new boolean[11]; // index 0 unused
    private final Scanner input = new Scanner(System.in);

    public void start() {
        System.out.println("✈️ Welcome to Air Reservation System");
        while (true) {
            makeReservation();
            System.out.println("\nWould you like to make another reservation? (1 for Yes / 2 for No)");
            int choice = input.nextInt();
            if (choice != 1) {
                System.out.println("Thank you! Have a great day! ✈️");
                break;
            }
        }
    }

    private void makeReservation() {
        int section = 0;
        while (section != 1 && section != 2) {
            System.out.print("Please type 1 for First Class or 2 for Economy: ");
            if (input.hasNextInt()) {
                section = input.nextInt();
            } else {
                input.next(); // discard invalid input
            }
        }

        if (section == 1) {
            assignSeat(1, 5, "First Class", 6, 10);
        } else {
            assignSeat(6, 10, "Economy", 1, 5);
        }
    }

    /**
     * Assigns a seat in the specified range.
     * 
     * @param startRange  start index of section
     * @param endRange    end index of section
     * @param sectionName readable section name
     * @param altStart    alternate section start index
     * @param altEnd      alternate section end index
     */
    private void assignSeat(int startRange, int endRange, String sectionName, int altStart, int altEnd) {
        int seatNumber = findAvailableSeat(startRange, endRange);

        if (seatNumber != -1) {
            seats[seatNumber] = true;
            System.out.printf("%s booking confirmed. Seat #%d%n", sectionName, seatNumber);
            return;
        }

        // Section full
        if (isSectionFull(altStart, altEnd)) {
            System.out.println("Sorry, flight fully booked. Next flight is in 3 hours.");
            return;
        }

        // Offer alternate section
        System.out.printf("%s is fully booked. Would you like to switch to %s? (1 for Yes / 2 for No): ",
                sectionName, (sectionName.equals("First Class") ? "Economy" : "First Class"));

        int choice = input.nextInt();
        if (choice == 1) {
            assignSeat(altStart, altEnd,
                    (sectionName.equals("First Class") ? "Economy" : "First Class"),
                    startRange, endRange);
        } else {
            System.out.println("Next flight is in 3 hours.");
        }
    }

    /**
     * Finds the first available seat in a section.
     */
    private int findAvailableSeat(int start, int end) {
        for (int i = start; i <= end; i++) {
            if (!seats[i]) {
                return i;
            }
        }
        return -1; // section full
    }

    /**
     * Checks if a section is full.
     */
    private boolean isSectionFull(int start, int end) {
        for (int i = start; i <= end; i++) {
            if (!seats[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Airline airline = new Airline();
        airline.start();
    }
}
