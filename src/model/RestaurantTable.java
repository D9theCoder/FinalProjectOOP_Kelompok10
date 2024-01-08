package model;

public class RestaurantTable {
    private int id;
    private Branch branch;
    private TableType tableType;

    public RestaurantTable(int id, Branch branch, TableType tableType) {
        this.id = id;
        this.branch = branch;
        this.tableType = tableType;
    }

    public int getId() {
        return id;
    }

    public Branch getBranch() {
        return branch;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }
}
