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
    public WeeklyFoodPlanDTO findWeeklyFoodPlanById(@PathVariable Long id){
        return weeklyFoodPlanService.findById(id);
    }
    @GetMapping()
    public List<WeeklyFoodPlanDTO> findAllWeeklyFoodPlans(){
        return weeklyFoodPlanService.findAll();
    }

    @GetMapping("/all/{id}")
    public List<WeeklyFoodPlanDTO> findAllWeeklyFoodPlansById(@PathVariable Long id){
        return weeklyFoodPlanService.findAllByFoodPlanId(id);
    }

    @PostMapping()
    public WeeklyFoodPlanDTO createWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO){
        return weeklyFoodPlanService.save(weeklyFoodPlanDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteWeeklyFoodPlan(@PathVariable Long id){
        weeklyFoodPlanService.deleteById(id);
    }

    @PutMapping()
    public WeeklyFoodPlanDTO updateWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO){
        return weeklyFoodPlanService.update(weeklyFoodPlanDTO);
    }


}
