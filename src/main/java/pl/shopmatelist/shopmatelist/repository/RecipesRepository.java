package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shopmatelist.shopmatelist.entity.Recipes;

public interface RecipesRepository extends JpaRepository<Recipes, Long> {
}
