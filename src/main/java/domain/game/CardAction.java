package domain.game;

public interface CardAction {
    void execute(GameContext context);
    boolean isNopeable();
    CardType getCardType();
}
