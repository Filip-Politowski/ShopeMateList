package pl.shopmatelist.shopmatelist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestIngredientsDTO {
    private Long ingredientId;
    private int quantity;
    private Long productId;
    private Long recipeId;
}
