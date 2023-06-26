package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;

import java.util.List;

@Repository
public interface WeeklyFoodPlanRepository extends JpaRepository<WeeklyFoodPlan, Long> {
    @Query("SELECT i FROM WeeklyFoodPlan i WHERE i.foodPlan.foodPlanId = :foodPlanId")
    List<WeeklyFoodPlan> findAllByFoodPlanId(Long foodPlanId);

}
