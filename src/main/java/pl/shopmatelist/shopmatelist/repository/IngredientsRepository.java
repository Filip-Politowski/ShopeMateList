package pl.shopmatelist.shopmatelist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Products;


import java.util.List;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {

    @Query("SELECT i.product FROM Ingredients i WHERE i.recipe.recipeId = :recipeId")
    List<Products> findProductsByRecipeId(@Param("recipeId") Long recipeId);

    @Query("SELECT i FROM Ingredients  i WHERE i.recipe.recipeId = :recipeId")
    List<Ingredients> findIngredientsByRecipeId(@Param("recipeId")long recipeId);
}
