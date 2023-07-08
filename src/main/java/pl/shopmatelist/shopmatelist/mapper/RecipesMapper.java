package pl.shopmatelist.shopmatelist.mapper;

import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestRecipesDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseRecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Recipes;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipesMapper {

    public ResponseRecipesDTO toDTO(Recipes recipes) {
        ResponseRecipesDTO dto = new ResponseRecipesDTO();
        dto.setRecipeId(recipes.getRecipeId());
        dto.setRecipeName(recipes.getRecipeName());
        dto.setRecipeDescription(recipes.getRecipeDescription());
        dto.setUserId(recipes.getUser().getId());
        return dto;
    }

    public Recipes toEntity(RequestRecipesDTO dto) {
        Recipes recipes = new Recipes();
        recipes.setRecipeId(dto.getRecipeId());
        recipes.setRecipeName(dto.getRecipeName());
        recipes.setRecipeDescription(dto.getRecipeDescription());
        return recipes;
    }
    public List<ResponseRecipesDTO> toDtoList(List<Recipes> recipes){
        return  recipes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
