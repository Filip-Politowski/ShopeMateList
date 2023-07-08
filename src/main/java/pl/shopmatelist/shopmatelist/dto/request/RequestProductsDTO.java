package pl.shopmatelist.shopmatelist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shopmatelist.shopmatelist.entity.FoodCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductsDTO {
    private Long productId;
    private FoodCategory foodCategory;
    private String productName;
}
