package model;

import java.sql.*;
import DataAccessObject.DatabaseConnection;

public class Reservation {
    private int id;
    private int tableId;
    private int employeeId;
    private String status;
    private String customerName;
    private int numberOfCustomers;

    

    public Reservation(int id, int tableId, int employeeId, String customerName, String status, int numberOfCustomers) {
        this.id = id;
        this.customerName = customerName;
        this.tableId = tableId;
        this.employeeId = employeeId;
        this.status = status;
        this.numberOfCustomers = numberOfCustomers;
    }

    public void createReservation(Reservation reservation) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Reservation (TableID, EmployeeID, CustomerName, Status, NumberOfCustomers) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, reservation.getTableId());
                preparedStatement.setInt(2, reservation.getEmployeeId());
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
