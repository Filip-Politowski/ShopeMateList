package pl.shopmatelist.shopmatelist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRecipesDTO {
    private Long recipeId;
    private String recipeName;
    private String recipeDescription;
    private Long UserId;
}