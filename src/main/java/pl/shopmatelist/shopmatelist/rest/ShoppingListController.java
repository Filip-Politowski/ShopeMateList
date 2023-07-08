package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
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
    public ShoppingListDTO findShoppingListById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            return shoppingListService.findById(id, token);
        } catch (ShoppingListNotFoundException exc) {
            throw new ShoppingListNotFoundException(exc.getMessage());
        }
    }

    @GetMapping()
    public List<ShoppingListDTO> findAllShoppingLists(@RequestHeader("Authorization") String token) {
        return shoppingListService.findAll(token);
    }


    @PostMapping()
    public ShoppingListDTO createShoppingList(@RequestBody ShoppingListDTO shoppingListDTO, @RequestHeader("Authorization") String token) {
        return shoppingListService.save(shoppingListDTO, token);
    }

    @PostMapping("/share/{shoppingListId}/{userId}")
    public ShoppingListDTO shareShoppingList(@PathVariable Long shoppingListId, @PathVariable Long userId, @RequestHeader("Authorization") String token) {
        try {
            return shoppingListService.shareShoppingList(shoppingListId, token, userId);

        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException(exc.getMessage());
        } catch (ShoppingListNotFoundException exc) {
            throw new ShoppingListNotFoundException(exc.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteShoppingList(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            shoppingListService.deleteById(id, token);
        } catch (ShoppingListNotFoundException exc) {
            throw new ShoppingListNotFoundException(exc.getMessage());
        }
    }

    @PutMapping()
    public ShoppingListDTO updateShoppingList(@RequestBody ShoppingListDTO shoppingListDTO, @RequestHeader("Authorization") String token) {
        try {
            return shoppingListService.update(shoppingListDTO, token);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        }

    }

}
