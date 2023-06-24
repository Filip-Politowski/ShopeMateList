package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;


    public ShoppingListDTO findById(Long id) {
        Optional<ShoppingList> optionalShoppingList = shoppingListRepository.findById(id);
        if (optionalShoppingList.isPresent()) {
            ShoppingList shoppingList = optionalShoppingList.get();
            return shoppingListMapper.toDTO(shoppingList);
        }
        throw new NoSuchElementException();
    }

    public List<ShoppingListDTO> findAll() {
        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        return shoppingListMapper.toDtoList(shoppingLists);
    }

    public ShoppingListDTO save(ShoppingListDTO shoppingListDTO) {
        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
        return shoppingListMapper.toDTO(savedShoppingList);
    }

    public void deleteById(Long id) {
        shoppingListRepository.deleteById(id);
    }

    public ShoppingListDTO update(ShoppingListDTO shoppingListDTO) {
        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        ShoppingList updatedShoppingList = shoppingListRepository.save(shoppingList);
        return shoppingListMapper.toDTO(updatedShoppingList);
    }

}
