// EmployeeDAO.java
package DataAccessObject;

import java.sql.*;
import java.util.*;
import model.*;

public class EmployeeDAO {
    
    private BranchDAO branchDAO = new BranchDAO();

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Employee";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("EmployeeID");
                        String name = resultSet.getString("EmployeeName");
                        int branchId = resultSet.getInt("BranchID");

                        Branch branch = branchDAO.getBranchById(branchId);

                        Employee employee = new Employee(id, name, branch);
                        employees.add(employee);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("EmployeeID");
                        String name = resultSet.getString("EmployeeName");
                        int branchId = resultSet.getInt("BranchID");

                        Branch branch = branchDAO.getBranchById(branchId);

                        employee = new Employee(id, name, branch);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public void insertEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Employee (EmployeeName, BranchID) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, employee.getName());

                // Check if the employee has a branch assigned
                if (employee.getRestaurantBranch() != null) {
                    preparedStatement.setInt(2, employee.getRestaurantBranch().getId());
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
