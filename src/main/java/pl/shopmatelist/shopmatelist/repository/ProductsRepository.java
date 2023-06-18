package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shopmatelist.shopmatelist.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
