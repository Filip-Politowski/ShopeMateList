package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shopmatelist.shopmatelist.entity.MealType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyFoodPlanDTO {

    private Long weeklyFoodPlanId;
    private Long foodPlanId;
    private String foodPlanName;
    private MealType mealType;
    private Long recipeId;
    private String recipeName;



}
