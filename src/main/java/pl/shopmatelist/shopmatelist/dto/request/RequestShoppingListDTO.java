package pl.shopmatelist.shopmatelist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestShoppingListDTO {

    private Long shoppingListId;
    private LocalDate shoppingDate;
    private Long marketId;
}
