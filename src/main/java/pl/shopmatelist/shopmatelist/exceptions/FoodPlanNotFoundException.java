package pl.shopmatelist.shopmatelist.exceptions;

public class FoodPlanNotFoundException extends RuntimeException {
    public FoodPlanNotFoundException(String message) {
        super(message);
    }
}
