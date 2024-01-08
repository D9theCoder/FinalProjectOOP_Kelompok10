package model;

public class Branch {
    private int id;
    private String name;
    private String location;

    // Constructor
    public Branch(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
