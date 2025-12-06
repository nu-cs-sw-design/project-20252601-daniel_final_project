package domain.game;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    private List<Card> cards;
    
    public Deck() {
        this.cards = new ArrayList<>();
    }
    
    public void initialize(int numberOfPlayers) {
        cards.clear();
        
        for (int i = 0; i < 4; i++) {
            cards.add(CardFactory.createShuffle());
        }
        
        for (int i = 0; i < 4; i++) {
            cards.add(CardFactory.createNope());
        }
        
        for (int i = 0; i < 4; i++) {
            cards.add(CardFactory.createDefuse());
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public void shuffleDeck() {
        shuffle();
    }
    
    public Card draw() {
        if (cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        return cards.remove(cards.size() - 1);
    }
    
    public void insertAt(int position, Card card) {
        if (position < 0) {
            position = 0;
        }
        if (position > cards.size()) {
            position = cards.size();
        }
        cards.add(position, card);
    }
    
    public void insertExplodingKittenAtIndex(int index) {
        insertAt(index, CardFactory.createExplodingKitten());
    }
    
    public int getSize() {
        return cards.size();
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
