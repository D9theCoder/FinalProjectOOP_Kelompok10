// package DataAccessObject;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// public class Reservation {
//     private int id;
//     private String customerName;
//     private RestaurantTable table;
//     private Employee employee;
//     private String status;

//     // Constructors, getters, and setters

//     public void createReservation(Reservation reservation) {
//         try (Connection connection = DatabaseConnection.getConnection()) {
//             String sql = "INSERT INTO Reservation (CustomerName, TableID, EmployeeID, Status) VALUES (?, ?, ?, ?)";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                 preparedStatement.setString(1, reservation.getCustomerName());
//                 preparedStatement.setInt(2, reservation.getTable().getId());
//                 preparedStatement.setInt(3, reservation.getEmployee().getId());
//                 preparedStatement.setString(4, "In Reserve"); // Initial status

//                 preparedStatement.executeUpdate();
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void updateReservationStatus(int reservationId, String newStatus) {
//         try (Connection connection = DatabaseConnection.getConnection()) {
//             String sql = "UPDATE Reservation SET Status = ? WHERE ID = ?";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                 preparedStatement.setString(1, newStatus);
//                 preparedStatement.setInt(2, reservationId);

//                 preparedStatement.executeUpdate();
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     // Other methods as needed
// }
