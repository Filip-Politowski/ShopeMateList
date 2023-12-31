package pl.shopmatelist.shopmatelist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseShoppingListDTO {
    private Long shoppingListId;
    private Long marketId;
    private String marketName;
    private LocalDate shoppingDate;
    private Boolean owner;
    private Long User;

}
