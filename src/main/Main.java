package main;

import java.util.List;
import java.util.Scanner;
import DataAccessObject.*;
import model.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ReservationDAO reservationDAO = new ReservationDAO();
    private static TransactionDAO transactionDAO = new TransactionDAO();
    private static BranchDAO branchDAO = new BranchDAO();
    private static TableTypeDAO tableTypeDAO = new TableTypeDAO();
    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    private static MenuDAO menuDAO = new MenuDAO();

    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("Welcome to LaperAh!");
            System.out.println("\nChoose an option:");
            System.out.println("1. Make Reservation");
            System.out.println("2. Update Reservation Status");
            System.out.println("3. Display All Reservations");
            System.out.println("4. Filter Reservations by Status");
            System.out.println("5. Filter Reservations by Customer Name");
            System.out.println("6. Register Employee");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // makeReservation();
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
                    registerAndAssignEmployee();
                    break;
                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    // private static void makeReservation() {
    // System.out.println("Enter customer name:");
    // String customerName = scanner.nextLine();

    // System.out.println("Available branches:");
    // List<Branch> branches = branchDAO.getAllBranches();
    // for (Branch branch : branches) {
    // System.out.println(branch.getId() + ". " + branch.getLocation());
    // }
    // System.out.println("Enter branch ID:");
    // int branchId = scanner.nextInt();
    // scanner.nextLine();

    // System.out.println("Available table types:");
    // List<TableType> tableTypes = tableTypeDAO.getAllTableTypes();
    // for (TableType tableType : tableTypes) {
    // System.out.println(tableType.getId() + ". " + tableType.getType());
    // }
    // System.out.println("Enter table type ID:");
    // int tableTypeId = scanner.nextInt();
    // scanner.nextLine();

    // System.out.println("Enter number of customers:");
    // int numberOfCustomers = scanner.nextInt();
    // scanner.nextLine();

    // Branch selectedBranch = branchDAO.getBranchById(branchId);
    // TableType selectedTableType = tableTypeDAO.getTableTypeById(tableTypeId);
    // RestaurantTable selectedTable = new RestaurantTable(0, selectedBranch,
    // selectedTableType);

    // Employee selectedEmployee = employeeDAO.getEmployeeById(1);

    // Reservation reservation = new Reservation(0, selectedTable, selectedEmployee,
    // customerName, "In Reserve", numberOfCustomers);

    // // Display available menu items
    // System.out.println("Available menu items:");
    // List<Menu> menuItems = menuDAO.getAllMenuItems();
    // for (Menu menuItem : menuItems) {
    // System.out.println(menuItem.getId() + ". " + menuItem.getMenuName());
    // }
    // System.out.println("Enter menu ID:");
    // int menuId = scanner.nextInt();
    // scanner.nextLine();

    // Menu selectedMenu = menuDAO.getMenuById(menuId);

    // reservationDAO.createReservation(reservation);
    // transactionDAO.createTransaction(new Transaction(0, reservation,
    // selectedMenu));

    // System.out.println("Reservation created successfully!");
    // }

    private static void updateReservationStatus() {
        System.out.println("Enter reservation ID:");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

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
        System.out.println("Enter a customer name to filter reservations (misal: Putu):");
        String filterCustomerName = scanner.nextLine();

        List<Reservation> reservationsByCustomer = reservationDAO.getReservationsByCustomerName(filterCustomerName);
        displayReservations(reservationsByCustomer);
    }

    /**
     * Display Reservations
     * 
     * This method displays the reservations with their details.
     * 
     * @param reservations The list of reservations to be displayed
     */
    private static void displayReservations(List<Reservation> reservations) {
        System.out.printf("%-15s %-15s %-15s %-20s %-15s %-20s\n", "ReservationID", "TableID", "EmployeeID", "Customer Name", "Status", "Number of Customers");
        for (Reservation r : reservations) {
            System.out.printf("%-15d %-15d %-15d %-20s %-15s %-20d\n", r.getId(), r.getTableId(), r.getEmployeeId(), r.getCustomerName(), r.getStatus(), r.getNumberOfCustomers());
        }
    }

    private static void registerAndAssignEmployee() {
        System.out.println("Enter employee name:");
        String employeeName = scanner.nextLine();

        Employee employee = new Employee(0, employeeName, null);

        // Display available branches (placeholder)
        System.out.println("Available branches:");
        System.out.println("1. Bandung ");
        System.out.println("2. Jakarta ");
        System.out.println("3. Bali ");
        System.out.println("4. Surabaya ");
        System.out.println("5. Samarinda ");
        System.out.println("6. Padang ");

        System.out.println("Enter branch ID to assign the employee:");
        int branchId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Assign the selected branch to the employee
        Branch selectedBranch = new Branch(branchId, "Branch Location");
        employee.setRestaurantBranch(selectedBranch);

        // Insert the employee into the database
        employeeDAO.insertEmployee(employee);

        System.out.println("Employee registered and assigned successfully!");
    }
}
