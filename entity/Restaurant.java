package entity;


public class Restaurant {
    private int restaurantId;
    private String name;
    private String address;
    private String cuisineType;
    private String contactNumber;

    public Restaurant(String name, String address, String cuisineType, String contactNumber){
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.contactNumber = contactNumber;
    }
    
    public Restaurant(int restaurantId, String name, String address, String cuisineType, String contactNumber){
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.contactNumber = contactNumber;
    }

    public void setId(int id) {
        this.restaurantId = id;
    }

    public int getId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String toString(){
        return "Restaurant ID: " + this.restaurantId +
           "\nName: " + this.name +
           "\nAddress: " + this.address +
           "\nCuisine Type: " + this.cuisineType +
           "\nContact Number: " + this.contactNumber;
    }
}
