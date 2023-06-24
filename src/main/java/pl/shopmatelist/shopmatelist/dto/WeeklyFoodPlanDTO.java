package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyFoodPlanDTO {

    private Long weeklyFoodPlanId;
    private String mealType;
    private Long recipeId;
    private String recipeName;


}
