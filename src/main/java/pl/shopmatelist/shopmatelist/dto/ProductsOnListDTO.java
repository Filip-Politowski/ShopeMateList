package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsOnListDTO {

    private Long listItemId;
    private Long shoppingListId;
    private Long productId;
    private String productName;
    private String productType;
    private int quantity;

}
