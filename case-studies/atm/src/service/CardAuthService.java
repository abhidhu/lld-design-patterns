package service;

import java.util.HashMap;
import java.util.Map;

public class CardAuthService {
    // Fake DB: cardNumber → hashedPIN
    private final Map<String, String> cardPinStore = new HashMap<>();

    public void registerCard(String cardNo, String pin) {
        cardPinStore.put(cardNo, hash(pin));
    }

    public boolean authenticate(String cardNo, String pin) {
        String stored = cardPinStore.get(cardNo);
        return stored != null && stored.equals(hash(pin));
    }

    // In production this would be BCrypt/SHA — simplified here
    private String hash(String pin) {
        return String.valueOf(pin.hashCode());
    }
}
