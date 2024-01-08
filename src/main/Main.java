package main;
import java.util.List;
import java.util.Scanner;

import DataAccessObject.*;
import model.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ReservationDAO reservationDAO = new ReservationDAO();
    private static TransactionDAO transactionDAO = new TransactionDAO();

    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Make Reservation");
            System.out.println("2. Update Reservation Status");
            System.out.println("3. Display All Reservations");
            System.out.println("4. Filter Reservations by Status");
            System.out.println("5. Filter Reservations by Customer Name");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    updateReservationStatus();
                    break;
                case 3:
                    displayAllReservations();
                    break;
                case 4:
                    filterReservationsByStatus();
                    break;
                case 5:
                    filterReservationsByCustomerName();
                    break;
                case 6:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void makeReservation() {
        // Input details and create Reservation instance
        System.out.println("Enter reservation ID:");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();

        // Assuming you have instances of Branch, TableType, and Employee
        Branch bandungBranch = new Branch(1, "Bandung Restaurant", "Bandung Location");
        TableType familyTableType = new TableType(1, "Family", 10);
        RestaurantTable familyTable = new RestaurantTable(1, bandungBranch, familyTableType);

        Employee bandungEmployee = new Employee(1, "Bob", bandungBranch);

        Reservation reservation = new Reservation(reservationId, customerName, familyTable, bandungEmployee, "In Reserve");

        // Create menu instance
        System.out.println("Enter menu ID:");
        int menuId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Enter menu name:");
        String menuName = scanner.nextLine();
        System.out.println("Enter menu type:");
        String menuType = scanner.nextLine();
        System.out.println("Enter menu price:");
        double menuPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Enter menu description:");
        String menuDescription = scanner.nextLine();

        Menu specialMenu = new Menu(menuId, menuName, menuType, menuPrice, menuDescription);

        // Perform operations using user input
        reservationDAO.createReservation(reservation);
        transactionDAO.createTransaction(new Transaction(1, reservation, specialMenu));

        System.out.println("Reservation created successfully!");
    }

    private static void updateReservationStatus() {
        System.out.println("Enter reservation ID:");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter new status (e.g., 'In Order'):");
        String newStatus = scanner.nextLine();

        reservationDAO.updateReservationStatus(reservationId, newStatus);
        System.out.println("Reservation status updated successfully!");
    }

    private static void displayAllReservations() {
        List<Reservation> allReservations = reservationDAO.getAllReservations();
        displayReservations(allReservations);
    }

    private static void filterReservationsByStatus() {
        System.out.println("Enter a status to filter reservations (e.g., 'In Order'):");
        String filterStatus = scanner.nextLine();

        List<Reservation> reservationsByStatus = reservationDAO.getReservationsByStatus(filterStatus);
        displayReservations(reservationsByStatus);
    }

    private static void filterReservationsByCustomerName() {
        System.out.println("Enter a customer name to filter reservations (e.g., 'Bob'):");
        String filterCustomerName = scanner.nextLine();

        List<Reservation> reservationsByCustomer = reservationDAO.getReservationsByCustomerName(filterCustomerName);
        displayReservations(reservationsByCustomer);
    }

    private static void displayReservations(List<Reservation> reservations) {
        System.out.println("\nReservations:");
        for (Reservation r : reservations) {
            System.out.println(r.getId() + " - " + r.getCustomerName() + " - " + r.getStatus());
        }
    }
}

