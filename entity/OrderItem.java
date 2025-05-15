package entity;

public class OrderItem {
    private int orderItemId;
    private int itemId;
    private int quantity;
    
    public OrderItem(int itemId, int quantity){
        this.itemId = itemId;
        this.quantity = quantity;
    }
    
    public OrderItem(int orderItemId, int itemId, int quantity){
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getId() {
        return orderItemId;
    }

    public void setId(int id) {
        this.orderItemId = id;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public int getQuantity() {
        return quantity;
    }
}
