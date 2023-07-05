package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.mapper.RecipesMapper;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipesService {

    private final RecipesRepository recipesRepository;
    private final RecipesMapper recipesMapper;
    private final UserService userService;
    private final IngredientsRepository ingredientsRepository;


    public RecipesDTO findById(Long recipeId, String token) {
        User user = userService.userFromToken(token);
        Optional<Recipes> optionalRecipe = recipesRepository.findByRecipeIdAndUser(recipeId, user);
        if (optionalRecipe.isPresent()) {
            Recipes recipe = optionalRecipe.get();
            return recipesMapper.toDTO(recipe);
        }
        throw new NoSuchElementException("Nie ma takiego przepisu!");

    }

    public List<RecipesDTO> findAll(String token) {
        User user = userService.userFromToken(token);
        List<Recipes> recipes = recipesRepository.findAllByUser(user);
        return recipesMapper.toDtoList(recipes);
    }

    public RecipesDTO save(RecipesDTO recipesDTO, String token) {
        User user = userService.userFromToken(token);

        Optional<Recipes> userRecipe = recipesRepository.findByRecipeNameAndUser(recipesDTO.getRecipeName(), userService.userFromToken(token));
        if (userRecipe.isPresent()) {
            throw new IllegalArgumentException("Taki przepis już istnieje!");
        }

        Recipes recipes = recipesMapper.toEntity(recipesDTO);
        recipes.setUser(user);
        Recipes savedRecipes = recipesRepository.save(recipes);
        return recipesMapper.toDTO(savedRecipes);
    }

    public void deleteById(Long id, String token) {
        User user = userService.userFromToken(token);
        Optional<Recipes> optionalRecipe = recipesRepository.findByRecipeIdAndUser(id, user);
        if (optionalRecipe.isPresent()) {
            Recipes recipeToDelete = optionalRecipe.get();

            List<Ingredients> ingredients = ingredientsRepository.findAllByRecipe(recipeToDelete);
            ingredientsRepository.deleteAll(ingredients);

            recipesRepository.deleteById(id);
            return;
        }
       throw new NoSuchElementException("Nie ma takiego przepisu!");
    }

    public RecipesDTO update(RecipesDTO recipesDTO, String token) {
        if (recipesDTO.getRecipeId() == null) {
            throw new NoSuchElementException("Nie ma takiego przepisu!");
        }
        User user = userService.userFromToken(token);
        Recipes recipes = recipesMapper.toEntity(recipesDTO);
        recipes.setUser(user);
        Recipes savedRecipes = recipesRepository.save(recipes);
        return recipesMapper.toDTO(savedRecipes);
    }

}
