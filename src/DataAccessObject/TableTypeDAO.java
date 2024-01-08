
package DataAccessObject;
import java.util.*;
import java.sql.*;

import model.TableType;

public class TableTypeDAO {

    public List<TableType> getAllTableTypes() {
        List<TableType> tableTypes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM TableType";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("TableTypeID");
                        String type = resultSet.getString("Type");
                        int maxCapacity = resultSet.getInt("MaxCapacity");

                        TableType tableType = new TableType(id, type, maxCapacity);
                        tableTypes.add(tableType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableTypes;
    }

    public TableType getTableTypeById(int tableTypeId) {
        TableType tableType = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM TableType WHERE TableTypeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, tableTypeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("TableTypeID");
                        String type = resultSet.getString("Type");
                        int maxCapacity = resultSet.getInt("MaxCapacity");

                        tableType = new TableType(id, type, maxCapacity);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableType;
    }
}
