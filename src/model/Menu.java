package model;

public class Menu {
    private int id;
    private String menuName;
    private String menuType;
    private double menuPrice;
    private String originDescription;

    // Constructors
    public Menu(int id, String menuName, String menuType, double menuPrice, String originDescription) {
        this.id = id;
        this.menuName = menuName;
        this.menuType = menuType;
        this.menuPrice = menuPrice;
        this.originDescription = originDescription;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    public String getOriginDescription() {
        return originDescription;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public void setOriginDescription(String originDescription) {
        this.originDescription = originDescription;
    }
}
