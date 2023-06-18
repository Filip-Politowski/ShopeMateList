package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.mapper.RecipesMapper;
import pl.shopmatelist.shopmatelist.repository.RecipesRepository;

@Service
public class RecipesService {

    private final RecipesRepository recipesRepository;
    private final RecipesMapper recipesMapper;

    @Autowired
    public RecipesService(RecipesRepository recipesRepository, RecipesMapper recipesMapper) {
        this.recipesRepository = recipesRepository;
        this.recipesMapper = recipesMapper;
    }

    public RecipesDTO createRecipes(RecipesDTO recipesDTO) {
        Recipes recipes = recipesMapper.toEntity(recipesDTO);
        Recipes savedRecipes = recipesRepository.save(recipes);
        return recipesMapper.toDTO(savedRecipes);
    }

    // Add other methods as needed
}
