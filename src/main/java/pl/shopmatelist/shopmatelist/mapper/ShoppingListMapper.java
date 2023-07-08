package pl.shopmatelist.shopmatelist.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.shopmatelist.shopmatelist.dto.request.RequestShoppingListDTO;
import pl.shopmatelist.shopmatelist.dto.response.ResponseShoppingListDTO;
import pl.shopmatelist.shopmatelist.entity.Market;

import pl.shopmatelist.shopmatelist.entity.ShoppingList;
import pl.shopmatelist.shopmatelist.repository.MarketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ShoppingListMapper {

    private final MarketRepository marketRepository;



    public ResponseShoppingListDTO toDTO(ShoppingList shoppingList) {
        ResponseShoppingListDTO dto = new ResponseShoppingListDTO();
        dto.setShoppingListId(shoppingList.getShoppingListId());
        dto.setMarketId(shoppingList.getMarket().getMarketId());
        dto.setShoppingDate(shoppingList.getShoppingDate());
        dto.setMarketName(shoppingList.getMarket().getMarketName());
        dto.setOwner(shoppingList.getOwner());
        dto.setUser(shoppingList.getUser().getId());
        return dto;
    }

    public ShoppingList toEntity(RequestShoppingListDTO dto) {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.setShoppingListId(dto.getShoppingListId());
        shoppingList.setShoppingDate(dto.getShoppingDate());
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        shoppingList.setMarket(market);

        return shoppingList;

    }

    public List<ResponseShoppingListDTO> toDtoList(List<ShoppingList> shoppingLists){
        return shoppingLists.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
