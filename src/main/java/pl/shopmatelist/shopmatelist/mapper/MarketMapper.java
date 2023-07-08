package pl.shopmatelist.shopmatelist.mapper;

import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestMarketDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseMarketDTO;
import pl.shopmatelist.shopmatelist.entity.Market;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketMapper {
    public ResponseMarketDTO toDto(Market market){
        ResponseMarketDTO dto = new ResponseMarketDTO();
        dto.setMarketId(market.getMarketId());
        dto.setMarketName(market.getMarketName());
        return dto;
    }

    public Market toEntity(RequestMarketDTO requestMarketDTO){
        Market market = new Market();
        market.setMarketName(requestMarketDTO.getMarketName());
        market.setMarketId(requestMarketDTO.getMarketId());
        return market;
    }

    public List<ResponseMarketDTO> toDtoList(List<Market> markets){
        return markets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
