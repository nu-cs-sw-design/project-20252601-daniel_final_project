package domain.game.behaviors;

import domain.game.Game;
import domain.game.Player;

public class DefuseBehavior implements CardBehavior {
    
    private int insertionIndex = 0;
    
    public void setInsertionIndex(int index) {
        this.insertionIndex = index;
    }
    
    @Override
    public CardResult execute(Game game, Player player, UIHandler uiHandler) {
        int playerIndex = findPlayerIndex(game, player);
        if (playerIndex == -1) {
            return CardResult.failure("Player not found in game.");
        }
        
        try {
            game.playDefuse(insertionIndex, playerIndex);
            return CardResult.success("Exploding Kitten defused and placed back in deck.");
        } catch (UnsupportedOperationException e) {
            return CardResult.failure(e.getMessage());
        }
    }
    
    @Override
    public boolean canBeNoped() {
        return false;
    }
    
    @Override
    public boolean endsTurn() {
        return true;
    }
    
    @Override
    public String getDescription() {
        return "Defuses an Exploding Kitten. Put the kitten back in the deck anywhere you want.";
    }
    
    private int findPlayerIndex(Game game, Player player) {
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            if (game.getPlayerAtIndex(i) == player) {
                return i;
            }
        }
        return -1;
    }
}
