package domain.game.behaviors;

import domain.game.Game;
import domain.game.Player;

public interface CardBehavior {
    
    CardResult execute(Game game, Player player, UIHandler uiHandler);
    
    boolean canBeNoped();
    
    boolean endsTurn();
    
    String getDescription();
}
