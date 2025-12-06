package domain.game;

import java.util.List;

public class TurnManager {
    
    private int currentPlayerIndex;
    private int turnsRemaining;
    private int numberOfPlayers;
    
    public TurnManager(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlayerIndex = 0;
        this.turnsRemaining = 1;
    }
    
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    
    public int getTurnsRemaining() {
        return turnsRemaining;
    }
    
    public void setTurnsRemaining(int turns) {
        this.turnsRemaining = turns;
    }
    
    public void decrementTurns() {
        turnsRemaining--;
    }
    
    public int advanceToNextPlayer(List<Player> players) {
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
        } while (!players.get(currentPlayerIndex).isAlive());
        
        return currentPlayerIndex;
    }
    
    public void skipTurn() {
        turnsRemaining = 0;
    }
    
    public void addExtraTurns(int turns) {
        turnsRemaining += turns;
    }
}

