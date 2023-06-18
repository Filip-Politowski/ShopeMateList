package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.mapper.IngredientsMapper;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;

@Service
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;
    private final IngredientsMapper ingredientsMapper;

    @Autowired
    public IngredientsService(IngredientsRepository ingredientsRepository, IngredientsMapper ingredientsMapper) {
        this.ingredientsRepository = ingredientsRepository;
        this.ingredientsMapper = ingredientsMapper;
    }

    public IngredientsDTO createIngredients(IngredientsDTO ingredientsDTO) {
        Ingredients ingredients = ingredientsMapper.toEntity(ingredientsDTO);
        Ingredients savedIngredients = ingredientsRepository.save(ingredients);
        return ingredientsMapper.toDTO(savedIngredients);
    }


}
