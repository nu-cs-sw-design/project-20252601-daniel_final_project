package domain.game;

public class Card {
    
    private final CardType type;
    private final CardAction action;
    
    public Card(CardType type, CardAction action) {
        this.type = type;
        this.action = action;
    }
    
    public CardType getType() {
        return type;
    }
    
    public CardAction getAction() {
        return action;
    }
}
