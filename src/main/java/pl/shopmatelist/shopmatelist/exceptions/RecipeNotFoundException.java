package pl.shopmatelist.shopmatelist.exceptions;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
