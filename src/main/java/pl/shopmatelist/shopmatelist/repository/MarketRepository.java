package pl.shopmatelist.shopmatelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shopmatelist.shopmatelist.entity.Market;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
