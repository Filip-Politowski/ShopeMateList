package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.MarketDTO;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.MarketNotFoundException;
import pl.shopmatelist.shopmatelist.services.MarketService;

import java.util.List;

@RestController
@RequestMapping("/api/market")
@AllArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/{id}")
    public MarketDTO findMarketById(@PathVariable Long id) {
        try {
            return marketService.findById(id);
        } catch (MarketNotFoundException exc) {
            throw new MarketNotFoundException(exc.getMessage());
        }

    }

    @GetMapping()
    public List<MarketDTO> findAllMarkets() {
        try {
            return marketService.findAll();
        } catch (MarketNotFoundException exc) {
            throw new MarketNotFoundException(exc.getMessage());
        }
    }

    @PostMapping()
    public MarketDTO createMarket(@RequestBody MarketDTO marketDTO) {
        try {
            return marketService.save(marketDTO);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMarket(@PathVariable Long id) {
        try {
            marketService.deleteById(id);
        } catch (MarketNotFoundException exc) {
            throw new MarketNotFoundException(exc.getMessage());
        }
    }

    @PutMapping()
    public MarketDTO updateMarket(@RequestBody MarketDTO marketDTO) {
        try {
            return marketService.update(marketDTO);
        }catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc.getMessage());
        }

    }


}
