package pl.shopmatelist.shopmatelist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductsOnListDTO {
    private Long listItemId;
    private int quantity;
    private Long productId;
    private Long shoppingListId;
}
