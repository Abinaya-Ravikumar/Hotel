package entity;

public class MenuItem {
    private int menuItemId;
    private int restaurantId;
    private String name;
    private double price;
    private String description;
    private int quantity;

    public MenuItem(int restaurantId, String name, double price, String description, int quantity){
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public MenuItem(int menuItemId, int restaurantId, String name, double price, String description, int quantity){
        this.menuItemId = menuItemId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() {
        return menuItemId;
    }

    public void setId(int id) {
        this.menuItemId = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
    
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Menu Item ID: " + this.menuItemId +
           "\nName: " + this.name +
           "\nPrice: $" + this.price +
           "\nDescription: " + this.description +
           "\nAvailable Quantity: " + this.quantity;
    }
}
