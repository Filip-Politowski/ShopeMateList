package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.mapper.RecipesMapper;
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


    public RecipesDTO findById(Long recipeId, String token){
        User user = userService.userFromToken(token);
        Optional<Recipes> optionalRecipe = recipesRepository.findByRecipeIdAndUser(recipeId, user);
        if(optionalRecipe.isPresent()){
            Recipes recipe = optionalRecipe.get();
            return  recipesMapper.toDTO(recipe);
        }
        throw new NoSuchElementException("Nie ma takiego przepisu!");

    }
    public List<RecipesDTO> findAll(String token){
        User user = userService.userFromToken(token);
        List<Recipes> recipes = recipesRepository.findAllByUser(user);
        return recipesMapper.toDtoList(recipes);
    }

    public RecipesDTO createRecipes(RecipesDTO recipesDTO) {
        Recipes recipes = recipesMapper.toEntity(recipesDTO);
        Recipes savedRecipes = recipesRepository.save(recipes);
        return recipesMapper.toDTO(savedRecipes);
    }

    public void deleteById(Long id){
        recipesRepository.deleteById(id);
    }

    public RecipesDTO update(RecipesDTO recipesDTO){
        Recipes recipe = recipesMapper.toEntity(recipesDTO);
        Recipes updatedRecipe = recipesRepository.save(recipe);
        return recipesMapper.toDTO(updatedRecipe);
    }

}
