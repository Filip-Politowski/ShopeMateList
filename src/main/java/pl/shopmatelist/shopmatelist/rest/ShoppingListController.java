package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestShoppingListDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseShoppingListDTO;
import pl.shopmatelist.shopmatelist.services.ShoppingListService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shopping-list")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping("/{id}")
    public ResponseShoppingListDTO findShoppingListById(@PathVariable Long id) {

        return shoppingListService.findById(id);

    }

    @GetMapping()
    public List<ResponseShoppingListDTO> findAllShoppingLists(@RequestParam(name = "sort", defaultValue = "false") boolean sort) {
        return shoppingListService.findAll(sort);
    }


    @PostMapping()
    public ResponseShoppingListDTO createShoppingList(@RequestBody RequestShoppingListDTO requestShoppingListDTO) {
        return shoppingListService.save(requestShoppingListDTO);
    }

    @PostMapping("/share/{shoppingListId}/{userId}")
    public ResponseShoppingListDTO shareShoppingList(@PathVariable Long shoppingListId, @PathVariable Long userId) {

            return shoppingListService.shareShoppingList(shoppingListId, userId);

    }

    @DeleteMapping("/{id}")
    public void deleteShoppingList(@PathVariable Long id) {

        shoppingListService.deleteById(id);

    }

    @PutMapping()
    public ResponseShoppingListDTO updateShoppingList(@RequestBody RequestShoppingListDTO requestShoppingListDTO) {

            return shoppingListService.update(requestShoppingListDTO);

    }

}
