package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.FoodPlansDTO;
import pl.shopmatelist.shopmatelist.services.FoodPlansService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/food-plans")
public class FoodPlansController {

    private final FoodPlansService foodPlansService;

    @GetMapping("/{id}")
    public FoodPlansDTO FindFoodPlanById(@PathVariable Long id){
        return foodPlansService.findById(id);
    }

    @GetMapping()
    public List<FoodPlansDTO> findAllFoodPlans(){
        return foodPlansService.findAll();
    }

    @PostMapping()
    public FoodPlansDTO createFoodPlan(@RequestBody FoodPlansDTO foodPlansDTO){
        return foodPlansService.save(foodPlansDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFoodPlanById(@PathVariable Long id){
        foodPlansService.delete(id);
    }

    @PutMapping()
    public FoodPlansDTO updateFoodPlan(FoodPlansDTO foodPlansDTO){
        return foodPlansService.update(foodPlansDTO);
    }

}
