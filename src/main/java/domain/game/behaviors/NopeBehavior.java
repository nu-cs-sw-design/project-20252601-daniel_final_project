package domain.game.behaviors;

import domain.game.Game;
import domain.game.Player;
import ui.GameUI;

public class NopeBehavior implements CardBehavior {
    
    @Override
    public CardResult execute(Game game, Player player, GameUI ui) {
        return CardResult.success("Nope! Action cancelled.");
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
        return "Cancels any action except Exploding Kitten or Defuse.";
    }
}

