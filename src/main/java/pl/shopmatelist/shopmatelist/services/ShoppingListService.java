package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;
import pl.shopmatelist.shopmatelist.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final UserService userService;
    private final UserRepository userRepository;



    public ShoppingListDTO findById(Long id, String token) {
        User user = userService.userFromToken(token);
        ShoppingList shoppingList = shoppingListRepository.findByShoppingListIdAndUsers(id, user);
        return shoppingListMapper.toDTO(shoppingList);
    }


    public List<ShoppingListDTO> findAll(String token) {
        User user = userService.userFromToken(token);
        List<ShoppingList> shoppingLists = shoppingListRepository.findAllByUsers(user);
        return shoppingListMapper.toDtoList(shoppingLists);
    }




    public ShoppingListDTO save(ShoppingListDTO shoppingListDTO, String token) {

        User user = userService.userFromToken(token);

        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        user.addShoppingList(savedShoppingList);
        userRepository.save(user);

        return shoppingListMapper.toDTO(savedShoppingList);
    }

    public void deleteById(Long id, String token) {
        User user = userService.userFromToken(token);
        ShoppingList shoppingList = shoppingListRepository.findByShoppingListIdAndUsers(id, user);
        user.getShoppingLists().remove(shoppingList);
        userRepository.save(user);
        shoppingListRepository.delete(shoppingList);
    }


    public ShoppingListDTO update(ShoppingListDTO shoppingListDTO, String token) {
        User user = userService.userFromToken(token);

        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        user.addShoppingList(savedShoppingList);
        userRepository.save(user);

        return shoppingListMapper.toDTO(savedShoppingList);
    }


}
