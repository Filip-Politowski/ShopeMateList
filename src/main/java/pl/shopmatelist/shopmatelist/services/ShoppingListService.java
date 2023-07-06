package pl.shopmatelist.shopmatelist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final UserService userService;


    private final ProductsOnListRepository productsOnListRepository;


    public ShoppingListDTO findById(Long id, String token) {
        User user = userService.userFromToken(token);
        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(id, user);

        if (shoppingList.isPresent()) {
            ShoppingList foundShoppingList = shoppingList.get();
            return shoppingListMapper.toDTO(foundShoppingList);
        }
        throw new NoSuchElementException("Nie ma takiej listy zakupowej");
    }


    public List<ShoppingListDTO> findAll(String token) {
        User user = userService.userFromToken(token);
        List<ShoppingList> shoppingLists = shoppingListRepository.findAllByUser(user);
        return shoppingListMapper.toDtoList(shoppingLists);
    }


    public ShoppingListDTO save(ShoppingListDTO shoppingListDTO, String token) {

        User user = userService.userFromToken(token);

        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        shoppingList.setUser(user);
        shoppingList.setOwner(true);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        return shoppingListMapper.toDTO(savedShoppingList);
    }

    public void deleteById(Long id, String token) {
        User user = userService.userFromToken(token);
        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(id, user);

        if (shoppingList.isPresent()) {
            ShoppingList shoppingListToDelete = shoppingList.get();
            List<ProductsOnList> productsOnList = productsOnListRepository.findAllByShoppingListId(id);
            productsOnListRepository.deleteAll(productsOnList);
            shoppingListRepository.delete(shoppingListToDelete);
            return;
        }
        throw new NoSuchElementException("Nie ma takiej listy zakupowej");

    }


    public ShoppingListDTO update(ShoppingListDTO shoppingListDTO, String token) {

        if (shoppingListDTO.getShoppingListId() == null){
            throw new IllegalArgumentException("Nie ma takiej listy zakupowej");
        }

        User user = userService.userFromToken(token);
        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        shoppingList.setUser(user);
        shoppingList.setOwner(!shoppingListRepository.findByShoppingListIdAndUser(shoppingListDTO.getShoppingListId(), user).get().getOwner().equals(false));
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        return shoppingListMapper.toDTO(savedShoppingList);
    }

    public ShoppingListDTO shareShoppingList(Long shoppingListId, String token, Long userId) {
        User user = userService.userFromToken(token);
        User userToShare = userService.findByUserId(userId);
        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(shoppingListId, user);
        if (shoppingList.isPresent()) {
            ShoppingList foundShoppingList = shoppingList.get();
            ShoppingList sharedShoppingList = new ShoppingList();
            sharedShoppingList.setOwner(false);
            sharedShoppingList.setUser(userToShare);
            sharedShoppingList.setShoppingDate(foundShoppingList.getShoppingDate());
            sharedShoppingList.setMarket(foundShoppingList.getMarket());

            ShoppingList savedShoppingList = shoppingListRepository.save(sharedShoppingList);


            List<ProductsOnList> productsOnList = productsOnListRepository.findAllByShoppingListId(shoppingListId);
            productsOnList.forEach(productOnList -> {
                ProductsOnList productsOnListToShare = new ProductsOnList();
                productsOnListToShare.setShoppingList(savedShoppingList);
                productsOnListToShare.setProduct(productOnList.getProduct());
                productsOnListToShare.setQuantity(productOnList.getQuantity());
                productsOnListRepository.save(productsOnListToShare);

            });


            return shoppingListMapper.toDTO(savedShoppingList);
        }
        throw new NoSuchElementException("Nie ma takiej listy zakupowej");
    }
}
