package pl.shopmatelist.shopmatelist.exceptions;

public class WeeklyFoodPlanNotFoundException extends RuntimeException{
    public WeeklyFoodPlanNotFoundException(String message) {
        super(message);
    }
}
