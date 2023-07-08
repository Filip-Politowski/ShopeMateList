package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.RecipeNotFoundException;
import pl.shopmatelist.shopmatelist.services.RecipesService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesService recipesService;



    @GetMapping("/{id}")
    public RecipesDTO findRecipeById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return  recipesService.findById(id, token);
        }
        catch (RecipeNotFoundException exc) {
             throw new RecipeNotFoundException(exc.getMessage());
        }
    }

    @GetMapping()
    public List<RecipesDTO> findAllRecipes( @RequestHeader("Authorization") String token){
        return recipesService.findAll(token);
    }

    @PostMapping()
    public RecipesDTO createRecipes(@RequestBody RecipesDTO recipesDTO, @RequestHeader("Authorization") String token) {
       try {
           return  recipesService.save(recipesDTO, token);
       }catch (IllegalArgumentException exc) {
           throw new IllegalArgumentException(exc.getMessage());
       }


    }

    @DeleteMapping("/{id}")
    public void deleteRecipesById(@PathVariable Long id, @RequestHeader("Authorization") String token){
       try {
           recipesService.deleteById(id, token);
       }catch (RecipeNotFoundException exc) {
           throw new RecipeNotFoundException(exc.getMessage());
       }

    }

    @PutMapping()
    public RecipesDTO updateRecipes(@RequestBody RecipesDTO recipesDTO, @RequestHeader("Authorization") String token) {
        try {
            return recipesService.update(recipesDTO, token);
        }catch (RecipeNotFoundException exc){
            throw new RecipeNotFoundException(exc.getMessage());
        }

    }


}
