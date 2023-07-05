package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.ProductsDTO;
import pl.shopmatelist.shopmatelist.dto.request.SignInRequest;
import pl.shopmatelist.shopmatelist.services.JwtService;
import pl.shopmatelist.shopmatelist.services.ProductsService;
import pl.shopmatelist.shopmatelist.services.UserService;
import pl.shopmatelist.shopmatelist.services.impl.JwtServiceImpl;
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
        return productsService.findById(id);
    }

    @GetMapping()
    List<ProductsDTO> findAllProducts(){
        return productsService.findAll();
    }

    @PostMapping()

    public ProductsDTO createProduct(@RequestBody ProductsDTO productsDTO, @RequestHeader("Authorization")String authorizationHeader) {

        System.out.println(userService.userFromToken(authorizationHeader));

        return productsService.createProducts(productsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productsService.deleteById(id);
    }

    @PutMapping()
    public ProductsDTO updateProduct(ProductsDTO productsDTO){
        return productsService.update(productsDTO);
    }




}