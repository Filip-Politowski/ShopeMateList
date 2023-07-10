package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestWeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseWeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.services.WeeklyFoodPlanService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/weekly-food-plan")
public class WeeklyFoodPlanController {

    private final WeeklyFoodPlanService weeklyFoodPlanService;

    @GetMapping("/{id}")
    public ResponseWeeklyFoodPlanDTO findWeeklyFoodPlanById(@PathVariable Long id) {

            return weeklyFoodPlanService.findById(id);

    }

    @GetMapping("/food-plan/{id}")
    public List<ResponseWeeklyFoodPlanDTO> findAllWeeklyFoodPlansByFoodPlanId(@PathVariable Long id, @RequestParam(name ="sort", defaultValue = "false")boolean sort) {

            return weeklyFoodPlanService.findAllByFoodPlanId(id, sort);
    }

    @PostMapping()
    public ResponseWeeklyFoodPlanDTO createWeeklyFoodPlan(@RequestBody RequestWeeklyFoodPlanDTO requestWeeklyFoodPlanDTO) {

            return weeklyFoodPlanService.save(requestWeeklyFoodPlanDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteWeeklyFoodPlan(@PathVariable Long id) {
            weeklyFoodPlanService.deleteById(id);
    }

    @PutMapping()
    public ResponseWeeklyFoodPlanDTO updateWeeklyFoodPlan(@RequestBody RequestWeeklyFoodPlanDTO requestWeeklyFoodPlanDTO) {

            return weeklyFoodPlanService.update(requestWeeklyFoodPlanDTO);
    }

}
