package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.services.WeeklyFoodPlanService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WeeklyFoodPlanController {

    private final WeeklyFoodPlanService weeklyFoodPlanService;

    @GetMapping("/weeklyfoodplan/{id}")
    public WeeklyFoodPlanDTO findWeeklyFoodPlanById(@PathVariable Long id){
        return weeklyFoodPlanService.findById(id);
    }
    @GetMapping("/weeklyfoodplan")
    public List<WeeklyFoodPlanDTO> findAllWeeklyFoodPlans(){
        return weeklyFoodPlanService.findAll();
    }

    @GetMapping("/weeklyfoodplan/all/{id}")
    public List<WeeklyFoodPlanDTO> findAllWeeklyFoodPlansById(@PathVariable Long id){
        return weeklyFoodPlanService.findAllByFoodPlanId(id);
    }

    @PostMapping("/weeklyfoodplan")
    public WeeklyFoodPlanDTO createWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO){
        return weeklyFoodPlanService.save(weeklyFoodPlanDTO);
    }

    @DeleteMapping("/weeklyfoodplan/{id}")
    public void deleteWeeklyFoodPlan(@PathVariable Long id){
        weeklyFoodPlanService.deleteById(id);
    }

    @PutMapping("/weeklyfoodplan")
    public WeeklyFoodPlanDTO updateWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO){
        return weeklyFoodPlanService.update(weeklyFoodPlanDTO);
    }


}
