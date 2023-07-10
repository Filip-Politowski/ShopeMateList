package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseProductsOnListDTO;
import pl.shopmatelist.shopmatelist.entity.*;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ProductOnListNotFoundException;
import pl.shopmatelist.shopmatelist.exceptions.ShoppingListNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.ProductsOnListMapper;
import pl.shopmatelist.shopmatelist.repository.IngredientsRepository;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;
import pl.shopmatelist.shopmatelist.repository.WeeklyFoodPlanRepository;
import pl.shopmatelist.shopmatelist.services.security.AuthenticationService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductsOnListService {

    private final ProductsOnListRepository productsOnListRepository;
    private final ProductsOnListMapper productsOnListMapper;
    private final IngredientsRepository ingredientsRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final AuthenticationService authenticationService;
    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;


    public ResponseProductsOnListDTO findById(Long id) {

        Optional<ProductsOnList> optionalProductsOnList = productsOnListRepository.findById(id);

        if (optionalProductsOnList.isPresent()) {
            ProductsOnList foundProductOnList = optionalProductsOnList.get();
            if (userAuthorization(authenticationService.authenticatedUser(), foundProductOnList)) {

                return productsOnListMapper.toDto(foundProductOnList);
            } else {
                throw new AuthorizationException("Brak uprawnień do odczytania tego produktu");
            }
        }
        throw new ProductOnListNotFoundException("Nie ma takiego produktu na liście zakupowej");
    }

    public List<ResponseProductsOnListDTO> findAllByShoppingListId(Long shoppingListId, boolean sort) {

        List<ShoppingList> userShoppingLists = shoppingListRepository.findAllByUser(authenticationService.authenticatedUser());

        boolean hasMatchingShoppingList = userShoppingLists.stream()
                .anyMatch(shoppingList -> shoppingList.getShoppingListId().equals(shoppingListId));

        if (hasMatchingShoppingList) {
            List<ProductsOnList> allFoundProductsOnList = productsOnListRepository.findAllByShoppingListId(shoppingListId);
          if(sort){
              allFoundProductsOnList.sort(Comparator.comparing(productsOnList -> productsOnList.getProduct().getFoodCategory()));
          }



            return productsOnListMapper.toDtoList(allFoundProductsOnList);
        }
        throw new AuthorizationException("Brak uprawnień do odczytania tych produktów");

    }

    public ResponseProductsOnListDTO save(RequestProductsOnListDTO requestProductsOnListDTO) {

        ProductsOnList product = productsOnListMapper.toEntity(requestProductsOnListDTO);
        Optional<ProductsOnList> existingProduct = productsOnListRepository.findByProductIdAndShoppingListId(product.getProduct().getProductId(), product.getShoppingList().getShoppingListId());

        List<ShoppingList> userShoppingList = shoppingListRepository.findAllByUser(authenticationService.authenticatedUser());

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

    public void deleteById(Long id) {

        Optional<ProductsOnList> optionalProductsOnList = productsOnListRepository.findById(id);

        if (optionalProductsOnList.isPresent()) {
            ProductsOnList productToDelete = optionalProductsOnList.get();
            if (userAuthorization(authenticationService.authenticatedUser(), productToDelete)) {
                productsOnListRepository.delete(productToDelete);
            } else {
                throw new AuthorizationException("Brak uprawnień do usunięcia tego produktu");
            }
        } else {
            throw new ProductOnListNotFoundException("Nie ma produktu o id: " + id + " na liście zakupowej");
        }
    }


    public ResponseProductsOnListDTO update(RequestProductsOnListDTO requestProductsOnListDTO) {

        if (requestProductsOnListDTO.getListItemId() == null) {
            throw new IllegalArgumentException("Należy podać id produktu na liście");
        }

        ProductsOnList product = productsOnListMapper.toEntity(requestProductsOnListDTO);

        if (userAuthorization(authenticationService.authenticatedUser(), product)) {
            Optional<ProductsOnList> foundProductOnList = productsOnListRepository.findById(product.getListItemId());
            if (foundProductOnList.isPresent()) {
                ProductsOnList productToSet = foundProductOnList.get();
                productToSet.setQuantity(requestProductsOnListDTO.getQuantity());
                ProductsOnList savedProductOnList = productsOnListRepository.save(productToSet);
                return productsOnListMapper.toDto(savedProductOnList);

            }
        }
        throw new AuthorizationException("Nie masz dostępu do tego produktu na liście");
    }


    public List<ResponseProductsOnListDTO> addingAllProductsFromRecipe(Long recipeId, Long shoppingListId) {

        List<Ingredients> ingredients = ingredientsRepository.findAllByRecipe_RecipeId(recipeId);
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId).orElseThrow(() -> new ShoppingListNotFoundException("Nie ma takiej listy zakupowej"));

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

    public List<List<ResponseProductsOnListDTO>> addingProductsFromWeeklyPlan(Long foodPlanId, Long shoppingListId) {
        List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlan_FoodPlanId(foodPlanId);
        List<Recipes> recipes = weeklyFoodPlans.stream()
                .map(WeeklyFoodPlan::getRecipes).toList();


        List<List<ResponseProductsOnListDTO>> allProductsOnListDTOS = new ArrayList<>();

        for (Recipes recipe : recipes) {
            Long recipeId = recipe.getRecipeId();
            List<ResponseProductsOnListDTO> responseProductsOnListDTOS = addingAllProductsFromRecipe(recipeId, shoppingListId);
            allProductsOnListDTOS.add(responseProductsOnListDTOS);
        }
        return allProductsOnListDTOS;

    }

    public boolean userAuthorization(User user, ProductsOnList productsOnList) {
        List<ShoppingList> userShoppingLists = shoppingListRepository.findAllByUser(user);
        return userShoppingLists.stream()
                .anyMatch(shoppingList -> shoppingList.getShoppingListId().equals(productsOnList.getShoppingList().getShoppingListId()));
    }


}
