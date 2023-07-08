package pl.shopmatelist.shopmatelist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFoodPlansDTO {
    private Long foodPlanId;
    private String foodPlanName;
    private Long UserId;

}
