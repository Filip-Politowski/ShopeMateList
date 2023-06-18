package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.mapper.RecipesMapper;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RecipesService {

    private final RecipesRepository recipesRepository;
    private final RecipesMapper recipesMapper;

    @Autowired
    public RecipesService(RecipesRepository recipesRepository, RecipesMapper recipesMapper) {
        this.recipesRepository = recipesRepository;
        this.recipesMapper = recipesMapper;
    }
    public RecipesDTO findById(Long id){
        Optional<Recipes> optionalRecipe = recipesRepository.findById(id);
        if(optionalRecipe.isPresent()){
            Recipes recipe = optionalRecipe.get();
            return  recipesMapper.toDTO(recipe);
        }
        throw new NoSuchElementException();

    }
    public List<RecipesDTO> findAll(){
        List<Recipes> recipes = recipesRepository.findAll();
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
