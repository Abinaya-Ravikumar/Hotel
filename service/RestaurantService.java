package service;

import java.util.List;

import entity.Restaurant;

public interface RestaurantService {
    
    boolean createRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurant();

}
