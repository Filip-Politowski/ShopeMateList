package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;

import pl.shopmatelist.shopmatelist.services.ProductsOnListService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products-on-list")
public class ProductsOnListController {

    private final ProductsOnListService productsOnListService;

    @GetMapping("/{id}")
    public ProductsOnListDTO findProductsOnListById(@PathVariable Long id){
        return productsOnListService.findById(id);
    }

    @GetMapping("/all/shopping-list/{id}")
    public List<ProductsOnListDTO> findAllProductsOnListByShoppingListId(@PathVariable Long id){
        return productsOnListService.findAllByShoppingListId(id);
    }

    @PostMapping()
    public ProductsOnListDTO createProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO, @RequestHeader("Authorization")String authorizationHeader){
        return productsOnListService.save(productsOnListDTO,authorizationHeader);
    }
    @PostMapping("/recipe/{recipeId}/{shoppingListId}")
    public List<ProductsOnListDTO> addAllProductsFromRecipe(@PathVariable Long recipeId, @PathVariable Long shoppingListId, @RequestHeader("Authorization")String token){
        return productsOnListService.addingAllProductsFromRecipe(recipeId,shoppingListId, token);
    }

    @PostMapping("/weekly-food-plan/{foodPlanId}/{shoppingListId}")
    public List<List<ProductsOnListDTO>> addAllProductsFromWeeklyFoodPlan(@PathVariable Long foodPlanId, @PathVariable Long shoppingListId, @RequestHeader("Authorization")String token){
        return productsOnListService.addingProductsFromWeeklyPlan(foodPlanId,shoppingListId, token);
    }


    @DeleteMapping("/{id}")
    public void deleteProductsOnList(@PathVariable Long id){
        productsOnListService.deleteById(id);
    }

    @PutMapping()
    public ProductsOnListDTO updateProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO){
        return productsOnListService.update(productsOnListDTO);
    }


}
