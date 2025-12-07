package domain.game.behaviors;

import domain.game.Game;
import domain.game.Player;
import ui.GameUI;

public class ShuffleBehavior implements CardBehavior {
    
    private static final int DEFAULT_SHUFFLE_COUNT = 1;
    
    @Override
    public CardResult execute(Game game, Player player, GameUI ui) {
        game.playShuffle(DEFAULT_SHUFFLE_COUNT);
        return CardResult.success("Deck has been shuffled.");
    }
    
    @Override
    public boolean canBeNoped() {
        return true;
    }
    
    @Override
    public boolean endsTurn() {
        return false;
    }
    
    @Override
    public String getDescription() {
        return "Shuffles the draw pile.";
    }
}

