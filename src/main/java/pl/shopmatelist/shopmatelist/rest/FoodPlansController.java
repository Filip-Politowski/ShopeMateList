package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestFoodPlansDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseFoodPlansDTO;
import pl.shopmatelist.shopmatelist.services.FoodPlansService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-plans")
public class FoodPlansController {

    private final FoodPlansService foodPlansService;

    @GetMapping("/{id}")
    public ResponseFoodPlansDTO FindFoodPlanById(@PathVariable Long id) {

            return foodPlansService.findById(id);
    }

    @GetMapping()
    public List<ResponseFoodPlansDTO> findAllFoodPlans() {

            return foodPlansService.findAll();
    }

    @PostMapping()
    public ResponseFoodPlansDTO createFoodPlan(@RequestBody RequestFoodPlansDTO requestFoodPlansDTO) {

            return foodPlansService.save(requestFoodPlansDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFoodPlanById(@PathVariable Long id) {

           foodPlansService.deleteById(id);
    }

    @PutMapping()
    public ResponseFoodPlansDTO updateFoodPlan(RequestFoodPlansDTO requestFoodPlansDTO) {
            return foodPlansService.update(requestFoodPlansDTO);

    }

}
