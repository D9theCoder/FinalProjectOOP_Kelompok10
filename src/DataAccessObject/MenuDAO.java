
package DataAccessObject;

import java.sql.*;
import java.util.*;
import model.*;

public class MenuDAO {

    public List<Menu> getAllMenuItems() {
        List<Menu> menuItems = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Menu";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("MenuID");
                        String menuName = resultSet.getString("MenuName");
                        String menuType = resultSet.getString("MenuType");
                        double menuPrice = resultSet.getDouble("MenuPrice");
                        String origin = resultSet.getString("Origin");
                        String menuDescription = resultSet.getString("MenuDescription");

                        Menu menuItem = new Menu(id, menuName, menuType, menuPrice, origin, menuDescription);
                        menuItems.add(menuItem);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    public static Menu getMenuById(int menuId) {
        Menu menuItem = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Menu WHERE MenuID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, menuId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("MenuID");
                        String menuName = resultSet.getString("MenuName");
                        String menuType = resultSet.getString("MenuType");
                        double menuPrice = resultSet.getDouble("MenuPrice");
                        String origin = resultSet.getString("Origin");
                        String menuDescription = resultSet.getString("MenuDescription");

                        menuItem = new Menu(id, menuName, menuType, menuPrice, origin, menuDescription);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItem;
    }

    public void insertEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Employee (EmployeeName, BranchID) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, employee.getName());

                // Check if the employee has a branch assigned
                if (employee.getRestaurantBranch() != null) {
                    preparedStatement.setInt(2, employee.getRestaurantBranch().getId());
                } else {
                    preparedStatement.setNull(2, Types.INTEGER);
                }

                preparedStatement.executeUpdate();

                // Retrieve the auto-generated employee ID
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int employeeId = generatedKeys.getInt(1);
                    employee.setId(employeeId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
