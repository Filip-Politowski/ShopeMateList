package pl.shopmatelist.shopmatelist.exceptions;

public class MarketNotFoundException extends RuntimeException {
    public MarketNotFoundException(String message) {
        super(message);
    }
}
