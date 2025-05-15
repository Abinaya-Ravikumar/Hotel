package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnectionUtil;

public class PriceCalculation {
    public static double getPrice(int id){
        double price = 0;
        try (Connection conn = DBConnectionUtil.getConnection()) {
            String query = "SELECT price FROM menuItem WHERE itemId = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                price = rs.getDouble(1);
            }

        } catch (SQLException e) {
            DBConnectionUtil.close();
            System.out.println("Error while getting the price of the item: " + e.getMessage());
        }finally{
            DBConnectionUtil.close();
        }
        return price;
    }
}
