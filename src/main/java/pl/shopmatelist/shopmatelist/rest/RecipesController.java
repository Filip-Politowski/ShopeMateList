package pl.shopmatelist.shopmatelist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestRecipesDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseRecipesDTO;
import pl.shopmatelist.shopmatelist.services.RecipesService;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesService recipesService;



    @GetMapping("/{id}")
    public ResponseRecipesDTO findRecipeById(@PathVariable Long id) {

            return  recipesService.findById(id);

    }

    @GetMapping()
    public List<ResponseRecipesDTO> findAllRecipes(){
        return recipesService.findAll();
    }

    @PostMapping()
    public ResponseRecipesDTO createRecipes(@RequestBody RequestRecipesDTO requestRecipesDTO) {

           return  recipesService.save(requestRecipesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipesById(@PathVariable Long id){

           recipesService.deleteById(id);

    }

    @PutMapping()
    public ResponseRecipesDTO updateRecipes(@RequestBody RequestRecipesDTO requestRecipesDTO) {

            return recipesService.update(requestRecipesDTO);

    }


}
