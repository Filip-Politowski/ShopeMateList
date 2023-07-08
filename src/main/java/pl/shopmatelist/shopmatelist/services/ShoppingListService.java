package pl.shopmatelist.shopmatelist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.Recipes;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.RecipeNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.ShoppingListNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.UserNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;
import pl.shopmatelist.shopmatelist.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    private final ProductsOnListRepository productsOnListRepository;


    public ShoppingListDTO findById(Long id, String token) {
        User user = userService.userFromToken(token);
        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(id, user);

        if (shoppingList.isPresent()) {
            ShoppingList foundShoppingList = shoppingList.get();
            return shoppingListMapper.toDTO(foundShoppingList);
        }
        throw new ShoppingListNotFoundException("Nie ma listy zakupowej o id: " + id + " w bazie!");
    }


    public List<ShoppingListDTO> findAll(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("Nie ma takiego uŻytkownika w bazie"));
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
        throw new ShoppingListNotFoundException("Nie ma listy zakupowej o id: " + id + " w bazie");

    }


    public ShoppingListDTO update(ShoppingListDTO shoppingListDTO, String token) {

        if (shoppingListDTO.getShoppingListId() == null) {
            throw new IllegalArgumentException("Musisz podać ID listy zakupowej");
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
        Optional<User> userToShare = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Nie ma użytkownika o podanym ID")));
      if(userToShare.isPresent()) {
          User foundUser = userToShare.get();


          Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(shoppingListId, user);
          if (shoppingList.isPresent()) {
              ShoppingList foundShoppingList = shoppingList.get();
              ShoppingList sharedShoppingList = new ShoppingList();
              sharedShoppingList.setOwner(false);
              sharedShoppingList.setUser(foundUser);
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
      }
        throw new ShoppingListNotFoundException("Nie możesz udostępnić listy zakupowej która nie istnieje");
    }
}
