package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.WeeklyFoodPlanNotFoundException;
import pl.shopmatelist.shopmatelist.services.WeeklyFoodPlanService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/weekly-food-plan")
public class WeeklyFoodPlanController {

    private final WeeklyFoodPlanService weeklyFoodPlanService;

    @GetMapping("/{id}")
    public WeeklyFoodPlanDTO findWeeklyFoodPlanById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return weeklyFoodPlanService.findById(id, token);
        } catch (WeeklyFoodPlanNotFoundException exc) {
            throw new WeeklyFoodPlanNotFoundException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @GetMapping("/food-plan/{id}")
    public List<WeeklyFoodPlanDTO> findAllWeeklyFoodPlansByFoodPlanId(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return weeklyFoodPlanService.findAllByFoodPlanId(id, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @PostMapping()
    public WeeklyFoodPlanDTO createWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO, @RequestHeader("Authorization") String token) {
        try {
            return weeklyFoodPlanService.save(weeklyFoodPlanDTO, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteWeeklyFoodPlan(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            weeklyFoodPlanService.deleteById(id, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        } catch (WeeklyFoodPlanNotFoundException exc) {
            throw new WeeklyFoodPlanNotFoundException(exc.getMessage());
        }

    }

    @PutMapping()
    public WeeklyFoodPlanDTO updateWeeklyFoodPlan(@RequestBody WeeklyFoodPlanDTO weeklyFoodPlanDTO, @RequestHeader("Authorization") String token) {
        try {
            return weeklyFoodPlanService.update(weeklyFoodPlanDTO, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        }
    }

}
