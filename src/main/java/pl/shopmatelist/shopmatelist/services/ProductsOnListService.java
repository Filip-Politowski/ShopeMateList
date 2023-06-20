package pl.shopmatelist.shopmatelist.services;

import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ProductsOnListDTO;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;
import pl.shopmatelist.shopmatelist.mapper.ProductsOnListMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsOnListRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductsOnListService {

    private final ProductsOnListRepository productsOnListRepository;
    private final ProductsOnListMapper productsOnListMapper;

    public ProductsOnListService(ProductsOnListRepository productsOnListRepository, ProductsOnListMapper productsOnListMapper) {
        this.productsOnListRepository = productsOnListRepository;
        this.productsOnListMapper = productsOnListMapper;
    }

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


}
