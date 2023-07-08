package pl.shopmatelist.shopmatelist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shopmatelist.shopmatelist.entity.MealType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestWeeklyFoodPlanDTO {
    private Long weeklyFoodPlanId;
    private MealType mealType;
    private Long foodPlanId;
    private Long recipeId;
}
