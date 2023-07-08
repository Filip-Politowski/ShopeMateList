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
    public ProductsOnListDTO findProductsOnListById(@PathVariable Long id) {

            return productsOnListService.findById(id);
    }

    @GetMapping("/shopping-list/{id}")
    public List<ProductsOnListDTO> findAllProductsOnListByShoppingListId(@PathVariable Long id) {

            return productsOnListService.findAllByShoppingListId(id);
    }

    @PostMapping()
    public ProductsOnListDTO createProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO) {

            return productsOnListService.save(productsOnListDTO);
    }

    @PostMapping("/recipe/{recipeId}/{shoppingListId}")
    public List<ProductsOnListDTO> addAllProductsFromRecipe(@PathVariable Long recipeId, @PathVariable Long shoppingListId) {
        return productsOnListService.addingAllProductsFromRecipe(recipeId, shoppingListId);
    }

    @PostMapping("/food-plan/{foodPlanId}/{shoppingListId}")
    public List<List<ProductsOnListDTO>> addAllProductsFromFoodPlan(@PathVariable Long foodPlanId, @PathVariable Long shoppingListId) {
        return productsOnListService.addingProductsFromWeeklyPlan(foodPlanId, shoppingListId);
    }


    @DeleteMapping("/{id}")
    public void deleteProductsOnList(@PathVariable Long id) {
        try {
            productsOnListService.deleteById(id);
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        } catch (ProductOnListNotFoundException exc) {
            throw new ProductOnListNotFoundException(exc.getMessage());
        }
    }

    @PutMapping()
    public ProductsOnListDTO updateProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO) {
        try {
            return productsOnListService.update(productsOnListDTO);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }
}



