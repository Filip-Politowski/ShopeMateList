package pl.shopmatelist.shopmatelist.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.ShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.Market;

import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.repository.MarketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingListMapper {

    private final MarketRepository marketRepository;

    @Autowired
    public ShoppingListMapper(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public ShoppingListDTO toDTO(ShoppingList shoppingList) {
        ShoppingListDTO dto = new ShoppingListDTO();
        dto.setShoppingListId(shoppingList.getShoppingListId());
        dto.setMarketId(shoppingList.getMarket().getMarketId());
        dto.setShoppingDate(shoppingList.getShoppingDate());
        return dto;
    }

    public ShoppingList toEntity(ShoppingListDTO dto) {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.setShoppingListId(dto.getShoppingListId());
        shoppingList.setShoppingDate(dto.getShoppingDate());
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        shoppingList.setMarket(market);

        return shoppingList;

    }

    public List<ShoppingListDTO> toDtoList(List<ShoppingList> shoppingLists){
        return shoppingLists.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
