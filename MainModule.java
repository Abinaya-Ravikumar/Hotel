import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.Customer;
import entity.MenuItem;
import entity.Restaurant;
import entity.OrderItem;
import entity.Payment;
import entity.Order;
import service.CustomerService;
import service.CustomerServiceImpl;
import service.MenuService;
import service.MenuServiceImpl;
import service.OrderService;
import service.OrderServiceImpl;
import service.PaymentService;
import service.PaymentServiceImpl;
import service.PriceCalculation;
import service.RestaurantService;
import service.RestaurantServiceImpl;

public class MainModule
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);

        while(true){
            
            System.out.println("=== Welcome to Online Food Delivery System ===");
            System.out.println("1. Register Customer\n2. Create Restuarant\n3. Create Menu Item\n4.View Restuarants\n5. Place Order\n6. View Orers\n7. Make Payments\n8. Exit");

            System.out.print("Choose an option: ");
            int control = sc.nextInt();
            System.out.println();
            sc.nextLine();
            

            if(control == 1){

                System.out.print("Enter Customer name: ");
                String name = sc.next();
                System.out.println();
                
                System.out.print("Enter Customer email: ");
                String email = sc.next();
                System.out.println();
                
                System.out.print("Enter Customer phone number: ");
                String phoneNumber = sc.next();
                System.out.println();
                
                System.out.print("Enter Customer password: ");
                String password = sc.next();
                System.out.println();

                Customer customer = new Customer(name, email, phoneNumber, password);
                CustomerService customerService = new CustomerServiceImpl();
                
                try{
                    customerService.createCustomer(customer);
                    System.out.println("Customer Registered Successfully");
                }
                    
                catch(Exception e){
                    System.out.println("Email is already taken");
                }
                    
            }

            else if(control == 2){

                System.out.print("Enter Restaurant name: ");
                String name = sc.nextLine();

                System.out.print("Enter Restaurant address: ");
                String address = sc.nextLine();

                System.out.print("Enter Cuisine Type: ");
                String cuisine = sc.nextLine();

                System.out.print("Enter contact number: ");
                String contactNumber = sc.nextLine();

                Restaurant restaurant = new Restaurant(name, address, cuisine, contactNumber);
                RestaurantService restaurantService = new RestaurantServiceImpl();

                if(restaurantService.createRestaurant(restaurant))
                    System.out.println("Restuarant Created Successfully");
                else
                    System.err.println("Some error araised while creating restaurant");
                    
            }

            else if(control == 3){
                
                System.out.print("Enter Restaurant ID to add menu: ");
                int restaurantId = sc.nextInt();
                System.out.println();
                sc.nextLine();

                System.out.print("Enter menu item name: ");
                String name = sc.nextLine();
                System.out.println();

                System.out.print("Enter menu item price: ");
                double price = sc.nextDouble();
                System.out.println();
                sc.nextLine();

                System.out.print("Enter menu item description: ");
                String description = sc.nextLine();
                System.out.println();

                System.out.print("Enter available quantity: ");
                int quantity = sc.nextInt();
                System.out.println();
                sc.nextLine();

                MenuItem menuItem = new MenuItem(restaurantId, name, price, description, quantity);
                MenuService menuService = new MenuServiceImpl();

                try {
                    
                    menuService.createMenuItem(menuItem);
                    System.out.println("Menu added successfully");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(control == 4){

                RestaurantService restaurantService = new RestaurantServiceImpl();
                List<Restaurant> list = restaurantService.getAllRestaurant();
                System.out.println("=== List of Restaurants ===");

                for(Restaurant restaurant : list){
                    System.out.println(restaurant.toString());
                    System.out.println("=====================================");
                    System.out.println();
                }
                    
            }

            else if(control == 5){
                try {
                    System.out.print("Enter Customer ID to order: ");
                    int customerId = sc.nextInt();
                    System.out.println();

                    System.out.print("Enter Restaurant ID to order: ");
                    int restaurantId = sc.nextInt();
                    System.out.println();
                    sc.nextLine();

                    System.out.print("Enter delivery address to order: ");
                    String deliveryAddress = sc.nextLine();
                    System.out.println();

                    double total = 0;

                    List<OrderItem> list = new ArrayList<>();

                    MenuService menuService = new MenuServiceImpl();
                    List<MenuItem> menuItems = menuService.listMenuItemByRestaurat(restaurantId);
                    System.out.println("=== List of Menu Items ===");

                    if(menuItems.isEmpty()) throw new Exception("");
                    
                    for(MenuItem menuItem : menuItems){
                        System.out.println(menuItem.toString());
                        System.out.println("====================================");
                        System.out.println();
                    }
                    
                    while(true){
                        
                        System.out.print("Enter item ID to order: ");
                        int itemId = sc.nextInt();
                        System.out.println();

                        System.out.print("Enter quantity to order: ");
                        int quantity = sc.nextInt();
                        System.out.println();
                        
                        double price = PriceCalculation.getPrice(itemId);
                        double subTotal = price * quantity;
                        total += subTotal;
                        OrderItem orderItem = new OrderItem(itemId, quantity);
                        list.add(orderItem);

                        System.out.print("Enter -1 to exit: ");
                        int flag = sc.nextInt();
                        sc.nextLine();
                        if(flag == -1) break;

                    }
                    Order order = new Order(customerId, restaurantId, "PENDING", total, deliveryAddress);
                    
                    OrderService orderService = new OrderServiceImpl();
                    orderService.createOrder(order, list);

                } catch (Exception e) {
                    System.out.println("No menu available for this restaurant");
                }
            }

            else if(control == 6){
                System.out.print("Enter Customer ID: ");
                int customerId = sc.nextInt();
                System.out.println();
                sc.nextLine();

                try {
                    OrderService orderService = new OrderServiceImpl();
                    List<Order> list = orderService.getOrdersByCustomer(customerId);
                    System.out.println("=== List of orders ===");
                    for(Order order : list){
                        System.out.println(order.toString());
                        System.out.println("===================================");
                        System.out.println();
                    }

                } catch (Exception e) {
                    System.out.println("Invalid Customer ID");
                }
            }

            else if(control == 7){
                try {

                    System.out.print("Enter Order ID to make payment: ");
                    int orderId = sc.nextInt();
                    System.out.println();
                    sc.nextLine();

                    System.out.print("Enter amount you pay: ");
                    double amount = sc.nextDouble();
                    System.out.println();
                    sc.nextLine();

                    LocalDateTime currDateTime = LocalDateTime.now();

                    Payment payment = new Payment(orderId, currDateTime, "PENDING", amount);
                    
                    PaymentService paymentService = new PaymentServiceImpl();
                    paymentService.processPayment(payment); 
                    System.out.println();
                    System.out.println("Payment Completed Successfully");  
                    continue;

                } catch (Exception e) {
                    System.out.println("Entered invalid amount");
                } 
            }

            else if(control == 8)
                break;
            
            else continue;
        }
        sc.close();
    }
}
