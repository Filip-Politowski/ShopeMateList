package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;

import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ProductOnListNotFoundException;
import pl.shopmatelist.shopmatelist.services.ProductsOnListService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products-on-list")
public class ProductsOnListController {

    private final ProductsOnListService productsOnListService;

    @GetMapping("/{id}")
    public ProductsOnListDTO findProductsOnListById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return productsOnListService.findById(id, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        } catch (ProductOnListNotFoundException exc) {
            throw new ProductOnListNotFoundException(exc.getMessage());
        }
    }

    @GetMapping("/shopping-list/{id}")
    public List<ProductsOnListDTO> findAllProductsOnListByShoppingListId(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return productsOnListService.findAllByShoppingListId(id, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @PostMapping()
    public ProductsOnListDTO createProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO, @RequestHeader("Authorization") String token) {
        try {
            return productsOnListService.save(productsOnListDTO, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }

    @PostMapping("/recipe/{recipeId}/{shoppingListId}")
    public List<ProductsOnListDTO> addAllProductsFromRecipe(@PathVariable Long recipeId, @PathVariable Long shoppingListId, @RequestHeader("Authorization") String token) {
        return productsOnListService.addingAllProductsFromRecipe(recipeId, shoppingListId, token);
    }

    @PostMapping("/weekly-food-plan/{foodPlanId}/{shoppingListId}")
    public List<List<ProductsOnListDTO>> addAllProductsFromWeeklyFoodPlan(@PathVariable Long foodPlanId, @PathVariable Long shoppingListId, @RequestHeader("Authorization") String token) {
        return productsOnListService.addingProductsFromWeeklyPlan(foodPlanId, shoppingListId, token);
    }


    @DeleteMapping("/{id}")
    public void deleteProductsOnList(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            productsOnListService.deleteById(id, token);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        } catch (ProductOnListNotFoundException exc) {
            throw new ProductOnListNotFoundException(exc.getMessage());
        }
    }

    @PutMapping()
    public ProductsOnListDTO updateProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO, @RequestHeader("Authorization") String token) {
        try {
            return productsOnListService.update(productsOnListDTO, token);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }
}



