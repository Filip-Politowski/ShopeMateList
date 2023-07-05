package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.FoodPlansDTO;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;
import pl.shopmatelist.shopmatelist.mapper.FoodPlansMapper;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;
import pl.shopmatelist.shopmatelist.repository.WeeklyFoodPlanRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodPlansService {

    private final FoodPlansRepository foodPlansRepository;
    private final FoodPlansMapper foodPlansMapper;
    private final UserService userService;
    private final WeeklyFoodPlanRepository  weeklyFoodPlanRepository;

    public FoodPlansDTO findById(Long foodPlanId, String token) {
        User user = userService.userFromToken(token);

        Optional<FoodPlans> foodPlan = foodPlansRepository.findByFoodPlanIdAndUser(foodPlanId, user);
        if (foodPlan.isPresent()) {
            FoodPlans foundFoodPlan = foodPlan.get();
            return foodPlansMapper.toDto(foundFoodPlan);
        }
        throw new NoSuchElementException("Nie ma takiego planu");
    }

    public List<FoodPlansDTO> findAll(String token) {
        User user = userService.userFromToken(token);
        List<FoodPlans> foodPlans = foodPlansRepository.findAllByUser(user);
        return foodPlansMapper.toDtoList(foodPlans);
    }

    public FoodPlansDTO save(FoodPlansDTO foodPlansDTO, String token) {
        User user = userService.userFromToken(token);

        Optional<FoodPlans> userFoodPlan = foodPlansRepository.findByFoodPlanNameAndUser(foodPlansDTO.getFoodPlanName(), user);
        if(userFoodPlan.isPresent()) {
            throw new IllegalArgumentException("Taki plan już istnieje!");
        }

        FoodPlans foodPlan = foodPlansMapper.toEntity(foodPlansDTO);
        foodPlan.setUser(user);
        FoodPlans savedFoodPlan = foodPlansRepository.save(foodPlan);
        return foodPlansMapper.toDto(savedFoodPlan);
    }

    public void deleteById(Long id, String token) {
        User user = userService.userFromToken(token);
        Optional<FoodPlans> foodPlan = foodPlansRepository.findByFoodPlanIdAndUser(id, user);
        if (foodPlan.isPresent()) {
            FoodPlans foundFoodPlan = foodPlan.get();

            List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan(foundFoodPlan);
            weeklyFoodPlanRepository.deleteAll(weeklyFoodPlans);

            foodPlansRepository.deleteById(id);
            return;
        }
        throw new NoSuchElementException("Nie ma takiego planu");

    }

    public FoodPlansDTO update(FoodPlansDTO foodPlansDTO, String token) {

        if(foodPlansDTO.getFoodPlanId() == null) {
            throw new NoSuchElementException("Nie ma takiego przepisu!");
        }
        User user = userService.userFromToken(token);
        FoodPlans foodPlan = foodPlansMapper.toEntity(foodPlansDTO);
        foodPlan.setUser(user);
        FoodPlans updatedFoodPlan = foodPlansRepository.save(foodPlan);
        return foodPlansMapper.toDto(updatedFoodPlan);
    }
}

