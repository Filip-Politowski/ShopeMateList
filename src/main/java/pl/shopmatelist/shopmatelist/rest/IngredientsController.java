package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.IngredientNofFoundException;
import pl.shopmatelist.shopmatelist.services.IngredientsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;


    @GetMapping("/{id}")
    public IngredientsDTO findIngredientById(@PathVariable Long id) {

            return ingredientsService.findById(id);

    }

    @GetMapping("/recipe/{id}")
    public List<IngredientsDTO> findAllIngredients(@PathVariable Long id) {

            return ingredientsService.findAllByRecipeId(id);

    }

    @PostMapping()
    public IngredientsDTO createIngredient(@RequestBody IngredientsDTO ingredientsDTO) {

            return ingredientsService.save(ingredientsDTO);

    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable Long id) {

            ingredientsService.deleteById(id);
    }

    @PutMapping()
    public IngredientsDTO updateIngredient(@RequestBody IngredientsDTO ingredientsDTO) {

            return ingredientsService.update(ingredientsDTO);
    }

}