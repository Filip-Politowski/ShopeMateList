package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.shopmatelist.shopmatelist.entity.ProductsOnList;

@Repository
public interface ProductsOnListRepository extends JpaRepository<ProductsOnList, Long> {
}
