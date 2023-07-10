package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestWeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseWeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.entity.*;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.WeeklyFoodPlanNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.WeeklyFoodPlanMapper;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;
import pl.shopmatelist.shopmatelist.repository.WeeklyFoodPlanRepository;
import pl.shopmatelist.shopmatelist.services.security.AuthenticationService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeeklyFoodPlanService {

    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;
    private final WeeklyFoodPlanMapper weeklyFoodPlanMapper;

    private final FoodPlansRepository foodPlansRepository;
    private final RecipesRepository recipesRepository;
    private final AuthenticationService authenticationService;

    public ResponseWeeklyFoodPlanDTO findById(Long weeklyFoodPlanId) {

        Optional<WeeklyFoodPlan> optionalWeeklyFoodPlan = weeklyFoodPlanRepository.findById(weeklyFoodPlanId);

        if (optionalWeeklyFoodPlan.isPresent()) {
            WeeklyFoodPlan foundWeeklyFoodPlan = optionalWeeklyFoodPlan.get();

            if (userAuthorizedByFoodPlan(foundWeeklyFoodPlan, authenticationService.authenticatedUser())) {
                return weeklyFoodPlanMapper.toDto(foundWeeklyFoodPlan);
            } else {
                throw new AuthorizationException("Nie masz dostępu do tego posiłku w planie tygodniowym");
            }

        }
        throw new WeeklyFoodPlanNotFoundException("Nie ma posiłku w planie tygodniowym o id: " + weeklyFoodPlanId);
    }

    public List<ResponseWeeklyFoodPlanDTO> findAllByFoodPlanId(Long FoodPlanId, boolean sort) {

        List<FoodPlans> foodPlans = foodPlansRepository.findAllByUser(authenticationService.authenticatedUser());

        boolean isAuthorized = foodPlans.stream()
                .anyMatch(foodPlan -> foodPlan.getFoodPlanId().equals(FoodPlanId));
        if (isAuthorized) {
            List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(FoodPlanId);
            if(sort){
                weeklyFoodPlans.sort(Comparator.comparing(WeeklyFoodPlan::getMealType));
            }
            return weeklyFoodPlanMapper.toDtoList(weeklyFoodPlans);
        }
        throw new AuthorizationException("Nie masz dostępu do tego planu tygodniowego");
    }

    public ResponseWeeklyFoodPlanDTO save(RequestWeeklyFoodPlanDTO requestWeeklyFoodPlanDTO) {

        List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(requestWeeklyFoodPlanDTO.getFoodPlanId());
        if (weeklyFoodPlans.stream().anyMatch(userWeeklyFoodPlan -> userWeeklyFoodPlan.getRecipes().getRecipeId().equals(requestWeeklyFoodPlanDTO.getRecipeId()))) {
            throw new IllegalArgumentException("Dany posiłek w planie tygodniowym znajduje się już na liście, nie możesz dodać go ponownie");
        }

        WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanMapper.toEntity(requestWeeklyFoodPlanDTO);
        if (userAuthorizedByFoodPlan(weeklyFoodPlan, authenticationService.authenticatedUser())) {
            if(!recipeAuthorization(weeklyFoodPlan, authenticationService.authenticatedUser())){
                throw new AuthorizationException("Nie masz dostępu do tego przepisu");
            }
            WeeklyFoodPlan savedWeeklyFoodPlan = weeklyFoodPlanRepository.save(weeklyFoodPlan);
            return weeklyFoodPlanMapper.toDto(savedWeeklyFoodPlan);
        }
        throw new AuthorizationException("Nie masz dostępu do tego planu tygodniowego");

    }

    public void deleteById(Long id) {
        Optional<WeeklyFoodPlan> weeklyFoodPlan = weeklyFoodPlanRepository.findById(id);
        if (weeklyFoodPlan.isPresent()) {
            WeeklyFoodPlan foundWeeklyFoodPlan = weeklyFoodPlan.get();
            if (userAuthorizedByFoodPlan(foundWeeklyFoodPlan, authenticationService.authenticatedUser())) {
                weeklyFoodPlanRepository.delete(foundWeeklyFoodPlan);
                return;
            }
            throw new AuthorizationException("Nie masz dostępu do tego planu tygodniowego");
        }
        throw new WeeklyFoodPlanNotFoundException("Nie ma planu tygodniowego o id: " + id);
    }

    public ResponseWeeklyFoodPlanDTO update(RequestWeeklyFoodPlanDTO requestWeeklyFoodPlanDTO) {
        if (requestWeeklyFoodPlanDTO.getWeeklyFoodPlanId() == null) {
            throw new IllegalArgumentException("Musisz podać id planu tygodniowego");
        }

        WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanMapper.toEntity(requestWeeklyFoodPlanDTO);
        if (userAuthorizedByFoodPlan(weeklyFoodPlan, authenticationService.authenticatedUser())) {

            List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(requestWeeklyFoodPlanDTO.getFoodPlanId());
            if (weeklyFoodPlans.stream().anyMatch(userWeeklyFoodPlan -> userWeeklyFoodPlan.getRecipes().getRecipeId().equals(requestWeeklyFoodPlanDTO.getRecipeId()))) {
                throw new IllegalArgumentException("Dany plan tygodniowy z id przepisu: " + requestWeeklyFoodPlanDTO.getRecipeId() + " znajduje się już na liście, nie możesz zmienić przepisu na taki, który aktualnie znajduje się w bazie danych");
            }
            if(!recipeAuthorization(weeklyFoodPlan,authenticationService.authenticatedUser())){
                throw new AuthorizationException("Nie masz dostępu do tego przepisu");
            }
            WeeklyFoodPlan updatedWeeklyFoodPlan = weeklyFoodPlanRepository.save(weeklyFoodPlan);
            return weeklyFoodPlanMapper.toDto(updatedWeeklyFoodPlan);
        }
        throw new AuthorizationException("Nie masz dostępu do tego planu tygodniowego");

    }

    public boolean userAuthorizedByFoodPlan(WeeklyFoodPlan weeklyFoodPlan, User user) {

        List<FoodPlans> foodPlans = foodPlansRepository.findAllByUser(user);
        return foodPlans.stream()
                .anyMatch(foodPlan -> foodPlan.getFoodPlanId().equals(weeklyFoodPlan.getFoodPlan().getFoodPlanId()));

    }

    public boolean recipeAuthorization(WeeklyFoodPlan weeklyFoodPlan, User user) {

        List<Recipes> recipes = recipesRepository.findAllByUser(user);
        return recipes.stream().anyMatch(recipe -> recipe.getRecipeId().equals(weeklyFoodPlan.getRecipes().getRecipeId()));

    }



}
