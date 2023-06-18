package pl.shopmatelist.shopmatelist.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

@Component
public class IngredientsMapper {
    private final IngredientsRepository ingredientsRepository;
    private final RecipesRepository recipesRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public IngredientsMapper(IngredientsRepository ingredientsRepository,
                             RecipesRepository recipesRepository,
                             ProductsRepository productsRepository) {
        this.ingredientsRepository = ingredientsRepository;
        this.recipesRepository = recipesRepository;
        this.productsRepository = productsRepository;
    }

    public IngredientsDTO toDTO(Ingredients ingredients) {
        IngredientsDTO dto = new IngredientsDTO();
        dto.setIngredientId(ingredients.getIngredientId());
        dto.setRecipeId(ingredients.getRecipe().getRecipeId());
        dto.setProductId(ingredients.getProduct().getProductId());
        dto.setQuantity(ingredients.getQuantity());
        return dto;
    }

    public Ingredients toEntity(IngredientsDTO dto) {
        Ingredients ingredients = new Ingredients();

        ingredients.setIngredientId(dto.getIngredientId());

        Recipes recipe = recipesRepository.findById(dto.getRecipeId()).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
        ingredients.setRecipe(recipe);

        Products product = productsRepository.findById(dto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        ingredients.setProduct(product);

        ingredients.setQuantity(dto.getQuantity());

        return ingredients;
    }
}