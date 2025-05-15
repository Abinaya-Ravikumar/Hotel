package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import entity.Payment;
import util.DBConnectionUtil;

public class PaymentServiceImpl implements PaymentService{
    
    public boolean processPayment(Payment payment) {
        String fetchOrderQuery = "SELECT totalPrice FROM `order` WHERE orderId = ?";
        String insertPaymentQuery = "INSERT INTO payment (orderId, paymentDate, PaymentStatus, amountPaid) VALUES (?, ?, ?, ?)";
        String updateOrderStatusQuery = "UPDATE `order` SET orderStatus = ? WHERE orderId = ?";

        Connection conn = null;
        PreparedStatement fetchOrderPs = null;
        PreparedStatement insertPaymentPs = null;
        PreparedStatement updateOrderStatusPs = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            conn.setAutoCommit(false);

            fetchOrderPs = conn.prepareStatement(fetchOrderQuery);
            fetchOrderPs.setInt(1, payment.getOrderId());
            rs = fetchOrderPs.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Order not found for ID: " + payment.getOrderId());
            }

            double orderTotal = rs.getDouble("totalPrice");
            String finalStatus = payment.getAmount() != orderTotal ? "FAILED" : "COMPLETED";

            insertPaymentPs = conn.prepareStatement(insertPaymentQuery, Statement.RETURN_GENERATED_KEYS);
            insertPaymentPs.setInt(1, payment.getOrderId());
            insertPaymentPs.setTimestamp(2, Timestamp.valueOf(payment.getDateTime()));
            insertPaymentPs.setString(3, finalStatus);
            insertPaymentPs.setDouble(4, payment.getAmount());
            insertPaymentPs.executeUpdate();

            rs = insertPaymentPs.getGeneratedKeys();
            if (rs.next()) {
                payment.setId(rs.getInt(1));
            }

            updateOrderStatusPs = conn.prepareStatement(updateOrderStatusQuery);
            updateOrderStatusPs.setString(1, finalStatus);
            updateOrderStatusPs.setInt(2, payment.getOrderId());
            updateOrderStatusPs.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            System.out.println("Error occured while making payment: " + e.getMessage());
        } finally {
            DBConnectionUtil.close();
        }
        return true;
    }

}
