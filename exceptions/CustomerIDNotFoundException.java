package exceptions;

public class CustomerIDNotFoundException extends Exception{
    public CustomerIDNotFoundException(String message){
        super(message);
    }
}
