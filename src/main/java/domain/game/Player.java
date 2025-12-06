package domain.game;

import java.util.List;
import java.util.ArrayList;

public class Player {
    
    private final int id;
    private List<Card> hand;
    private boolean alive;
    
    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.alive = true;
    }
    
    public int getId() {
        return id;
    }
    
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    
    public int getHandSize() {
        return hand.size();
    }
    
    public Card getCard(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new InvalidMoveException("Invalid card index");
        }
        return hand.get(index);
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
    
    public Card removeCard(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new InvalidMoveException("Invalid card index");
        }
        return hand.remove(index);
    }
    
    public boolean hasCardOfType(CardType type) {
        for (Card card : hand) {
            if (card.getType() == type) {
                return true;
            }
        }
        return false;
    }
    
    public int findCardOfType(CardType type) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getType() == type) {
                return i;
            }
        }
        return -1;
    }
    
    public Card removeCardOfType(CardType type) {
        int index = findCardOfType(type);
        if (index >= 0) {
            return removeCard(index);
        }
        throw new InvalidMoveException("Player does not have card of type: " + type);
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void eliminate() {
        alive = false;
    }
    
    public boolean hasCard(CardType cardType) {
        return hasCardOfType(cardType);
    }
    
    public int getIndexOfCard(CardType cardType) {
        return findCardOfType(cardType);
    }
    
    public void removeCardFromHand(int index) {
        removeCard(index);
    }
    
    public void setIsDead() {
        eliminate();
    }
}
