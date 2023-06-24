package pl.shopmatelist.shopmatelist.mapper;

import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WeeklyFoodPlanMapper {
    private final RecipesRepository recipesRepository;

    public WeeklyFoodPlanDTO toDto(WeeklyFoodPlan weeklyFoodPlan){
        WeeklyFoodPlanDTO dto = new WeeklyFoodPlanDTO();
        dto.setWeeklyFoodPlanId(weeklyFoodPlan.getWeeklyFoodPlanId());
        dto.setMealType(weeklyFoodPlan.getMealType());
        dto.setRecipeId(weeklyFoodPlan.getRecipes().getRecipeId());
        dto.setRecipeName(weeklyFoodPlan.getRecipes().getRecipeName());
        return dto;
    }

    public WeeklyFoodPlan toEntity(WeeklyFoodPlanDTO weeklyFoodPlanDTO){
        WeeklyFoodPlan weeklyFoodPlan = new WeeklyFoodPlan();
        weeklyFoodPlan.setMealType(weeklyFoodPlanDTO.getMealType());
        Recipes recipes = recipesRepository.findById(weeklyFoodPlanDTO.getRecipeId()).orElseThrow(() -> new NoSuchElementException("Nie ma takiego przepisu"));
        weeklyFoodPlan.setRecipes(recipes);
        return weeklyFoodPlan;
    }

    public List<WeeklyFoodPlanDTO> toDtoList(List<WeeklyFoodPlan> weeklyFoodPlanList){
        return weeklyFoodPlanList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
