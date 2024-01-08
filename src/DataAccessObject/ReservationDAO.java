package DataAccessObject;

import java.sql.*;
import java.util.*;
import model.*;

public class ReservationDAO {
    public void createReservation(Reservation reservation) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Reservation (TableID, EmployeeID, CustomerName, Status, NumberOfCustomers) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, reservation.getTableId());
                preparedStatement.setInt(2, reservation.getEmployeeId());
                preparedStatement.setString(3, reservation.getCustomerName());
                preparedStatement.setString(4, "In Reserve"); // Initial status
                preparedStatement.setInt(5, reservation.getNumberOfCustomers());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int reservationId = generatedKeys.getInt(1);
                        reservation.setId(reservationId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservationStatus(int reservationId, String newStatus) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Reservation SET Status = ? WHERE ReservationID = ?";
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
            String sql = "SELECT * FROM Reservation WHERE ReservationID = ?";
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
        int reservationId = resultSet.getInt("ReservationID");
        int tableId = resultSet.getInt("TableID"); 
        int employeeId = resultSet.getInt("EmployeeID"); 
        String customerName = resultSet.getString("CustomerName");
        String status = resultSet.getString("Status");
        int numberOfCustomers = resultSet.getInt("NumberOfCustomers");

        return new Reservation(reservationId, tableId, employeeId, customerName, status, numberOfCustomers);

    }

    private RestaurantTable getTableById(int tableId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM RestaurantTable WHERE TableID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, tableId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int branchId = resultSet.getInt("BranchID");
                        int tableTypeId = resultSet.getInt("TableTypeID");

                        Branch branch = getBranchById(branchId);
                        TableType tableType = getTableTypeById(tableTypeId);

                        return new RestaurantTable(tableId, branch, tableType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT EmployeeName, BranchID FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("EmployeeName");
                        int branchId = resultSet.getInt("BranchID");
                        Branch branch = getBranchById(branchId);
                        employee = new Employee(employeeId, name, branch);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    private Branch getBranchById(int branchId) {
        Branch branch = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT Location FROM Branch WHERE BranchID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, branchId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String location = resultSet.getString("Location");
                        branch = new Branch(branchId, location);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branch;
    }

    private TableType getTableTypeById(int tableTypeId) {
        TableType tableType = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT Type, MaxCapacity FROM TableType WHERE TableTypeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, tableTypeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String type = resultSet.getString("Type");
                        int maxCapacity = resultSet.getInt("MaxCapacity");
                        tableType = new TableType(tableTypeId, type, maxCapacity);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableType;
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
