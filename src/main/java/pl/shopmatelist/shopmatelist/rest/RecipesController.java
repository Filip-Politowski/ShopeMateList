package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.services.RecipesService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesService recipesService;



    @GetMapping("/{id}")
    public ResponseEntity<RecipesDTO> findRecipeById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            RecipesDTO recipe = recipesService.findById(id, token);
            return ResponseEntity.ok(recipe);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping()
    public List<RecipesDTO> findAllRecipes( @RequestHeader("Authorization") String token){
        return recipesService.findAll(token);
    }

    @PostMapping()
    public RecipesDTO createRecipes(@RequestBody RecipesDTO recipesDTO, @RequestHeader("Authorization") String token) {
        return  recipesService.save(recipesDTO, token);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipesById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        recipesService.deleteById(id, token);
    }

    @PutMapping()
    public RecipesDTO updateRecipes(@RequestBody RecipesDTO recipesDTO, @RequestHeader("Authorization") String token) {
        return recipesService.update(recipesDTO, token);
    }


}
