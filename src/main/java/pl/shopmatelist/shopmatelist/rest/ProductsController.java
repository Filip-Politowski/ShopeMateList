package pl.shopmatelist.shopmatelist.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestProductsDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseProductsDTO;
import pl.shopmatelist.shopmatelist.entity.response.DeleteResponse;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
import pl.shopmatelist.shopmatelist.exceptions.ProductNotFoundException;
import pl.shopmatelist.shopmatelist.services.ProductsService;
import pl.shopmatelist.shopmatelist.services.security.impl.UserServiceImpl;

import java.util.List;

@RestController


@Data
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final UserServiceImpl userService;


    @GetMapping("/{id}")
    public ResponseProductsDTO findProductById(@PathVariable Long id) {
        try {
            return productsService.findById(id);
        } catch (ProductNotFoundException exc) {
            throw new ProductNotFoundException(exc.getMessage());
        }

    }

    @GetMapping()
    List<ResponseProductsDTO> findAllProducts() {
        return productsService.findAll();
    }

    @PostMapping()
    public ResponseProductsDTO createProduct(@RequestBody RequestProductsDTO requestProductsDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {

                return productsService.createProducts(requestProductsDTO);
        }
        throw new AuthorizationException("Potrzebne są uprawienia administratora");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteProductById(@PathVariable Long id, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {

                productsService.deleteById(id);
                return new ResponseEntity<>(new DeleteResponse("Produkt usunięty poprawnie", 200), HttpStatus.OK);

        }
        throw new AuthorizationException("Potrzebne są uprawienia administratora");
    }

    @PutMapping()
    public ResponseProductsDTO updateProduct(@RequestBody RequestProductsDTO requestProductsDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {

                return productsService.update(requestProductsDTO);
        }
        throw new AuthorizationException("Potrzebne są uprawienia administratora");
    }

}