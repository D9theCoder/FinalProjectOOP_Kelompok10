package DataAccessObject;

import java.util.*;
import java.sql.*;
import model.Branch;

public class BranchDAO {

    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Branch";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("BranchID");
                        String name = resultSet.getString("Location");
                        String location = resultSet.getString("Location");

                        Branch branch = new Branch(id, name, location);
                        branches.add(branch);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branches;
    }

    public Branch getBranchById(int branchId) {
        Branch branch = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Branch WHERE BranchID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, branchId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("BranchID");
                        String name = resultSet.getString("Name");
                        String location = resultSet.getString("Location");

                        branch = new Branch(id, name, location);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branch;
    }
}
