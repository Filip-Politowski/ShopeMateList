package pl.shopmatelist.shopmatelist.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.FoodPlansDTO;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FoodPlansMapper {

    private final FoodPlansRepository foodPlansRepository;

    public FoodPlansDTO toDto(FoodPlans foodPlans){
        FoodPlansDTO dto = new FoodPlansDTO();
        dto.setFoodPlanId(foodPlans.getFoodPlanId());
        dto.setFoodPlanName(foodPlans.getFoodPlanName());
        dto.setUserId(foodPlans.getUser().getId());
        return dto;
    }

    public FoodPlans toEntity(FoodPlansDTO foodPlansDTO){
        FoodPlans foodPlans = new FoodPlans();
        foodPlans.setFoodPlanId(foodPlansDTO.getFoodPlanId());
        foodPlans.setFoodPlanName(foodPlansDTO.getFoodPlanName());

        return foodPlans;
    }

    public List<FoodPlansDTO> toDtoList(List<FoodPlans> foodPlans){
        return foodPlans.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
