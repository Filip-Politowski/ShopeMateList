package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shopmatelist.shopmatelist.entity.FoodCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {
    private Long productId;
    private String productName;
    private FoodCategory foodCategory;
}
