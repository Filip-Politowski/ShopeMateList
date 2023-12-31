package pl.shopmatelist.shopmatelist.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestIngredientsDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseIngredientsDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IngredientsMapper {

    private final RecipesRepository recipesRepository;
    private final ProductsRepository productsRepository;



    public ResponseIngredientsDTO toDTO(Ingredients ingredients) {
        ResponseIngredientsDTO dto = new ResponseIngredientsDTO();
        dto.setIngredientId(ingredients.getIngredientId());
        dto.setRecipeId(ingredients.getRecipe().getRecipeId());
        dto.setProductId(ingredients.getProduct().getProductId());
        dto.setQuantity(ingredients.getQuantity());
        dto.setRecipeName(ingredients.getRecipe().getRecipeName());
        dto.setProductName(ingredients.getProduct().getProductName());
        return dto;
    }

    public Ingredients toEntity(RequestIngredientsDTO dto) {
        Ingredients ingredients = new Ingredients();

        ingredients.setIngredientId(dto.getIngredientId());

        Recipes recipe = recipesRepository.findById(dto.getRecipeId()).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
        ingredients.setRecipe(recipe);

        Products product = productsRepository.findById(dto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        ingredients.setProduct(product);

        ingredients.setQuantity(dto.getQuantity());

        return ingredients;
    }

    public List<ResponseIngredientsDTO> toDtoList(List<Ingredients> ingredients) {
        return ingredients.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}