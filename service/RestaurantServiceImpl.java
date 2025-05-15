package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Restaurant;
import util.DBConnectionUtil;

public class RestaurantServiceImpl implements RestaurantService{
    
    public boolean createRestaurant(Restaurant restaurant) {
        String query = "INSERT INTO restaurant (name, address, cuisineType, contactNumber) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            String name = restaurant.getName();
            String address = restaurant.getAddress();
            String cuisineType = restaurant.getCuisineType();
            String contactNumber = restaurant.getContactNumber();

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, cuisineType);
            ps.setString(4, contactNumber);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error occured: " + e.getMessage());
        } finally {
            DBConnectionUtil.close();
        }
        return false;
    }

    public List<Restaurant> getAllRestaurant() {
        
        String query = "SELECT * FROM restaurant";
        List<Restaurant> restaurants = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int restaurantId = rs.getInt("restaurantId");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String cuisineType = rs.getString("cuisineType");
                String contactNumber = rs.getString("contactNumber");

                Restaurant restaurant = new Restaurant(restaurantId, name, address, cuisineType, contactNumber);

                restaurants.add(restaurant);
            }
        }catch (SQLException e){
            System.out.println("Error while quering restaurants " + e.getMessage());
        } 
        finally {
            DBConnectionUtil.close();
        }

        return restaurants;
    }
}
