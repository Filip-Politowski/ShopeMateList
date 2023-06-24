package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;

@Repository
public interface WeeklyFoodPlanRepository extends JpaRepository<WeeklyFoodPlan, Long> {
}
