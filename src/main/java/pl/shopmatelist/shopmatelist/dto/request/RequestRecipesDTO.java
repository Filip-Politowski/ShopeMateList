package pl.shopmatelist.shopmatelist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRecipesDTO {
    private Long recipeId;
    private String recipeDescription;
    private String recipeName;
}
