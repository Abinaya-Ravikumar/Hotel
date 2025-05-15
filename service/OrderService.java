package service;

import java.util.List;

import entity.Order;
import entity.OrderItem;
import exceptions.CustomerIDNotFoundException;

public interface OrderService {
    
    boolean createOrder(Order order, List<OrderItem> orderItems);
    List<Order> getOrdersByCustomer(int customerId) throws CustomerIDNotFoundException;


}
