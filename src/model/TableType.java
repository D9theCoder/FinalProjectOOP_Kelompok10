package model;

public class TableType {
    private int id;
    private String type;
    private int maxCapacity;

    // Constructors
    public TableType(int id, String type, int maxCapacity) {
        this.id = id;
        this.type = type;
        this.maxCapacity = maxCapacity;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
