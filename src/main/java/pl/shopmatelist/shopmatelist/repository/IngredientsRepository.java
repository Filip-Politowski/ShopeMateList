package pl.shopmatelist.shopmatelist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.entity.Recipes;


import java.util.List;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {





    List<Ingredients> findAllByRecipe_RecipeId(Long recipeId);

    List<Ingredients> findAllByRecipe(Recipes recipeToDelete);

}
