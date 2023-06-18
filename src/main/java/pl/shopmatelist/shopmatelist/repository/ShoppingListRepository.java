package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shopmatelist.shopmatelist.entity.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
}
