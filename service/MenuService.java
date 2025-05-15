package service;

import java.util.List;

import entity.MenuItem;
import exceptions.RestaurantNotFoundException;

public interface MenuService {

    boolean createMenuItem(MenuItem MenuItem) throws RestaurantNotFoundException;
    List<MenuItem> listMenuItemByRestaurat(int restaurantId);

}
