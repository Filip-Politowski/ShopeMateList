package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.MarketDTO;
import pl.shopmatelist.shopmatelist.entity.response.DeleteResponse;
import pl.shopmatelist.shopmatelist.exceptions.AuthorizationException;
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
    public MarketDTO createMarket(@RequestBody MarketDTO marketDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            try {
                return marketService.save(marketDTO);
            } catch (IllegalArgumentException exc) {
                throw new IllegalArgumentException(exc.getMessage());
            }
        }
        throw new AuthorizationException("Potrzebne uprawnienia administratora");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteMarket(@PathVariable Long id, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            try {
                marketService.deleteById(id);
                return new ResponseEntity<>(new DeleteResponse("Market usunięty poprawnie", 200), HttpStatus.OK);
            } catch (MarketNotFoundException exc) {
                throw new MarketNotFoundException(exc.getMessage());
            }
        }
        throw new AuthorizationException("Potrzebne uprawnienia administratora");
    }

    @PutMapping()
    public MarketDTO updateMarket(@RequestBody MarketDTO marketDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            try {
                return marketService.update(marketDTO);
            } catch (IllegalArgumentException exc) {
                throw new IllegalArgumentException(exc.getMessage());
            }

        }
        throw new AuthorizationException("Potrzebne uprawnienia administratora");
    }

}
