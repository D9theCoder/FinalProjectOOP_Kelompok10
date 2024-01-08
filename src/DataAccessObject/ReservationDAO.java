package DataAccessObject;

import java.sql.*;
import java.util.*;
import model.*;

public class ReservationDAO {
    public void createReservation(Reservation reservation) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Reservation (CustomerName, TableID, EmployeeID, Status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, reservation.getCustomerName());
                preparedStatement.setInt(2, reservation.getTable().getId());
                preparedStatement.setInt(3, reservation.getEmployee().getId());
                preparedStatement.setString(4, reservation.getStatus());

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

    public Reservation getReservationById(int reservationId) {
        Reservation reservation = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Reservation WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, reservationId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        reservation = mapResultSetToReservation(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Reservation";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Reservation reservation = mapResultSetToReservation(resultSet);
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String customerName = resultSet.getString("CustomerName");
        int tableId = resultSet.getInt("TableID");
        int employeeId = resultSet.getInt("EmployeeID");
        String status = resultSet.getString("Status");

        // Assuming you have methods to get Table and Employee objects by their IDs
        RestaurantTable table = getTableById(tableId);
        Employee employee = getEmployeeById(employeeId);

        return new Reservation(id, customerName, table, employee, status);
    }

    // Assuming you have methods to get Table and Employee objects by their IDs
    private RestaurantTable getTableById(int tableId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM RestaurantTable WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, tableId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int branchId = resultSet.getInt("BranchID");
                        int tableTypeId = resultSet.getInt("TableTypeID");
    
                        // Assuming you have methods to get Branch and TableType objects by their IDs
                        Branch branch = getBranchById(branchId);
                        TableType tableType = getTableTypeById(tableTypeId);
    
                        return new RestaurantTable(tableId, branch, tableType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Handle appropriately if the record is not found or an error occurs
    }
    private Employee getEmployeeById(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Employee WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int branchId = resultSet.getInt("RestaurantID");
    
                        // Assuming you have a method to get Branch by its ID
                        Branch branch = getBranchById(branchId);
    
                        return new Employee(employeeId, resultSet.getString("Name"), branch);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Handle appropriately if the record is not found or an error occurs
    }

    private Branch getBranchById(int branchId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Branch WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, branchId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Branch(branchId, resultSet.getString("Name"), resultSet.getString("Location"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Handle appropriately if the record is not found or an error occurs
    }
    
    private TableType getTableTypeById(int tableTypeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM TableType WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, tableTypeId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new TableType(tableTypeId, resultSet.getString("Type"), resultSet.getInt("MaxCapacity"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Handle appropriately if the record is not found or an error occurs
    }

    public List<Reservation> getReservationsByCustomerName(String customerName) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Reservation WHERE CustomerName LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + customerName + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Reservation reservation = mapResultSetToReservation(resultSet);
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    
    public List<Reservation> getReservationsByStatus(String status) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Reservation WHERE Status = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, status);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Reservation reservation = mapResultSetToReservation(resultSet);
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
