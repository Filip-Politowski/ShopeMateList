package pl.shopmatelist.shopmatelist.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.ProductNotFoundException;
import pl.shopmatelist.shopmatelist.services.ProductsService;
import pl.shopmatelist.shopmatelist.services.impl.UserServiceImpl;

import java.util.List;

@RestController


@Data
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final UserServiceImpl userService;


    @GetMapping("/{id}")
    public ProductsDTO findProductById(@PathVariable Long id) {
        try {
            return productsService.findById(id);
        }catch (ProductNotFoundException exc){
            throw new ProductNotFoundException(exc.getMessage());
        }

    }

    @GetMapping()
    List<ProductsDTO> findAllProducts() {
        return productsService.findAll();
    }

    @PostMapping()
    public ProductsDTO createProduct(@RequestBody ProductsDTO productsDTO) {
        try {
            return productsService.createProducts(productsDTO);
        }catch (IllegalArgumentException exc){
            throw new IllegalArgumentException(exc.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        try {
            productsService.deleteById(id);
        }catch (ProductNotFoundException exc){
            throw new ProductNotFoundException(exc.getMessage());
        }

    }

    @PutMapping()
    public ProductsDTO updateProduct(ProductsDTO productsDTO) {
        try {
            return productsService.update(productsDTO);
        }catch (IllegalArgumentException exc){
            throw new IllegalArgumentException(exc.getMessage());
        }
    }

}