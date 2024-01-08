package model;

import java.sql.*;
import DataAccessObject.DatabaseConnection;

public class Reservation {
    private int id;
    private String customerName;
    private RestaurantTable table;
    private Employee employee;
    private String status;
    private int numberOfCustomers;

    public Reservation(int id, String customerName, RestaurantTable table, Employee employee, String status,int numberOfCustomers) {
        this.id = id;
        this.customerName = customerName;
        this.table = table;
        this.employee = employee;
        this.status = status;
        this.numberOfCustomers = numberOfCustomers;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getStatus() {
        return status;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public void createReservation(Reservation reservation) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Reservation (TableID, EmployeeID, CustomerName, Status, NumberOfCustomers) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, reservation.getTable().getId());
                preparedStatement.setInt(2, reservation.getEmployee().getId());
                preparedStatement.setString(3, reservation.getCustomerName());
                preparedStatement.setString(4, "In Reserve"); // Initial status
                preparedStatement.setInt(5, reservation.getNumberOfCustomers());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservationStatus(int reservationId, String newStatus) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Reservation SET Status = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newStatus);
                preparedStatement.setInt(2, reservationId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
