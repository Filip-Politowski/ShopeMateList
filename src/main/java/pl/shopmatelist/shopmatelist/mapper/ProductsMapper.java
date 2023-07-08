package pl.shopmatelist.shopmatelist.mapper;

import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestProductsDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductsMapper {

    public ResponseProductsDTO toDTO(Products products) {
        ResponseProductsDTO dto = new ResponseProductsDTO();
        dto.setProductId(products.getProductId());
        dto.setProductName(products.getProductName());
        dto.setFoodCategory(products.getFoodCategory());
        return dto;
    }

    public Products toEntity(RequestProductsDTO dto) {
        Products products = new Products();
        products.setProductId(dto.getProductId());
        products.setProductName(dto.getProductName());
        products.setFoodCategory(dto.getFoodCategory());
        return products;
    }

    public List<ResponseProductsDTO> toDtoList(List<Products> products){
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
