package DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            String sql = "UPDATE Transaction SET ReservationID = ?, MenuID = ? WHERE ID = ?";
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
            String sql = "DELETE FROM Transaction WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, transactionId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
