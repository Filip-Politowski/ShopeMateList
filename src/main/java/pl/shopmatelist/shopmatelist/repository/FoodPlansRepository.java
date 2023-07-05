package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodPlansRepository extends JpaRepository<FoodPlans, Long> {
    Optional<FoodPlans> findByFoodPlanIdAndUser(Long foodPlanId, User user);

    List<FoodPlans> findAllByUser(User user);

    Optional<FoodPlans> findByFoodPlanNameAndUser(String foodPlanName, User userFromToken);
}
