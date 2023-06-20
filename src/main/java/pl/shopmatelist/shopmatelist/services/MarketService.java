package pl.shopmatelist.shopmatelist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.MarketDTO;
import pl.shopmatelist.shopmatelist.entity.Market;
import pl.shopmatelist.shopmatelist.mapper.MarketMapper;
import pl.shopmatelist.shopmatelist.repository.MarketRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;

    @Autowired
    public MarketService(MarketRepository marketRepository, MarketMapper marketMapper) {
        this.marketRepository = marketRepository;
        this.marketMapper = marketMapper;
    }

    public MarketDTO findById(Long id) {
        Optional<Market> optionalMarket = marketRepository.findById(id);
        if (optionalMarket.isPresent()) {
            Market market = optionalMarket.get();
            return marketMapper.toDto(market);
        }
        throw new NoSuchElementException();
    }

    public List<MarketDTO> findAll() {
        List<Market> markets = marketRepository.findAll();
        return marketMapper.toDtoList(markets);
    }

    public MarketDTO save(MarketDTO marketDTO) {
        Market market = marketMapper.toEntity(marketDTO);
        Market savedMarket = marketRepository.save(market);
        return marketMapper.toDto(savedMarket);
    }

    public void deleteById(Long id) {
        marketRepository.deleteById(id);
    }

    public MarketDTO update(MarketDTO marketDTO) {
        Market market = marketMapper.toEntity(marketDTO);
        Market updatedMarket = marketRepository.save(market);
        return marketMapper.toDto(updatedMarket);
    }

}
