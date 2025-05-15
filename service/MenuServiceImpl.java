package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.MenuItem;
import exceptions.RestaurantNotFoundException;
import util.DBConnectionUtil;

public class MenuServiceImpl implements MenuService{

    public boolean createMenuItem(MenuItem menuItem) throws RestaurantNotFoundException{
        String query = "INSERT INTO menuItem (restaurantId, name, price, description, availableQuantity) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnectionUtil.getConnection();
            ps = conn.prepareStatement(query);

            ps.setInt(1, menuItem.getRestaurantId());
            ps.setString(2, menuItem.getName());
            ps.setDouble(3, menuItem.getPrice());
            ps.setString(4, menuItem.getDescription());
            ps.setInt(5, menuItem.getQuantity());

            ps.executeUpdate();
            

        } catch (SQLException e) {
            DBConnectionUtil.close();
            System.out.println(e.getMessage());
            throw new RestaurantNotFoundException("Restaurant ID :" + menuItem.getRestaurantId() + " is not found");
        } finally {
            DBConnectionUtil.close();
        }
        return true;
    }

    public List<MenuItem> listMenuItemByRestaurat(int restaurantId) {
        
        String query = "SELECT * FROM menuItem WHERE restaurantId = ?";
        List<MenuItem> menuItems = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, restaurantId);

            rs = ps.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("itemId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int quantity = rs.getInt("availableQuantity");

                MenuItem menuItem = new MenuItem(itemId, restaurantId, name, price, description, quantity);

                menuItems.add(menuItem);
            }
        } catch (Exception e){
            System.out.println("Error occured while quering menu: " + e.getMessage());
        } 
        
        finally {
            DBConnectionUtil.close();
        }

        return menuItems;
    }
}
