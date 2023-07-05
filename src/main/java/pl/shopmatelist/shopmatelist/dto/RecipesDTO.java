package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipesDTO {
    private Long recipeId;
    private String recipeName;
    private String recipeDescription;
    private Long UserId;
}