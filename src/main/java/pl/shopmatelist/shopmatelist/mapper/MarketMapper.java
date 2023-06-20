package pl.shopmatelist.shopmatelist.mapper;

import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.MarketDTO;
import pl.shopmatelist.shopmatelist.entity.Market;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketMapper {
    public MarketDTO toDto(Market market){
        MarketDTO dto = new MarketDTO();
        dto.setMarketId(market.getMarketId());
        dto.setMarketName(market.getMarketName());
        return dto;
    }

    public Market toEntity(MarketDTO marketDTO){
        Market market = new Market();
        market.setMarketName(marketDTO.getMarketName());
        market.setMarketId(marketDTO.getMarketId());
        return market;
    }

    public List<MarketDTO> toDtoList(List<Market> markets){
        return markets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
