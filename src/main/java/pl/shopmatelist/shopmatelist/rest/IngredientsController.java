package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.services.IngredientsService;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    @Autowired
    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/{id}")
    public IngredientsDTO findIngredientById(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        return ingredientsService.findById(id, token);
    }

    @GetMapping("/recipe/{id}")
    public List<IngredientsDTO>findAllIngredients(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        return ingredientsService.findAllByRecipeId(id, token);
    }

    @PostMapping()
    public IngredientsDTO createIngredient(@RequestBody IngredientsDTO ingredientsDTO, @RequestHeader(value = "Authorization") String token) {
        return  ingredientsService.save(ingredientsDTO, token);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable Long id,  @RequestHeader(value = "Authorization") String token){
        ingredientsService.deleteById(id, token);
    }

    @PutMapping()
    public IngredientsDTO updateIngredient(@RequestBody IngredientsDTO ingredientsDTO,  @RequestHeader(value = "Authorization") String token){
        return ingredientsService.update(ingredientsDTO, token);
    }


}