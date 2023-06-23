package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.mapper.ProductsOnListMapper;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;

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
    private final ShoppingListService shoppingListService;
    private final ShoppingListMapper shoppingListMapper;


    public ProductsOnListDTO findById(Long id) {
        Optional<ProductsOnList> optionalProductsOnList = productsOnListRepository.findById(id);
        if (optionalProductsOnList.isPresent()) {
            ProductsOnList productsOnList = optionalProductsOnList.get();
            return productsOnListMapper.toDto(productsOnList);
        }
        throw new NoSuchElementException();
    }

    public List<ProductsOnListDTO> findAllByShoppingListId(Long shoppingListId) {
        List<ProductsOnList> productsOnLists = productsOnListRepository.findAllByShoppingListId(shoppingListId);
        return productsOnListMapper.toDtoList(productsOnLists);
    }

    public ProductsOnListDTO save(ProductsOnListDTO productsOnListDTO) {
        ProductsOnList product = productsOnListMapper.toEntity(productsOnListDTO);
        Optional<ProductsOnList> existingProduct = productsOnListRepository.findByProductIdAndShoppingListId(product.getProduct().getProductId(), product.getShoppingList().getShoppingListId());

        if (existingProduct.isPresent()) {
            ProductsOnList foundProduct = existingProduct.get();

                foundProduct.setQuantity(foundProduct.getQuantity() + product.getQuantity());
                ProductsOnList savedUpdatedProductOnList = productsOnListRepository.save(foundProduct);
                return productsOnListMapper.toDto(savedUpdatedProductOnList);

        } else {

            ProductsOnList savedProductOnList = productsOnListRepository.save(product);
            return productsOnListMapper.toDto(savedProductOnList);
        }

    }

    public void deleteById(Long id) {
        productsOnListRepository.deleteById(id);
    }

    public ProductsOnListDTO update(ProductsOnListDTO productsOnListDTO) {
        ProductsOnList product = productsOnListMapper.toEntity(productsOnListDTO);
        Optional<ProductsOnList> existingProduct = productsOnListRepository.findByProductIdAndShoppingListId(product.getProduct().getProductId(),product.getShoppingList().getShoppingListId());

        if (existingProduct.isPresent()) {

            ProductsOnList foundProduct = existingProduct.get();
            foundProduct.setQuantity(product.getQuantity());
            ProductsOnList savedUpdatedProductOnList = productsOnListRepository.save(foundProduct);
            return productsOnListMapper.toDto(savedUpdatedProductOnList);
        } else {

            throw new NoSuchElementException("Nie da się zaktualizować produktu, którego nie ma");
        }

    }

    public List<ProductsOnListDTO> addingAllProductsFromRecipe(Long recipeId, Long shoppingListId) {

        List<Ingredients> ingredients = ingredientsService.getIngredientsByRecipeId(recipeId);
        ShoppingListDTO shoppingListDto = shoppingListService.findById(shoppingListId);
        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDto);


        List<ProductsOnList> productsOnLists = ingredients.stream()
                .map(ingredient -> {
                    ProductsOnList productsOnList = new ProductsOnList();
                    productsOnList.setShoppingList(shoppingList);
                    productsOnList.setProduct(ingredient.getProduct());
                    productsOnList.setQuantity(ingredient.getQuantity());
                    return productsOnList;
                })
                .collect(Collectors.toList());

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


}
