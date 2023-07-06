package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ProductNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.ProductsMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;
import pl.shopmatelist.shopmatelist.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;


    public ProductsDTO findById(Long id) {
        Optional<Products> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            return productsMapper.toDTO(product);
        }
        throw new ProductNotFoundException("Nie ma produktu o podanym id:  " + id);
    }

    public List<ProductsDTO> findAll() {
        List<Products> products = productsRepository.findAll();
        return productsMapper.toDtoList(products);
    }


    public ProductsDTO createProducts(ProductsDTO productsDTO) {
        List<Products> products = productsRepository.findAll();
        Products product = productsMapper.toEntity(productsDTO);
        boolean exist = products.stream().anyMatch(productInDB -> productInDB.getProductName().equals(product.getProductName().toLowerCase()));
        if (exist) {
            throw new IllegalArgumentException("Produkt o nazwie: " + product.getProductName() + " już istnieje");
        } else {
            Products savedProducts = productsRepository.save(product);
            return productsMapper.toDTO(savedProducts);
        }

    }

    public void deleteById(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new ProductNotFoundException("Nie ma produktu o podanym id:  " + id);
        }
        productsRepository.deleteById(id);
    }

    public ProductsDTO update(ProductsDTO productsDTO) {
        if(productsDTO.getProductId() == null) {
            throw new IllegalArgumentException("ID produktu nie może byc puste!");
        }
        Products product = productsMapper.toEntity(productsDTO);
        Products updatedProduct = productsRepository.save(product);
        return productsMapper.toDTO(updatedProduct);
    }


}
