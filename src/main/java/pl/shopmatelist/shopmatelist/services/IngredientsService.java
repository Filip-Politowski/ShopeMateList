package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.mapper.IngredientsMapper;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;
    private final IngredientsMapper ingredientsMapper;

    @Autowired
    public IngredientsService(IngredientsRepository ingredientsRepository, IngredientsMapper ingredientsMapper) {
        this.ingredientsRepository = ingredientsRepository;
        this.ingredientsMapper = ingredientsMapper;
    }

    public IngredientsDTO findById(Long id) {
        Optional<Ingredients> optionalIngredient = ingredientsRepository.findById(id);
        if (optionalIngredient.isPresent()) {
            Ingredients ingredient = optionalIngredient.get();
            return ingredientsMapper.toDTO(ingredient);
        }
        throw new NoSuchElementException();

    }

    public List<IngredientsDTO> findAll() {
        List<Ingredients> ingredients = ingredientsRepository.findAll();
        return ingredientsMapper.toDtoList(ingredients);
    }

    public IngredientsDTO save(IngredientsDTO ingredientsDTO) {
        Ingredients ingredients = ingredientsMapper.toEntity(ingredientsDTO);
        Ingredients savedIngredients = ingredientsRepository.save(ingredients);
        return ingredientsMapper.toDTO(savedIngredients);
    }

    public void deleteById(Long id) {
        ingredientsRepository.deleteById(id);
    }

    public IngredientsDTO update(IngredientsDTO ingredientsDTO) {
        Ingredients ingredient = ingredientsMapper.toEntity(ingredientsDTO);
        Ingredients updatedIngredient = ingredientsRepository.save(ingredient);
        return ingredientsMapper.toDTO(updatedIngredient);
    }

    public List<Products> getProductsByRecipeId(Long recipeId) {
        return ingredientsRepository.findProductsByRecipeId(recipeId);
    }

    public List<Ingredients> getIngredientsByRecipeId(Long recipeId) {
        return ingredientsRepository.findIngredientsByRecipeId(recipeId);
    }


}
