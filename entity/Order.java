package entity;

public class Order {
    private int orderId;
    private int customerId;
    private int restaurantId;
    private String status;
    private double price;
    private String deliveryAddress;

    public Order(int customerId, int restaurantId, String status, double price, String deliveryAddress){
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
    }

    public Order(int orderId, int customerId, int restaurantId, String status, double price, String deliveryAddress){
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
    }
    
    public int getId() {
        return orderId;
    }

    public void setId(int id) {
        this.orderId = id;
    }

    public int getCustomerId() {
        return customerId;
    }
    
    public int getRestaurantId() {
        return restaurantId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public String toString() {
        return "Order ID: " + this.orderId +
           "\nCustomer ID: " + this.customerId +
           "\nStatus: " + this.status +
           "\nTotal Price: $" + String.format("%.2f", this.price) +
           "\nDelivery Address: " + this.deliveryAddress;
    }

}
