package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shopmatelist.shopmatelist.dto.RecipesDTO;
import pl.shopmatelist.shopmatelist.services.RecipesService;

@RestController
@RequestMapping("/api")
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @PostMapping("/recipes")
    public ResponseEntity<RecipesDTO> createRecipes(@RequestBody RecipesDTO recipesDTO) {
        RecipesDTO createdRecipes = recipesService.createRecipes(recipesDTO);
        return new ResponseEntity<>(createdRecipes, HttpStatus.CREATED);
    }

    // Add other endpoints as needed
}
