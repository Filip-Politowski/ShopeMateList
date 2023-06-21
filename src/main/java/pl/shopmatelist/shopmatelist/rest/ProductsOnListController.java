package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.services.ProductsOnListService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductsOnListController {

    private final ProductsOnListService productsOnListService;

    @GetMapping("/productsonlist/{id}")
    public ProductsOnListDTO findProductsOnListById(@PathVariable Long id){
        return productsOnListService.findById(id);
    }

    @GetMapping("/productsonlist")
    public List<ProductsOnListDTO> findAllProductsOnList(){
        return productsOnListService.findAll();
    }

    @PostMapping("/productsonlist")
    public ProductsOnListDTO createProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO){
        return productsOnListService.save(productsOnListDTO);
    }
    @PostMapping("/productsonlist/recipe/{recipeId}/{shoppingListId}")
    public List<ProductsOnListDTO> addAllProductsFromRecipe(@PathVariable Long recipeId, @PathVariable Long shoppingListId){
        return productsOnListService.addingAllProductsByRecipe(recipeId,shoppingListId);
    }

    @DeleteMapping("productsonlist/{id}")
    public void deleteProductsOnList(@PathVariable Long id){
        productsOnListService.deleteById(id);
    }

    @PutMapping("/productsonlist")
    public ProductsOnListDTO updateProductOnList(@RequestBody ProductsOnListDTO productsOnListDTO){
        return productsOnListService.update(productsOnListDTO);
    }


}
