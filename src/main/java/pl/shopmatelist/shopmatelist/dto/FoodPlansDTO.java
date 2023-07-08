package pl.shopmatelist.shopmatelist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPlansDTO {
    private Long foodPlanId;
    private String foodPlanName;
    private Long UserId;

}
