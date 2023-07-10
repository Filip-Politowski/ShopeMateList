package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseProductsOnListDTO;

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
    public ResponseProductsOnListDTO findProductsOnListById(@PathVariable Long id) {

            return productsOnListService.findById(id);
    }

    @GetMapping("/shopping-list/{id}")
    public List<ResponseProductsOnListDTO> findAllProductsOnListByShoppingListId(@PathVariable Long id, @RequestParam(name = "sort", defaultValue = "false") boolean sort) {

            return productsOnListService.findAllByShoppingListId(id, sort);
    }

    @PostMapping()
    public ResponseProductsOnListDTO createProductOnList(@RequestBody RequestProductsOnListDTO requestProductsOnListDTO) {

            return productsOnListService.save(requestProductsOnListDTO);
    }

    @PostMapping("/recipe/{recipeId}/{shoppingListId}")
    public List<ResponseProductsOnListDTO> addAllProductsFromRecipe(@PathVariable Long recipeId, @PathVariable Long shoppingListId) {
        return productsOnListService.addingAllProductsFromRecipe(recipeId, shoppingListId);
    }

    @PostMapping("/food-plan/{foodPlanId}/{shoppingListId}")
    public List<List<ResponseProductsOnListDTO>> addAllProductsFromFoodPlan(@PathVariable Long foodPlanId, @PathVariable Long shoppingListId) {
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
    public ResponseProductsOnListDTO updateProductOnList(@RequestBody RequestProductsOnListDTO requestProductsOnListDTO) {
        try {
            return productsOnListService.update(requestProductsOnListDTO);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        } catch (AuthorizationException exc) {
            throw new AuthorizationException(exc.getMessage());
        }
    }
}



