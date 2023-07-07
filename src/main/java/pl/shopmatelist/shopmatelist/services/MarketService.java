package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.MarketDTO;
import pl.shopmatelist.shopmatelist.entity.Market;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.MarketNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.MarketMapper;
import pl.shopmatelist.shopmatelist.repository.MarketRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;


    public MarketDTO findById(Long id) {
        Optional<Market> optionalMarket = marketRepository.findById(id);
        if (optionalMarket.isPresent()) {
            Market market = optionalMarket.get();
            return marketMapper.toDto(market);
        }
        throw new MarketNotFoundException("Nie ma takiego marketu w bazie danych");
    }

    public List<MarketDTO> findAll() {
        List<Market> markets = marketRepository.findAll();
        if (markets.isEmpty()) {
            throw new MarketNotFoundException("Baza marketów jest pusta");
        }
        return marketMapper.toDtoList(markets);
    }

    public MarketDTO save(MarketDTO marketDTO) {
        Market market = marketMapper.toEntity(marketDTO);
        List<Market> markets = marketRepository.findAll();
        if (markets.stream().anyMatch(marketsDB -> marketsDB.getMarketName().toUpperCase().equals(market.getMarketName().toUpperCase().trim()))) {
            throw new IllegalArgumentException("Taki market już istnieje w bazie");
        }
        market.setMarketName(market.getMarketName().toUpperCase());
        Market savedMarket = marketRepository.save(market);
        return marketMapper.toDto(savedMarket);
    }

    public void deleteById(Long id) {
        Optional<Market> marketDB = marketRepository.findById(id);
        if (marketDB.isEmpty()) {
            throw new MarketNotFoundException("Nie ma takiego marketu w bazie danych");
        }
        marketRepository.deleteById(id);
    }

    public MarketDTO update(MarketDTO marketDTO) {
        if (marketDTO.getMarketId() == null) {
            throw new IllegalArgumentException("Należy podać id Marketu");
        }
        Market market = marketMapper.toEntity(marketDTO);
        Market updatedMarket = marketRepository.save(market);
        return marketMapper.toDto(updatedMarket);
    }

}
