package pl.shopmatelist.shopmatelist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMarketDTO {

    private Long marketId;
    private String marketName;

}
