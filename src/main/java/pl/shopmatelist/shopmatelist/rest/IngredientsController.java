package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestIngredientsDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseIngredientsDTO;
import pl.shopmatelist.shopmatelist.services.IngredientsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;


    @GetMapping("/{id}")
    public ResponseIngredientsDTO findIngredientById(@PathVariable Long id) {

        return ingredientsService.findById(id);

    }

    @GetMapping("/recipe/{id}")
    public List<ResponseIngredientsDTO> findAllIngredients(@PathVariable Long id, @RequestParam(name = "sort", defaultValue = "false") boolean sort) {

        return ingredientsService.findAllByRecipeId(id, sort);

    }

    @PostMapping()
    public ResponseIngredientsDTO createIngredient(@RequestBody RequestIngredientsDTO requestIngredientsDTO) {

        return ingredientsService.save(requestIngredientsDTO);

    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable Long id) {

        ingredientsService.deleteById(id);
    }

    @PutMapping()
    public ResponseIngredientsDTO updateIngredient(@RequestBody RequestIngredientsDTO requestIngredientsDTO) {

        return ingredientsService.update(requestIngredientsDTO);
    }

}