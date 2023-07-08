package pl.shopmatelist.shopmatelist.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestFoodPlansDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseFoodPlansDTO;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FoodPlansMapper {

    private final FoodPlansRepository foodPlansRepository;

    public ResponseFoodPlansDTO toDto(FoodPlans foodPlans){
        ResponseFoodPlansDTO dto = new ResponseFoodPlansDTO();
        dto.setFoodPlanId(foodPlans.getFoodPlanId());
        dto.setFoodPlanName(foodPlans.getFoodPlanName());
        dto.setUserId(foodPlans.getUser().getId());
        return dto;
    }

    public FoodPlans toEntity(RequestFoodPlansDTO responseFoodPlansDTO){
        FoodPlans foodPlans = new FoodPlans();
        foodPlans.setFoodPlanId(responseFoodPlansDTO.getFoodPlanId());
        foodPlans.setFoodPlanName(responseFoodPlansDTO.getFoodPlanName());

        return foodPlans;
    }

    public List<ResponseFoodPlansDTO> toDtoList(List<FoodPlans> foodPlans){
        return foodPlans.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
