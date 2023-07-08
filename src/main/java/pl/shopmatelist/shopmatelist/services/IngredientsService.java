package pl.shopmatelist.shopmatelist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestIngredientsDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseIngredientsDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.IngredientNofFoundException;
import pl.shopmatelist.shopmatelist.mapper.IngredientsMapper;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;
import pl.shopmatelist.shopmatelist.services.security.AuthenticationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;
    private final IngredientsMapper ingredientsMapper;
    private final RecipesRepository recipesRepository;
    private final AuthenticationService authenticationService;



    public ResponseIngredientsDTO findById(Long ingredientId) {


        Optional<Ingredients> optionalIngredient = ingredientsRepository.findById(ingredientId);

        if (optionalIngredient.isPresent()) {
            Ingredients ingredient = optionalIngredient.get();

            if (userAuthorization(ingredient, authenticationService.authenticatedUser())) {
                return ingredientsMapper.toDTO(ingredient);
            } else {
                throw new AuthorizationException("Nie masz dostępu do tego składnika");
            }
        }
        throw new IngredientNofFoundException("Nie ma takiego składnika");

    }

    public List<ResponseIngredientsDTO> findAllByRecipeId(Long recipeId) {

        List<Recipes> userRecipes = recipesRepository.findAllByUser(authenticationService.authenticatedUser());
        boolean hasMatchingRecipe = userRecipes.stream()
                .anyMatch(recipe -> recipe.getRecipeId().equals(recipeId));
        if (hasMatchingRecipe) {
            List<Ingredients> ingredients = ingredientsRepository.findAllByRecipe_RecipeId(recipeId);
            if (ingredients.isEmpty()) {
                throw new IngredientNofFoundException("Nie ma żadnych składników");
            }
            return ingredientsMapper.toDtoList(ingredients);
        }
        throw new AuthorizationException("Nie masz dostępu do tych składników");
    }

    public ResponseIngredientsDTO save(RequestIngredientsDTO requestIngredientsDTO) {

        List<Ingredients> ingredients = ingredientsRepository.findAllByRecipe_RecipeId(requestIngredientsDTO.getRecipeId());
        if (ingredients.stream().anyMatch(userIngredients -> userIngredients.getProduct().getProductId().equals(requestIngredientsDTO.getProductId()))) {
            throw new IllegalArgumentException("Dany składnik znajduje się już na liście, nie możesz dodać go ponownie");
        }

        Ingredients ingredient = ingredientsMapper.toEntity(requestIngredientsDTO);

        if (userAuthorization(ingredient, authenticationService.authenticatedUser())) {
            Ingredients savedIngredients = ingredientsRepository.save(ingredient);
            return ingredientsMapper.toDTO(savedIngredients);
        }
        throw new AuthorizationException("Nie masz dostępu do tego składnika");

    }

    public void deleteById(Long id) {

        Optional<Ingredients> optionalIngredient = ingredientsRepository.findById(id);

        if (optionalIngredient.isPresent()) {
            Ingredients ingredient = optionalIngredient.get();

            if (userAuthorization(ingredient, authenticationService.authenticatedUser())) {
                ingredientsRepository.deleteById(id);
                return;
            }
            throw new AuthorizationException("Nie masz dostępu do tego składnika");
        }
        throw new IngredientNofFoundException("Nie ma składnika o id: " + id);
    }

    public ResponseIngredientsDTO update(RequestIngredientsDTO requestIngredientsDTO) {

        if (requestIngredientsDTO.getIngredientId() == null) {
            throw new java.lang.IllegalArgumentException("Należy podać id składnika");
        }

        Ingredients ingredients = ingredientsMapper.toEntity(requestIngredientsDTO);

        if (userAuthorization(ingredients, authenticationService.authenticatedUser())) {
            Optional<Ingredients> foundIngredient = ingredientsRepository.findById(ingredients.getIngredientId());
            if (foundIngredient.isPresent()) {
                Ingredients ingredientToSet = foundIngredient.get();
                ingredientToSet.setQuantity(requestIngredientsDTO.getQuantity());
                Ingredients savedIngredients = ingredientsRepository.save(ingredientToSet);
                return ingredientsMapper.toDTO(savedIngredients);
            }

        }
        throw new AuthorizationException("Nie masz dostępu do tego składnika");

    }

    public boolean userAuthorization(Ingredients ingredient, User user) {

        List<Recipes> userRecipes = recipesRepository.findAllByUser(user);
        return userRecipes.stream()
                .anyMatch(recipe -> recipe.getRecipeId().equals(ingredient.getRecipe().getRecipeId()));
    }



}
