package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.services.ShoppingListService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping("/shoppinglist/{id}")
    public ShoppingListDTO findShoppingListById(@PathVariable Long id){
        return shoppingListService.findById(id);
    }
    @GetMapping("/shoppinglist")
    public List<ShoppingListDTO> findAllShoppingLists(){
        return shoppingListService.findAll();
    }

    @PostMapping("/shoppinglist")
    public ShoppingListDTO createShoppingList(@RequestBody ShoppingListDTO shoppingListDTO, @RequestHeader("Authorization")String token){
        return shoppingListService.save(shoppingListDTO, token);
    }

    @DeleteMapping("/shoppinglist/{id}")
    public void deleteShoppingList(@PathVariable Long id){
        shoppingListService.deleteById(id);
    }

    @PutMapping("/shoppinglist")
    public ShoppingListDTO updateShoppingList(@RequestBody ShoppingListDTO shoppingListDTO){
        return shoppingListService.update(shoppingListDTO);
    }

}
