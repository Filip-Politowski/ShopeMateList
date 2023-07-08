package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestRecipesDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseRecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.RecipeNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.RecipesMapper;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;
import pl.shopmatelist.shopmatelist.services.security.AuthenticationService;


import java.util.List;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipesService {

    private final RecipesRepository recipesRepository;
    private final RecipesMapper recipesMapper;
    private final IngredientsRepository ingredientsRepository;

    private final AuthenticationService authenticationService;


    public ResponseRecipesDTO findById(Long recipeId) {

        Optional<Recipes> optionalRecipe = recipesRepository.findByRecipeIdAndUser(recipeId, authenticationService.authenticatedUser());

        if (optionalRecipe.isPresent()) {
            Recipes recipe = optionalRecipe.get();
            return recipesMapper.toDTO(recipe);
        }
        throw new RecipeNotFoundException("Nie ma przepisu o id: " + recipeId + " w bazie!");

    }

    public List<ResponseRecipesDTO> findAll() {

        List<Recipes> recipes = recipesRepository.findAllByUser(authenticationService.authenticatedUser());
        return recipesMapper.toDtoList(recipes);

    }

    public ResponseRecipesDTO save(RequestRecipesDTO requestRecipesDTO) {

        Optional<Recipes> userRecipe = recipesRepository.findByRecipeNameAndUser(requestRecipesDTO.getRecipeName(),authenticationService.authenticatedUser());
        if (userRecipe.isPresent()) {
            throw new IllegalArgumentException("Przepis o nazwie " + requestRecipesDTO.getRecipeName() + " już istnieje w bazie!");
        }

        Recipes recipes = recipesMapper.toEntity(requestRecipesDTO);
        recipes.setUser(authenticationService.authenticatedUser());
        Recipes savedRecipes = recipesRepository.save(recipes);
        return recipesMapper.toDTO(savedRecipes);
    }

    public void deleteById(Long id) {

        Optional<Recipes> optionalRecipe = recipesRepository.findByRecipeIdAndUser(id, authenticationService.authenticatedUser());
        if (optionalRecipe.isPresent()) {
            Recipes recipeToDelete = optionalRecipe.get();

            List<Ingredients> ingredients = ingredientsRepository.findAllByRecipe(recipeToDelete);
            ingredientsRepository.deleteAll(ingredients);

            recipesRepository.deleteById(id);
            return;
        }
       throw new RecipeNotFoundException("Nie ma przepisu o id: " + id + " w bazie!");
    }

    public ResponseRecipesDTO update(RequestRecipesDTO requestRecipesDTO) {

        if (requestRecipesDTO.getRecipeId() == null) {
            throw new RecipeNotFoundException("Nie ma przepisu o id: " + requestRecipesDTO.getRecipeId() + " w bazie!");
        }

        Recipes recipes = recipesMapper.toEntity(requestRecipesDTO);
        recipes.setUser(authenticationService.authenticatedUser());
        Recipes savedRecipes = recipesRepository.save(recipes);
        return recipesMapper.toDTO(savedRecipes);
    }



}
