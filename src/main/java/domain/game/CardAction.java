package domain.game;

public interface CardAction {
    ActionResult execute(GameContext context);
    boolean canExecute(GameContext context);
    CardType getCardType();
}
