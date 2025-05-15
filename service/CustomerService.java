package service;

import entity.Customer;
import exceptions.EmailAlreadyRegisteredException;

public interface CustomerService {
    
    boolean createCustomer(Customer customer) throws EmailAlreadyRegisteredException;

}
