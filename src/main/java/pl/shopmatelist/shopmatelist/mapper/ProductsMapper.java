package pl.shopmatelist.shopmatelist.mapper;

import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductsMapper {

    public ProductsDTO toDTO(Products products) {
        ProductsDTO dto = new ProductsDTO();
        dto.setProductId(products.getProductId());
        dto.setProductName(products.getProductName());
        dto.setCategory(products.getCategory());
        return dto;
    }

    public Products toEntity(ProductsDTO dto) {
        Products products = new Products();
        products.setProductId(dto.getProductId());
        products.setProductName(dto.getProductName());
        products.setCategory(dto.getCategory());
        return products;
    }

    public List<ProductsDTO> toDtoList(List<Products> products){
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
