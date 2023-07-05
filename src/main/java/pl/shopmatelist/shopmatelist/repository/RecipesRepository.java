package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.User;

import java.util.List;
import java.util.Optional;

public interface RecipesRepository extends JpaRepository<Recipes, Long> {
    Optional<Recipes> findByRecipeIdAndUser(Long id, User user);
    List<Recipes> findAllByUser(User user);
}
