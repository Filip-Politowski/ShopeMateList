package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.mapper.ProductsMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    public ProductsDTO createProducts(ProductsDTO productsDTO) {
        Products products = productsMapper.toEntity(productsDTO);
        Products savedProducts = productsRepository.save(products);
        return productsMapper.toDTO(savedProducts);
    }

    // Add other methods as needed
}
