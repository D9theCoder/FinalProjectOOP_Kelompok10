package model;

public class Menu {
    private int id;
    private String menuName;
    private String menuType;
    private double menuPrice;
    private String origin;
    private String menuDescription;

    public Menu(int id, String menuName, String menuType, double menuPrice, String origin, String menuDescription) {
        this.id = id;
        this.menuName = menuName;
        this.menuType = menuType;
        this.menuPrice = menuPrice;
        this.origin =  origin;
        this.menuDescription = menuDescription;
    }

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

    public String getOrigin() {
        return origin;
    }

    public String getDescription() {
        return menuDescription;
    }

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

    public void setOrigin(String origin) {
        this.origin = origin;
    }

      public void setDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }
}
