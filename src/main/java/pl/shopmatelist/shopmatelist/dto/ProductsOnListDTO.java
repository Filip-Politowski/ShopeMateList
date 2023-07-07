package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shopmatelist.shopmatelist.entity.FoodCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsOnListDTO {

    private Long listItemId;
    private Long shoppingListId;
    private Long productId;
    private String productName;
    private FoodCategory foodCategory;
    private int quantity;

}
