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
    public IngredientsDTO findIngredientById(@PathVariable Long id){
        return ingredientsService.findById(id);
    }

    @GetMapping()
    public List<IngredientsDTO>findAllIngredients(){
        return ingredientsService.findAll();
    }

    @PostMapping()
    public IngredientsDTO createIngredient(@RequestBody IngredientsDTO ingredientsDTO) {
        return  ingredientsService.save(ingredientsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable Long id){
        ingredientsService.deleteById(id);
    }

    @PutMapping()
    public IngredientsDTO updateIngredient(@RequestBody IngredientsDTO ingredientsDTO){
        return ingredientsService.update(ingredientsDTO);
    }


}