package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.services.IngredientsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    @Autowired
    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/ingredients/{id}")
    public IngredientsDTO findIngredientById(@PathVariable Long id){
        return ingredientsService.findById(id);
    }

    @GetMapping("/ingredients")
    public List<IngredientsDTO>findAllIngredients(){
        return ingredientsService.findAll();
    }

    @PostMapping("/ingredients")
    public IngredientsDTO createIngredient(@RequestBody IngredientsDTO ingredientsDTO) {
        return  ingredientsService.save(ingredientsDTO);
    }

    @DeleteMapping("/ingredients/{id}")
    public void deleteIngredientById(@PathVariable Long id){
        ingredientsService.deleteById(id);
    }

    @PutMapping("/ingredients")
    public IngredientsDTO updateIngredient(@RequestBody IngredientsDTO ingredientsDTO){
        return ingredientsService.update(ingredientsDTO);
    }


}