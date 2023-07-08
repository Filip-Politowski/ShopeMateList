package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import pl.shopmatelist.shopmatelist.dto.request.RequestProductsDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseProductsDTO;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ProductNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.ProductsMapper;
import pl.shopmatelist.shopmatelist.repository.ProductsRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;


    public ResponseProductsDTO findById(Long id) {
        Optional<Products> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            return productsMapper.toDTO(product);
        }
        throw new ProductNotFoundException("Nie ma produktu o podanym id:  " + id);
    }

    public List<ResponseProductsDTO> findAll() {
        List<Products> products = productsRepository.findAll();
        return productsMapper.toDtoList(products);
    }


    public ResponseProductsDTO createProducts(RequestProductsDTO requestProductsDTO) {
        List<Products> products = productsRepository.findAll();
        Products product = productsMapper.toEntity(requestProductsDTO);
        boolean exist = products.stream().anyMatch(productInDB -> productInDB.getProductName().equals(product.getProductName().toLowerCase()) || productInDB.getProductId().equals(product.getProductId()));
        if (exist) {
            throw new IllegalArgumentException("Produkt o nazwie: " + product.getProductName() + " już istnieje");
        } else {
            Products savedProducts = productsRepository.save(product);
            return productsMapper.toDTO(savedProducts);
        }

    }

    public void deleteById(Long id) {

        Optional<Products> productToDelete = productsRepository.findById(id);
        if (productToDelete.isEmpty()) {
            throw new ProductNotFoundException("Nie ma produktu o id: " + id + " w bazie");
        }
        productsRepository.deleteById(id);

    }

    public ResponseProductsDTO update(RequestProductsDTO requestProductsDTO) {
        if (requestProductsDTO.getProductId() == null) {
            throw new IllegalArgumentException("ID produktu nie może byc puste!");
        }

        Products product = productsMapper.toEntity(requestProductsDTO);
        Products updatedProduct = productsRepository.save(product);
        return productsMapper.toDTO(updatedProduct);
    }


}
