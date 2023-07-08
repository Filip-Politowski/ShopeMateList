package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestFoodPlansDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseFoodPlansDTO;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;
import pl.shopmatelist.shopmatelist.exceptions.FoodPlanNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.mapper.FoodPlansMapper;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;
import pl.shopmatelist.shopmatelist.repository.WeeklyFoodPlanRepository;
import pl.shopmatelist.shopmatelist.services.security.AuthenticationService;
import pl.shopmatelist.shopmatelist.services.security.UserService;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodPlansService {

    private final FoodPlansRepository foodPlansRepository;
    private final FoodPlansMapper foodPlansMapper;
    private final UserService userService;
    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;
    private final AuthenticationService authenticationService;

    public ResponseFoodPlansDTO findById(Long foodPlanId) {

        Optional<FoodPlans> foodPlan = foodPlansRepository.findByFoodPlanIdAndUser(foodPlanId, authenticationService.authenticatedUser());
        if (foodPlan.isPresent()) {
            FoodPlans foundFoodPlan = foodPlan.get();
            return foodPlansMapper.toDto(foundFoodPlan);
        }
        throw new FoodPlanNotFoundException("Nie ma planu o id: " + foodPlanId);
    }

    public List<ResponseFoodPlansDTO> findAll() {

        List<FoodPlans> foodPlans = foodPlansRepository.findAllByUser(authenticationService.authenticatedUser());
        if (foodPlans.isEmpty()) {
            throw new FoodPlanNotFoundException("Brak planów posiłków");
        }
        return foodPlansMapper.toDtoList(foodPlans);
    }

    public ResponseFoodPlansDTO save(RequestFoodPlansDTO requestFoodPlansDTO) {

        Optional<FoodPlans> userFoodPlan = foodPlansRepository.findByFoodPlanNameAndUser(requestFoodPlansDTO.getFoodPlanName(), authenticationService.authenticatedUser());
        if (userFoodPlan.isPresent()) {
            throw new IllegalArgumentException("Taki plan już istnieje!");
        }

        FoodPlans foodPlan = foodPlansMapper.toEntity(requestFoodPlansDTO);
        foodPlan.setUser(authenticationService.authenticatedUser());
        FoodPlans savedFoodPlan = foodPlansRepository.save(foodPlan);
        return foodPlansMapper.toDto(savedFoodPlan);
    }

    public void deleteById(Long id) {

        Optional<FoodPlans> foodPlan = foodPlansRepository.findByFoodPlanIdAndUser(id, authenticationService.authenticatedUser());
        if (foodPlan.isPresent()) {
            FoodPlans foundFoodPlan = foodPlan.get();

            List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan(foundFoodPlan);
            weeklyFoodPlanRepository.deleteAll(weeklyFoodPlans);

            foodPlansRepository.deleteById(id);
            return;
        }
        throw new FoodPlanNotFoundException("Nie ma planu o id: " + id);
    }

    public ResponseFoodPlansDTO update(RequestFoodPlansDTO requestFoodPlansDTO) {

        if (requestFoodPlansDTO.getFoodPlanId() == null) {
            throw new java.lang.IllegalArgumentException("Należy podać ID planu");
        }

        FoodPlans foodPlan = foodPlansMapper.toEntity(requestFoodPlansDTO);
        foodPlan.setUser(authenticationService.authenticatedUser());
        FoodPlans updatedFoodPlan = foodPlansRepository.save(foodPlan);
        return foodPlansMapper.toDto(updatedFoodPlan);
    }
}

