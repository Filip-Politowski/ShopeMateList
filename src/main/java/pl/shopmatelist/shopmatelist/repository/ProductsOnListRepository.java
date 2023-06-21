package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.shopmatelist.shopmatelist.entity.Products;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;

import java.util.Optional;

@Repository
public interface ProductsOnListRepository extends JpaRepository<ProductsOnList, Long> {
    @Query("SELECT p FROM ProductsOnList p WHERE p.product.productId = :productId")
    Optional<ProductsOnList> findByProductId(@Param("productId") Long productId);
}
