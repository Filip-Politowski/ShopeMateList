package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.request.RequestMarketDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseMarketDTO;
import pl.shopmatelist.shopmatelist.entity.Market;
import pl.shopmatelist.shopmatelist.exceptions.IllegalArgumentException;
import pl.shopmatelist.shopmatelist.exceptions.MarketNotFoundException;
import pl.shopmatelist.shopmatelist.mapper.MarketMapper;
import pl.shopmatelist.shopmatelist.repository.MarketRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;


    public ResponseMarketDTO findById(Long id) {
        Optional<Market> optionalMarket = marketRepository.findById(id);
        if (optionalMarket.isPresent()) {
            Market market = optionalMarket.get();
            return marketMapper.toDto(market);
        }
        throw new MarketNotFoundException("Nie ma takiego marketu w bazie danych");
    }

    public List<ResponseMarketDTO> findAll(boolean sort) {
        List<Market> markets = marketRepository.findAll();
        if(sort){
            markets.sort(Comparator.comparing(Market::getMarketName));
        }
        if (markets.isEmpty()) {
            throw new MarketNotFoundException("Baza marketów jest pusta");
        }
        return marketMapper.toDtoList(markets);
    }

    public ResponseMarketDTO save(RequestMarketDTO requestMarketDTO) {
        Market market = marketMapper.toEntity(requestMarketDTO);
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

    public ResponseMarketDTO update(RequestMarketDTO requestMarketDTO) {
        if (requestMarketDTO.getMarketId() == null) {
            throw new IllegalArgumentException("Należy podać id Marketu");
        }
        Market market = marketMapper.toEntity(requestMarketDTO);
        Market updatedMarket = marketRepository.save(market);
        return marketMapper.toDto(updatedMarket);
    }

}
