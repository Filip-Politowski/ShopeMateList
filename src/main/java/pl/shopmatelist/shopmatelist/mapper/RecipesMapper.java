package pl.shopmatelist.shopmatelist.mapper;

import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Recipes;

@Component
public class RecipesMapper {

    public RecipesDTO toDTO(Recipes recipes) {
        RecipesDTO dto = new RecipesDTO();
        dto.setRecipeId(recipes.getRecipeId());
        dto.setRecipeName(recipes.getRecipeName());
        dto.setRecipeDescription(recipes.getRecipeDescription());
        return dto;
    }

    public Recipes toEntity(RecipesDTO dto) {
        Recipes recipes = new Recipes();
        recipes.setRecipeId(dto.getRecipeId());
        recipes.setRecipeName(dto.getRecipeName());
        recipes.setRecipeDescription(dto.getRecipeDescription());
        return recipes;
    }
}
