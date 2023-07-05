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
    public FoodPlansDTO FindFoodPlanById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return foodPlansService.findById(id, token);
    }

    @GetMapping()
    public List<FoodPlansDTO> findAllFoodPlans(@RequestHeader("Authorization") String token){
        return foodPlansService.findAll(token);

    }

    @PostMapping()
    public FoodPlansDTO createFoodPlan(@RequestBody FoodPlansDTO foodPlansDTO, @RequestHeader("Authorization") String token){
        return foodPlansService.save(foodPlansDTO, token);
    }

    @DeleteMapping("/{id}")
    public void deleteFoodPlanById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        foodPlansService.deleteById(id, token);
    }



    @PutMapping()
    public FoodPlansDTO updateFoodPlan(FoodPlansDTO foodPlansDTO, @RequestHeader("Authorization") String token){
        return foodPlansService.update(foodPlansDTO, token);
    }

}
