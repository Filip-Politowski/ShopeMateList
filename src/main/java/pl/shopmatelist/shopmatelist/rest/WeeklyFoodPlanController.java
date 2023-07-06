package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.services.WeeklyFoodPlanService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/weekly-food-plan")
public class WeeklyFoodPlanController {

    private final WeeklyFoodPlanService weeklyFoodPlanService;

    @GetMapping("/{id}")
    public WeeklyFoodPlanDTO findWeeklyFoodPlanById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return weeklyFoodPlanService.findById(id, token);
    }
    @GetMapping("/food-plan/{id}")
    public List<WeeklyFoodPlanDTO> findAllWeeklyFoodPlansByFoodPlanId(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return weeklyFoodPlanService.findAllByFoodPlanId(id,token);
    }

    @PostMapping()
    public WeeklyFoodPlanDTO createWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO, @RequestHeader("Authorization") String token){
        return weeklyFoodPlanService.save(weeklyFoodPlanDTO, token);
    }

    @DeleteMapping("/{id}")
    public void deleteWeeklyFoodPlan(@PathVariable Long id, @RequestHeader("Authorization") String token){
        weeklyFoodPlanService.deleteById(id, token);
    }

    @PutMapping()
    public WeeklyFoodPlanDTO updateWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO, @RequestHeader("Authorization") String token){
        return weeklyFoodPlanService.update(weeklyFoodPlanDTO, token );
    }


}
