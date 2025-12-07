package domain.game.behaviors;

import domain.game.CardType;
import domain.game.Game;
import domain.game.Player;
import ui.GameUI;

public class ExplodingKittenBehavior implements CardBehavior {
    
    @Override
    public CardResult execute(Game game, Player player, GameUI ui) {
        int playerIndex = findPlayerIndex(game, player);
        if (playerIndex == -1) {
            return CardResult.failure("Player not found in game.");
        }
        
        if (player.hasCard(CardType.DEFUSE)) {
            return CardResult.success("Player has a Defuse card and can defuse the Exploding Kitten.");
        } else {
            boolean eliminated = game.playExplodingKitten(playerIndex);
            if (eliminated) {
                return CardResult.eliminated("Player has been eliminated by the Exploding Kitten!");
            } else {
                return CardResult.success("Player defused the Exploding Kitten.");
            }
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
        return "You must defuse this card or you're eliminated from the game!";
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

