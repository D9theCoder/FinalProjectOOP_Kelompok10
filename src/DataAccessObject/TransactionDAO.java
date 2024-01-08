package DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Menu;
import model.Reservation;
import model.Transaction;

public class TransactionDAO {

    public void createTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Transaction (ReservationID, MenuID) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, transaction.getReservation().getId());
                preparedStatement.setInt(2, transaction.getMenu().getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Transaction SET ReservationID = ?, MenuID = ? WHERE TransactionID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, transaction.getReservation().getId());
                preparedStatement.setInt(2, transaction.getMenu().getId());
                preparedStatement.setInt(3, transaction.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(int transactionId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Transaction WHERE TransactionID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, transactionId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public Transaction viewTransaction(int transactionId) {
        Transaction transaction = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Transaction WHERE TransactionID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, transactionId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("TransactionID");
                    int reservationId = resultSet.getInt("ReservationID");
                    int menuId = resultSet.getInt("MenuID");

                    // Assuming you have methods to get Reservation and Menu by their IDs
                    Reservation reservation = new ReservationDAO().getReservationById(reservationId);
                    Menu menu = new MenuDAO().getMenuById(menuId);

                    transaction = new Transaction(id, reservation, menu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    private Reservation getReservationById(int reservationId, Connection connection) {
        Reservation reservation = null;

        String sql = "SELECT * FROM Reservation WHERE TransactionID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservationId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("ReservationID");
                int tableId = resultSet.getInt("TableID");
                int employeeId = resultSet.getInt("EmployeeID");
                String customerName = resultSet.getString("CustomerName");
                String status = resultSet.getString("Status");
                int numberOfCustomers = resultSet.getInt("NumberOfCustomers");

                reservation = new Reservation(id, tableId, employeeId, customerName, status, numberOfCustomers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }
}
