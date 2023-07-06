package pl.shopmatelist.shopmatelist.exceptions;

public class ShoppingListNotFoundException extends RuntimeException{
    public ShoppingListNotFoundException(String message) {
        super(message);
    }
}
