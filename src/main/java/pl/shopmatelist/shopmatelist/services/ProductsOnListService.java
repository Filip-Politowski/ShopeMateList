package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.Ingredients;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.mapper.IngredientsMapper;
import pl.shopmatelist.shopmatelist.mapper.ProductsMapper;
import pl.shopmatelist.shopmatelist.mapper.ProductsOnListMapper;
import pl.shopmatelist.shopmatelist.mapper.ShoppingListMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;

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
    private final ProductsMapper productsMapper;
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

    public List<ProductsOnListDTO> findAll() {
        List<ProductsOnList> productsOnLists = productsOnListRepository.findAll();
        return productsOnListMapper.toDtoList(productsOnLists);
    }

    public ProductsOnListDTO save(ProductsOnListDTO productsOnListDTO) {
        ProductsOnList productsOnList = productsOnListMapper.toEntity(productsOnListDTO);
        ProductsOnList savedProductsOnList = productsOnListRepository.save(productsOnList);
        return productsOnListMapper.toDto(savedProductsOnList);
    }

    public void deleteById(Long id) {
        productsOnListRepository.deleteById(id);
    }

    public ProductsOnListDTO update(ProductsOnListDTO productsOnListDTO) {
        ProductsOnList productsOnList = productsOnListMapper.toEntity(productsOnListDTO);
        ProductsOnList updatedProductsOnList = productsOnListRepository.save(productsOnList);
        return productsOnListMapper.toDto(updatedProductsOnList);
    }

    public List<ProductsOnListDTO> addingAllProductsByRecipe(Long recipeId, Long shoppingListId) {


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
                    Optional<ProductsOnList> existingProduct = productsOnListRepository.findByProductId(productsOnList.getProduct().getProductId());

                    if (existingProduct.isPresent()) {

                        ProductsOnList updatedProduct = existingProduct.get();
                        updatedProduct.setQuantity(updatedProduct.getQuantity() + productsOnList.getQuantity());
                        return productsOnListRepository.save(updatedProduct);
                    } else {

                        return productsOnListRepository.save(productsOnList);

                    }
                })
                .collect(Collectors.toList());



        return productsOnListMapper.toDtoList(savedProductsOnList);
    }


}
