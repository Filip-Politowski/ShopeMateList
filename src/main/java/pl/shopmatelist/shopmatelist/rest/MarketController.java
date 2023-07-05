package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.MarketDTO;
import pl.shopmatelist.shopmatelist.services.MarketService;

import java.util.List;

@RestController
@RequestMapping("/api/market")
@AllArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/{id}")
    public MarketDTO findMarketById(@PathVariable Long id){
        return marketService.findById(id);
    }

    @GetMapping()
    public List<MarketDTO> findAllMarkets(){
        return marketService.findAll();
    }

    @PostMapping()
    public MarketDTO createMarket(@RequestBody MarketDTO marketDTO){
        return marketService.save(marketDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMarket(@PathVariable Long id){
        marketService.deleteById(id);
    }

    @PutMapping()
    public MarketDTO updateMarket(@RequestBody MarketDTO marketDTO){
        return marketService.update(marketDTO);
    }



}
