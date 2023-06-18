package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDTO {
    private Long shoppingListId;
    private Long marketId;
    private LocalDate shoppingDate;

}
