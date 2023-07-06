package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;

import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;
import pl.shopmatelist.shopmatelist.mapper.WeeklyFoodPlanMapper;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;
import pl.shopmatelist.shopmatelist.repository.WeeklyFoodPlanRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeeklyFoodPlanService {

    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;
    private final WeeklyFoodPlanMapper weeklyFoodPlanMapper;
    private final UserService userService;
    private final FoodPlansRepository foodPlansRepository;

    public WeeklyFoodPlanDTO findById(Long weeklyFoodPlanId, String token) {

        Optional<WeeklyFoodPlan> optionalWeeklyFoodPlan = weeklyFoodPlanRepository.findById(weeklyFoodPlanId);

        if (optionalWeeklyFoodPlan.isPresent()) {
            WeeklyFoodPlan foundWeeklyFoodPlan = optionalWeeklyFoodPlan.get();

            if (userAuthorizedByFoodPlan(foundWeeklyFoodPlan, token)) {
                return weeklyFoodPlanMapper.toDto(foundWeeklyFoodPlan);
            } else {
                throw new AuthorizationServiceException("Nie masz dostępu do tego plany tygodniowego");
            }

        }
        throw new NoSuchElementException();
    }

    public List<WeeklyFoodPlanDTO> findAllByFoodPlanId(Long FoodPlanId, String token) {
        User user = userService.userFromToken(token);
        List<FoodPlans> foodPlans = foodPlansRepository.findAllByUser(user);

        boolean isAuthorized = foodPlans.stream()
                .anyMatch(foodPlan -> foodPlan.getFoodPlanId().equals(FoodPlanId));
        if (isAuthorized) {
            List<WeeklyFoodPlan> WeeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(FoodPlanId);
            return weeklyFoodPlanMapper.toDtoList(WeeklyFoodPlans);
        }
        throw new AuthorizationServiceException("Nie masz dostępu do tego planu tygodniowego");
    }

    public WeeklyFoodPlanDTO save(WeeklyFoodPlanDTO weeklyFoodPlanDTO, String token) {

        List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(weeklyFoodPlanDTO.getFoodPlanId());
        if(weeklyFoodPlans.stream().anyMatch(userWeeklyFoodPlan -> userWeeklyFoodPlan.getRecipes().getRecipeId().equals(weeklyFoodPlanDTO.getRecipeId()))){
            throw new IllegalArgumentException("Dany plan tygodniowy znajduje się już na liście, nie możesz dodać go ponownie");
        }

        WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanMapper.toEntity(weeklyFoodPlanDTO);
        if(userAuthorizedByFoodPlan(weeklyFoodPlan, token)){
            WeeklyFoodPlan savedWeeklyFoodPlan = weeklyFoodPlanRepository.save(weeklyFoodPlan);
            return weeklyFoodPlanMapper.toDto(savedWeeklyFoodPlan);
        }
        throw new AuthorizationServiceException("Nie możesz zapisać tego planu tygodniowego");

    }

    public void deleteById(Long id, String token) {
        Optional<WeeklyFoodPlan> weeklyFoodPlan = weeklyFoodPlanRepository.findById(id);
        if (weeklyFoodPlan.isPresent()) {
            WeeklyFoodPlan foundWeeklyFoodPlan = weeklyFoodPlan.get();
            if (userAuthorizedByFoodPlan(foundWeeklyFoodPlan, token)) {
                weeklyFoodPlanRepository.delete(foundWeeklyFoodPlan);
                return;
            }
            throw new AuthorizationServiceException("Nie masz dostępu do tego planu tygodniowego");
        }
        throw new NoSuchElementException("Nie ma takiego planu tygodniowego");
    }

    public WeeklyFoodPlanDTO update(WeeklyFoodPlanDTO weeklyFoodPlanDTO, String token) {
        if(weeklyFoodPlanDTO.getWeeklyFoodPlanId() == null){
            throw new IllegalArgumentException("Taki plan tygodniowy nie istnieje");
        }

        WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanMapper.toEntity(weeklyFoodPlanDTO);
        if(userAuthorizedByFoodPlan(weeklyFoodPlan, token)){

            List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(weeklyFoodPlanDTO.getFoodPlanId());
            if(weeklyFoodPlans.stream().anyMatch(userWeeklyFoodPlan -> userWeeklyFoodPlan.getRecipes().getRecipeId().equals(weeklyFoodPlanDTO.getRecipeId()))){
                throw new IllegalArgumentException("Dany plan tygodniowy znajduje się już na liście, nie możesz dodać go ponownie");
            }
            WeeklyFoodPlan updatedWeeklyFoodPlan = weeklyFoodPlanRepository.save(weeklyFoodPlan);
            return weeklyFoodPlanMapper.toDto(updatedWeeklyFoodPlan);
        }
       throw new AuthorizationServiceException("Nie masz dostępu do tego planu tygodniowego");

    }

    public boolean userAuthorizedByFoodPlan(WeeklyFoodPlan weeklyFoodPlan, String token) {
        User user = userService.userFromToken(token);
        List<FoodPlans> foodPlans = foodPlansRepository.findAllByUser(user);

        return foodPlans.stream()
                .anyMatch(foodPlan -> foodPlan.getFoodPlanId().equals(weeklyFoodPlan.getFoodPlan().getFoodPlanId()));

    }


}
