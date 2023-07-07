package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import pl.shopmatelist.shopmatelist.dto.IngredientsDTO;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;
import pl.shopmatelist.shopmatelist.entity.*;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ProductOnListNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.IngredientsMapper;
import pl.shopmatelist.shopmatelist.mapper.ProductsOnListMapper;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.mapper.WeeklyFoodPlanMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductsOnListService {

    private final ProductsOnListRepository productsOnListRepository;
    private final ProductsOnListMapper productsOnListMapper;
    private final IngredientsService ingredientsService;
    private final IngredientsMapper ingredientsMapper;
    private final ShoppingListService shoppingListService;
    private final ShoppingListMapper shoppingListMapper;
    private final WeeklyFoodPlanService weeklyFoodPlanService;
    private final WeeklyFoodPlanMapper weeklyFoodPlanMapper;
    private final UserService userService;
    private final ShoppingListRepository shoppingListRepository;


    public ProductsOnListDTO findById(Long id, String token) {

        Optional<ProductsOnList> optionalProductsOnList = productsOnListRepository.findById(id);

        if (optionalProductsOnList.isPresent()) {
            ProductsOnList foundProductOnList = optionalProductsOnList.get();
            if (userAuthorization(token, foundProductOnList)) {

                return productsOnListMapper.toDto(foundProductOnList);
            } else {
                throw new AuthorizationException("Brak uprawnień do odczytania tego produktu");
            }
        }
        throw new ProductOnListNotFoundException("Nie ma takiego produktu na liście zakupowej");
    }

    public List<ProductsOnListDTO> findAllByShoppingListId(Long shoppingListId, String token) {
        User user = userService.userFromToken(token);
        List<ShoppingList> userShoppingLists = shoppingListRepository.findAllByUser(user);

        boolean hasMatchingShoppingList = userShoppingLists.stream()
                .anyMatch(shoppingList -> shoppingList.getShoppingListId().equals(shoppingListId));

        if (hasMatchingShoppingList) {
            List<ProductsOnList> allFoundProductsOnList = productsOnListRepository.findAllByShoppingListId(shoppingListId);
            return productsOnListMapper.toDtoList(allFoundProductsOnList);
        }
        throw new AuthorizationException("Brak uprawnień do odczytania tych produktów");

    }

    public ProductsOnListDTO save(ProductsOnListDTO productsOnListDTO, String token) {

        ProductsOnList product = productsOnListMapper.toEntity(productsOnListDTO);
        Optional<ProductsOnList> existingProduct = productsOnListRepository.findByProductIdAndShoppingListId(product.getProduct().getProductId(), product.getShoppingList().getShoppingListId());

        User user = userService.userFromToken(token);
        List<ShoppingList> userShoppingList = shoppingListRepository.findAllByUser(user);

        boolean hasMatchingShoppingList = userShoppingList.stream()
                .anyMatch(shoppingList -> shoppingList.getShoppingListId().equals(product.getShoppingList().getShoppingListId()));

        if (hasMatchingShoppingList) {
            if (existingProduct.isPresent()) {

                ProductsOnList foundProduct = existingProduct.get();

                foundProduct.setQuantity(foundProduct.getQuantity() + product.getQuantity());
                ProductsOnList savedUpdatedProductOnList = productsOnListRepository.save(foundProduct);
                return productsOnListMapper.toDto(savedUpdatedProductOnList);

            } else {
                ProductsOnList savedProductOnList = productsOnListRepository.save(product);
                return productsOnListMapper.toDto(savedProductOnList);

            }
        } else {
            throw new AuthorizationException("Brak uprawnień do zapisania tego produktu");
        }


    }

    public void deleteById(Long id, String token) {

        Optional<ProductsOnList> optionalProductsOnList = productsOnListRepository.findById(id);

        if (optionalProductsOnList.isPresent()) {
            ProductsOnList productToDelete = optionalProductsOnList.get();
            if (userAuthorization(token, productToDelete)) {
                productsOnListRepository.delete(productToDelete);
            } else {
                throw new AuthorizationException("Brak uprawnień do usunięcia tego produktu");
            }
        } else {
            throw new ProductOnListNotFoundException("Nie ma produktu o id: " + id + " na liście zakupowej");
        }
    }


    public ProductsOnListDTO update(ProductsOnListDTO productsOnListDTO, String token) {

        if (productsOnListDTO.getListItemId() == null) {
            throw new IllegalArgumentException("Należy podać id produktu na liście");
        }

        ProductsOnList product = productsOnListMapper.toEntity(productsOnListDTO);


        if (userAuthorization(token, product)) {
            Optional<ProductsOnList> foundProductOnList = productsOnListRepository.findById(product.getListItemId());
            if (foundProductOnList.isPresent()) {
                ProductsOnList productToSet = foundProductOnList.get();
                productToSet.setQuantity(productsOnListDTO.getQuantity());
                ProductsOnList savedProductOnList = productsOnListRepository.save(productToSet);
                return productsOnListMapper.toDto(savedProductOnList);

            }
        }
        throw new AuthorizationException("Nie masz dostępu do tego produktu na liście");
    }


    public List<ProductsOnListDTO> addingAllProductsFromRecipe(Long recipeId, Long shoppingListId, String token) {

        List<IngredientsDTO> ingredientsDTO = ingredientsService.findAllByRecipeId(recipeId, token);


        List<Ingredients> ingredients = ingredientsDTO.stream()
                .map(ingredientsMapper::toEntity).toList();
        ShoppingListDTO shoppingListDto = shoppingListService.findById(shoppingListId, token);
        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDto);


        List<ProductsOnList> productsOnLists = ingredients.stream()
                .map(ingredient -> {
                    ProductsOnList productsOnList = new ProductsOnList();
                    productsOnList.setShoppingList(shoppingList);
                    productsOnList.setProduct(ingredient.getProduct());
                    productsOnList.setQuantity(ingredient.getQuantity());
                    return productsOnList;
                }).toList();

        List<ProductsOnList> savedProductsOnList = productsOnLists.stream()
                .map(productsOnList -> {
                    Optional<ProductsOnList> existingProduct = productsOnListRepository.findByProductIdAndShoppingListId(productsOnList.getProduct().getProductId(), productsOnList.getShoppingList().getShoppingListId());

                    if (existingProduct.isPresent()) {

                        ProductsOnList foundProduct = existingProduct.get();
                        foundProduct.setQuantity(foundProduct.getQuantity() + productsOnList.getQuantity());
                        return productsOnListRepository.save(foundProduct);
                    } else {

                        return productsOnListRepository.save(productsOnList);
                    }
                })
                .collect(Collectors.toList());


        return productsOnListMapper.toDtoList(savedProductsOnList);
    }

    public List<List<ProductsOnListDTO>> addingProductsFromWeeklyPlan(Long foodPlanId, Long shoppingListId, String token) {
        List<WeeklyFoodPlanDTO> weeklyFoodPlansDTO = weeklyFoodPlanService.findAllByFoodPlanId(foodPlanId, token);
        List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlansDTO.stream()
                .map(weeklyFoodPlanMapper::toEntity).toList();

        List<Recipes> recipes = weeklyFoodPlans.stream()
                .map(WeeklyFoodPlan::getRecipes).toList();


        List<List<ProductsOnListDTO>> allProductsOnListDTOS = new ArrayList<>();

        for (Recipes recipe : recipes) {
            Long recipeId = recipe.getRecipeId();
            List<ProductsOnListDTO> productsOnListDTOS = addingAllProductsFromRecipe(recipeId, shoppingListId, token);
            allProductsOnListDTOS.add(productsOnListDTOS);
        }
        return allProductsOnListDTOS;

    }

    public boolean userAuthorization(String token, ProductsOnList productsOnList) {
        User user = userService.userFromToken(token);
        List<ShoppingList> userShoppingLists = shoppingListRepository.findAllByUser(user);

        return userShoppingLists.stream()
                .anyMatch(shoppingList -> shoppingList.getShoppingListId().equals(productsOnList.getShoppingList().getShoppingListId()));
    }


}
