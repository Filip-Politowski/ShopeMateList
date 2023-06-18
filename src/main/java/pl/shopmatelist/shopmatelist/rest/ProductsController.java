package pl.shopmatelist.shopmatelist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.services.ProductsService;

@RestController
@RequestMapping("/api")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductsDTO> createProducts(@RequestBody ProductsDTO productsDTO) {
        ProductsDTO createdProducts = productsService.createProducts(productsDTO);
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

    // Add other endpoints as needed
}