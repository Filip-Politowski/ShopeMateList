package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.services.RecipesService;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping("/{id}")
    public RecipesDTO findRecipeById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return recipesService.findById(id, token);
    }

    @GetMapping()
    public List<RecipesDTO> findAllRecipes( @RequestHeader("Authorization") String token){
        return recipesService.findAll(token);
    }

    @PostMapping()
    public RecipesDTO createRecipes(@RequestBody RecipesDTO recipesDTO) {
        return  recipesService.createRecipes(recipesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipesById(@PathVariable Long id){
        recipesService.deleteById(id);
    }


}
