package model;

import java.sql.*;
import DataAccessObject.DatabaseConnection;

public class Transaction {
    private int id;
    private Reservation reservation;
    private Menu menu;

    public Transaction(int id, Reservation reservation, Menu menu) {
        this.id = id;
        this.reservation = reservation;
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

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

}