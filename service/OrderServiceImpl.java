package service;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Order;
import entity.OrderItem;
import exceptions.CustomerIDNotFoundException;
import util.DBConnectionUtil;

public class OrderServiceImpl implements OrderService{
    
    public boolean createOrder(Order order, List<OrderItem> orderItems) {
        String orderQuery = "INSERT INTO `order` (customerId, restaurantId, orderStatus, totalPrice, deliveryAddress) VALUES (?, ?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO orderItem (orderId, itemId, quantity) VALUES (?, ?, ?)";
        String updateMenuItemQuery = "UPDATE menuItem SET availableQuantity = availableQuantity - ? WHERE itemId = ? AND availableQuantity >= ?";

        Connection conn = null;
        PreparedStatement orderPs = null;
        PreparedStatement orderItemPs = null;
        PreparedStatement updateMenuItemPs = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            conn.setAutoCommit(false);

            orderPs = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, order.getCustomerId());
            orderPs.setInt(2, order.getRestaurantId());
            orderPs.setString(3, order.getStatus());
            orderPs.setDouble(4, order.getPrice());
            orderPs.setString(5, order.getDeliveryAddress());

            int affectedRows = orderPs.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                throw new SQLException("Order creation failed, no rows affected.");
            }

            rs = orderPs.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                conn.rollback();
                throw new SQLException("Order creation failed, no order ID obtained.");
            }

            orderItemPs = conn.prepareStatement(orderItemQuery);
            updateMenuItemPs = conn.prepareStatement(updateMenuItemQuery);

            for (OrderItem item : orderItems) {
                int itemId = item.getItemId();
                int quantity = item.getQuantity();

                orderItemPs.setInt(1, orderId);
                orderItemPs.setInt(2, itemId);
                orderItemPs.setInt(3, quantity);
                orderItemPs.addBatch();

                updateMenuItemPs.setInt(1, quantity);
                updateMenuItemPs.setInt(2, itemId);
                updateMenuItemPs.setInt(3, quantity);
                updateMenuItemPs.addBatch();
            }

            orderItemPs.executeBatch();
            int[] updated = updateMenuItemPs.executeBatch();

            for (int i : updated) {
                if (i == 0) {
                    conn.rollback();
                    throw new SQLException("Menu item out of stock or insufficient quantity.");
                }
            }

            conn.commit();
            

        } catch (SQLException e) {
            System.out.println("Error occured while processing order: " + e.getMessage());
        } finally {
            DBConnectionUtil.close();
        }
        return true;
    }

    public List<Order> getOrdersByCustomer(int customerId) throws CustomerIDNotFoundException {
        String query = "SELECT * FROM `order` WHERE customerId = ?";
        List<Order> orders = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int restaurantId = rs.getInt("restaurantId");
                String status = rs.getString("orderStatus");
                double price = rs.getDouble("totalPrice");
                String deliveryAddress = rs.getString("deliveryAddress");

                Order order = new Order(orderId, customerId, restaurantId, status, price, deliveryAddress);

                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            DBConnectionUtil.close();
            throw new CustomerIDNotFoundException("Invalid Customer ID entered");
        } finally {
            DBConnectionUtil.close();
        }

        return orders;
    }

}
