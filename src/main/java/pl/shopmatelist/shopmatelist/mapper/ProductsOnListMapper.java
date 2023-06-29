package pl.shopmatelist.shopmatelist.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;
import pl.shopmatelist.shopmatelist.repository.ShoppingListRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductsOnListMapper {

    private final ShoppingListRepository shoppingListRepository;
    private final ProductsRepository productsRepository;



    public ProductsOnListDTO toDto(ProductsOnList productsOnList){
        ProductsOnListDTO dto = new ProductsOnListDTO();
        dto.setListItemId(productsOnList.getListItemId());
        dto.setQuantity(productsOnList.getQuantity());
        dto.setProductId(productsOnList.getProduct().getProductId());
        dto.setShoppingListId(productsOnList.getShoppingList().getShoppingListId());
        dto.setProductName(productsOnList.getProduct().getProductName());
        dto.setProductType(productsOnList.getProduct().getCategory());
        return dto;
    }

    public ProductsOnList toEntity(ProductsOnListDTO dto){

        ProductsOnList productsOnList = new ProductsOnList();

        productsOnList.setListItemId(dto.getListItemId());
        productsOnList.setQuantity(dto.getQuantity());

        ShoppingList shoppingList = shoppingListRepository.findById(dto.getShoppingListId()).orElseThrow(()-> new EntityNotFoundException("Shopping list not found"));
        productsOnList.setShoppingList(shoppingList);

        Products product = productsRepository.findById(dto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productsOnList.setProduct(product);

        return productsOnList;
    }

    public List<ProductsOnListDTO> toDtoList(List<ProductsOnList> productsOnLists){
        return productsOnLists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
