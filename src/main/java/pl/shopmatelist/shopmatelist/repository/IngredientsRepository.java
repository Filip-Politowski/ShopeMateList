package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shopmatelist.shopmatelist.entity.Ingredients;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
}
