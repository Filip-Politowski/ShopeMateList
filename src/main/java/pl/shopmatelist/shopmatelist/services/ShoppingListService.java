package pl.shopmatelist.shopmatelist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestShoppingListDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.entity.User;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ShoppingListNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.UserNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;
import pl.shopmatelist.shopmatelist.repository.UserRepository;
import pl.shopmatelist.shopmatelist.services.security.AuthenticationService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final UserRepository userRepository;
    private final ProductsOnListRepository productsOnListRepository;
    private final AuthenticationService authenticationService;


    public ResponseShoppingListDTO findById(Long id) {

        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(id, authenticationService.authenticatedUser());

        if (shoppingList.isPresent()) {
            ShoppingList foundShoppingList = shoppingList.get();
            return shoppingListMapper.toDTO(foundShoppingList);
        }
        throw new ShoppingListNotFoundException("Nie ma listy zakupowej o id: " + id + " w bazie!");
    }


    public List<ResponseShoppingListDTO> findAll() {

        List<ShoppingList> shoppingLists = shoppingListRepository.findAllByUser(authenticationService.authenticatedUser());
        return shoppingListMapper.toDtoList(shoppingLists);
    }


    public ResponseShoppingListDTO save(RequestShoppingListDTO requestShoppingListDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        if (user.isPresent()) {
            User authenticatedUser = user.get();
            ShoppingList shoppingList = shoppingListMapper.toEntity(requestShoppingListDTO);
            shoppingList.setUser(authenticatedUser);
            shoppingList.setOwner(true);
            ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

            return shoppingListMapper.toDTO(savedShoppingList);
        }
        throw new UserNotFoundException("Nie ma takiego użytkownika w bazie");
    }

    public void deleteById(Long id) {

        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(id, authenticationService.authenticatedUser());

        if (shoppingList.isPresent()) {
            ShoppingList shoppingListToDelete = shoppingList.get();
            List<ProductsOnList> productsOnList = productsOnListRepository.findAllByShoppingListId(id);
            productsOnListRepository.deleteAll(productsOnList);
            shoppingListRepository.delete(shoppingListToDelete);
            return;
        }
        throw new ShoppingListNotFoundException("Nie ma listy zakupowej o id: " + id + " w bazie");

    }


    public ResponseShoppingListDTO update(RequestShoppingListDTO requestShoppingListDTO) {

        if (requestShoppingListDTO.getShoppingListId() == null) {
            throw new IllegalArgumentException("Musisz podać ID listy zakupowej");
        }

        ShoppingList shoppingList = shoppingListMapper.toEntity(requestShoppingListDTO);
        shoppingList.setUser(authenticationService.authenticatedUser());
        shoppingList.setOwner(!shoppingListRepository.findByShoppingListIdAndUser(requestShoppingListDTO.getShoppingListId(), authenticationService.authenticatedUser()).get().getOwner().equals(false));
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        return shoppingListMapper.toDTO(savedShoppingList);
    }

    public ResponseShoppingListDTO shareShoppingList(Long shoppingListId, Long userId) {

        User userToShare = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Nie ma użytkownika o podanym ID"));

        Optional<ShoppingList> shoppingList = shoppingListRepository.findByShoppingListIdAndUser(shoppingListId, authenticationService.authenticatedUser());
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

        throw new ShoppingListNotFoundException("Nie możesz udostępnić listy zakupowej która nie istnieje");
    }


}
