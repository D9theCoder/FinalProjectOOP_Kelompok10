package model;

public class Employee {
    private int id;
    private String name;
    private Branch restaurantBranch;

    public Employee(int id, String name, Branch restaurantBranch) {
        this.id = id;
        this.name = name;
        this.restaurantBranch = restaurantBranch;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Branch getRestaurantBranch() {
        return restaurantBranch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurantBranch(Branch restaurantBranch) {
        this.restaurantBranch = restaurantBranch;
    }
}
