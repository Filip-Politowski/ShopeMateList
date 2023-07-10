package pl.shopmatelist.shopmatelist.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.shopmatelist.shopmatelist.dto.request.RequestMarketDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseMarketDTO;
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
    public ResponseMarketDTO findMarketById(@PathVariable Long id) {
        try {
            return marketService.findById(id);
        } catch (MarketNotFoundException exc) {
            throw new MarketNotFoundException(exc.getMessage());
        }

    }

    @GetMapping()
    public List<ResponseMarketDTO> findAllMarkets(@RequestParam(name = "sort", defaultValue = "false") boolean sort) {
        try {
            return marketService.findAll(sort);
        } catch (MarketNotFoundException exc) {
            throw new MarketNotFoundException(exc.getMessage());
        }
    }

    @PostMapping()
    public ResponseMarketDTO createMarket(@RequestBody RequestMarketDTO requestMarketDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            return marketService.save(requestMarketDTO);
        }
        throw new AuthorizationException("Potrzebne uprawnienia administratora");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteMarket(@PathVariable Long id, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            marketService.deleteById(id);
            return new ResponseEntity<>(new DeleteResponse("Market usunięty poprawnie", 200), HttpStatus.OK);
        }
        throw new AuthorizationException("Potrzebne uprawnienia administratora");
    }

    @PutMapping()
    public ResponseMarketDTO updateMarket(@RequestBody RequestMarketDTO requestMarketDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            return marketService.update(requestMarketDTO);
        }
        throw new AuthorizationException("Potrzebne uprawnienia administratora");
    }

}
