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
    public IngredientsDTO findIngredientById(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        try {
            return ingredientsService.findById(id, token);
        } catch (IngredientNofFoundException exc) {
            throw new IngredientNofFoundException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @GetMapping("/recipe/{id}")
    public List<IngredientsDTO> findAllIngredients(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        try {
            return ingredientsService.findAllByRecipeId(id, token);
        } catch (IngredientNofFoundException exc) {
            throw new IngredientNofFoundException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @PostMapping()
    public IngredientsDTO createIngredient(@RequestBody IngredientsDTO ingredientsDTO, @RequestHeader(value = "Authorization") String token) {
        try {
            return ingredientsService.save(ingredientsDTO, token);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        try {
            ingredientsService.deleteById(id, token);
        } catch (IngredientNofFoundException exc) {
            throw new IngredientNofFoundException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @PutMapping()
    public IngredientsDTO updateIngredient(@RequestBody IngredientsDTO ingredientsDTO, @RequestHeader(value = "Authorization") String token) {
        try {
            return ingredientsService.update(ingredientsDTO, token);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

}