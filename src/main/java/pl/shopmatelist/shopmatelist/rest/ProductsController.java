package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products/{id}")
    public ProductsDTO findProductById(@PathVariable Long id) {
        return productsService.findById(id);
    }

    @GetMapping("/products")
    List<ProductsDTO> findAllProducts(){
        return productsService.findAll();
    }

    @PostMapping("/products")

    public ProductsDTO createProduct(@RequestBody ProductsDTO productsDTO) {
        return productsService.createProducts(productsDTO);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable Long id){
        productsService.deleteById(id);
    }

    @PutMapping("/products")
    public ProductsDTO updateProduct(ProductsDTO productsDTO){
        return productsService.update(productsDTO);
    }



}