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
    public RecipesDTO findRecipeById(@PathVariable Long id) {

            return  recipesService.findById(id);

    }

    @GetMapping()
    public List<RecipesDTO> findAllRecipes(){
        return recipesService.findAll();
    }

    @PostMapping()
    public RecipesDTO createRecipes(@RequestBody RecipesDTO recipesDTO) {

           return  recipesService.save(recipesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipesById(@PathVariable Long id){

           recipesService.deleteById(id);

    }

    @PutMapping()
    public RecipesDTO updateRecipes(@RequestBody RecipesDTO recipesDTO) {

            return recipesService.update(recipesDTO);

    }


}
