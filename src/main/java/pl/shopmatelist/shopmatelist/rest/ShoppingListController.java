package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ShoppingListNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.UserNotFoundException;
import pl.shopmatelist.shopmatelist.services.ShoppingListService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shopping-list")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping("/{id}")
    public ShoppingListDTO findShoppingListById(@PathVariable Long id) {

        return shoppingListService.findById(id);

    }

    @GetMapping()
    public List<ShoppingListDTO> findAllShoppingLists() {
        return shoppingListService.findAll();
    }


    @PostMapping()
    public ShoppingListDTO createShoppingList(@RequestBody ShoppingListDTO shoppingListDTO) {
        return shoppingListService.save(shoppingListDTO);
    }

    @PostMapping("/share/{shoppingListId}/{userId}")
    public ShoppingListDTO shareShoppingList(@PathVariable Long shoppingListId, @PathVariable Long userId) {

            return shoppingListService.shareShoppingList(shoppingListId, userId);

    }

    @DeleteMapping("/{id}")
    public void deleteShoppingList(@PathVariable Long id) {

        shoppingListService.deleteById(id);

    }

    @PutMapping()
    public ShoppingListDTO updateShoppingList(@RequestBody ShoppingListDTO shoppingListDTO) {

            return shoppingListService.update(shoppingListDTO);

    }

}
