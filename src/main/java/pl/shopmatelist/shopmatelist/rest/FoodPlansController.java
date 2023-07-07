package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.FoodPlansDTO;
import pl.shopmatelist.shopmatelist.exceptions.FoodPlanNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.services.FoodPlansService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-plans")
public class FoodPlansController {

    private final FoodPlansService foodPlansService;

    @GetMapping("/{id}")
    public FoodPlansDTO FindFoodPlanById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return foodPlansService.findById(id, token);
        } catch (FoodPlanNotFoundException exc) {
            throw new FoodPlanNotFoundException(exc.getMessage());
        }

    }

    @GetMapping()
    public List<FoodPlansDTO> findAllFoodPlans(@RequestHeader("Authorization") String token) {
        try {
            return foodPlansService.findAll(token);
        } catch (FoodPlanNotFoundException exc) {
            throw new FoodPlanNotFoundException(exc.getMessage());
        }
    }

    @PostMapping()
    public FoodPlansDTO createFoodPlan(@RequestBody FoodPlansDTO foodPlansDTO, @RequestHeader("Authorization") String token) {
        try {
            return foodPlansService.save(foodPlansDTO, token);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFoodPlanById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
       try {
           foodPlansService.deleteById(id, token);
       }catch (FoodPlanNotFoundException exc) {
           throw new FoodPlanNotFoundException(exc.getMessage());
       }

    }


    @PutMapping()
    public FoodPlansDTO updateFoodPlan(FoodPlansDTO foodPlansDTO, @RequestHeader("Authorization") String token) {
            return foodPlansService.update(foodPlansDTO, token);

    }

}
