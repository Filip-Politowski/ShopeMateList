package pl.shopmatelist.shopmatelist.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestWeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseWeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WeeklyFoodPlanMapper {
    private final RecipesRepository recipesRepository;
    private final FoodPlansRepository foodPlansRepository;

    public ResponseWeeklyFoodPlanDTO toDto(WeeklyFoodPlan weeklyFoodPlan){
        ResponseWeeklyFoodPlanDTO dto = new ResponseWeeklyFoodPlanDTO();
        dto.setWeeklyFoodPlanId(weeklyFoodPlan.getWeeklyFoodPlanId());
        dto.setMealType(weeklyFoodPlan.getMealType());
        dto.setRecipeId(weeklyFoodPlan.getRecipes().getRecipeId());
        dto.setRecipeName(weeklyFoodPlan.getRecipes().getRecipeName());
        dto.setFoodPlanId(weeklyFoodPlan.getFoodPlan().getFoodPlanId());
        dto.setFoodPlanName(weeklyFoodPlan.getFoodPlan().getFoodPlanName());
        return dto;
    }

    public WeeklyFoodPlan toEntity(RequestWeeklyFoodPlanDTO requestWeeklyFoodPlanDTO){
        WeeklyFoodPlan weeklyFoodPlan = new WeeklyFoodPlan();
        weeklyFoodPlan.setMealType(requestWeeklyFoodPlanDTO.getMealType());
        weeklyFoodPlan.setWeeklyFoodPlanId(requestWeeklyFoodPlanDTO.getWeeklyFoodPlanId());

        Recipes recipes = recipesRepository.findById(requestWeeklyFoodPlanDTO.getRecipeId()).orElseThrow(() -> new NoSuchElementException("Nie ma takiego przepisu"));
        weeklyFoodPlan.setRecipes(recipes);

        FoodPlans foodPlans = foodPlansRepository.findById(requestWeeklyFoodPlanDTO.getFoodPlanId()).orElseThrow(() -> new NoSuchElementException("Nie ma takiego planu"));
        weeklyFoodPlan.setFoodPlan(foodPlans);

        return weeklyFoodPlan;
    }

    public List<ResponseWeeklyFoodPlanDTO> toDtoList(List<WeeklyFoodPlan> weeklyFoodPlanList){
        return weeklyFoodPlanList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
