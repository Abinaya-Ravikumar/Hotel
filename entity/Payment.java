package entity;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int orderId;
    private LocalDateTime dateTime;
    private String status;
    private double amount;

    public Payment(int orderId, LocalDateTime dateTime, String status, double amount){
        this.orderId = orderId;
        this.dateTime = dateTime;
        this.status = status;
        this.amount = amount;
    }

    public Payment(int paymentId, int orderId, LocalDateTime dateTime, String status, double amount){
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.dateTime = dateTime;
        this.status = status;
        this.amount = amount;
    }

    public int getId() {
        return paymentId;
    }

    public void setId(int id) {
        this.paymentId = id;
    }

    public int getOrderId() {
        return orderId;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public double getAmount() {
        return amount;
    }
}
