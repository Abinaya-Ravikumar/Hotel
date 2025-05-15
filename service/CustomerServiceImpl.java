package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.Customer;
import exceptions.EmailAlreadyRegisteredException;
import util.DBConnectionUtil;

public class CustomerServiceImpl implements CustomerService{

    public boolean createCustomer(Customer customer) throws EmailAlreadyRegisteredException{
        String query = "INSERT INTO customer (name, email, phoneNumber, password) VALUES (?, ?, ?, ?)";
        
        try {
            Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            
            String name = customer.getName();
            String email = customer.getEmail();
            String phone_number = customer.getPhoneNumber();
            String password = customer.getPassword();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone_number);
            ps.setString(4, password);
            
            ps.executeUpdate();
            

        } catch (SQLException e) {

            DBConnectionUtil.close();
            throw new EmailAlreadyRegisteredException("Email ID " + customer.getEmail() + " is already taken");
        
        }
        finally{
            DBConnectionUtil.close();
        }
        return true;
    }
    
}
