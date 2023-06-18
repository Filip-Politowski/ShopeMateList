package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.mapper.ProductsMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }
    public ProductsDTO findById(Long id){
        Optional<Products> optionalProduct = productsRepository.findById(id);
        if(optionalProduct.isPresent()){
            Products product = optionalProduct.get();
            return productsMapper.toDTO(product);
        }
        throw new NoSuchElementException();
    }

    public List<ProductsDTO> findAll(){
        List<Products> products = productsRepository.findAll();
        return productsMapper.toDtoList(products);
    }


    public ProductsDTO createProducts(ProductsDTO productsDTO) {
        Products products = productsMapper.toEntity(productsDTO);
        Products savedProducts = productsRepository.save(products);
        return productsMapper.toDTO(savedProducts);
    }

    public void deleteById(Long id){
        productsRepository.deleteById(id);
    }

    public ProductsDTO update(ProductsDTO productsDTO){
        Products product = productsMapper.toEntity(productsDTO);
        Products updatedProduct = productsRepository.save(product);
        return productsMapper.toDTO(updatedProduct);
    }




}
