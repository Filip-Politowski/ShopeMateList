package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;

@Repository
public interface FoodPlansRepository extends JpaRepository<FoodPlans, Long> {
}
