package pl.shopmatelist.shopmatelist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shopmatelist.shopmatelist.entity.MealType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWeeklyFoodPlanDTO {

    private Long weeklyFoodPlanId;
    private Long foodPlanId;
    private String foodPlanName;
    private MealType mealType;
    private Long recipeId;
    private String recipeName;



}
