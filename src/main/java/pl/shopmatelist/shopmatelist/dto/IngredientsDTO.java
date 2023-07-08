package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsDTO {
    private Long ingredientId;
    private Long recipeId;
    private String recipeName;
    private Long productId;
    private String productName;
    private int quantity;
}