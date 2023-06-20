package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.services.RecipesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping("/recipes/{id}")
    public RecipesDTO findRecipeById(@PathVariable Long id){
        return recipesService.findById(id);
    }

    @GetMapping("/recipes")
    public List<RecipesDTO> findAllRecipes(){
        return recipesService.findAll();
    }

    @PostMapping("/recipes")
    public RecipesDTO createRecipes(@RequestBody RecipesDTO recipesDTO) {
        return  recipesService.createRecipes(recipesDTO);
    }

    @DeleteMapping("/recipes/{id}")
    public void deleteRecipesById(@PathVariable Long id){
        recipesService.deleteById(id);
    }


}
