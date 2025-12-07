package domain.game.behaviors;

import domain.game.Game;
import domain.game.Player;
import ui.GameUI;

public interface CardBehavior {
    
    CardResult execute(Game game, Player player, GameUI ui);
    
    boolean canBeNoped();
    
    boolean endsTurn();
    
    String getDescription();
}

