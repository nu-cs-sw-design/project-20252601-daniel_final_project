package domain.game;

public class ShuffleAction implements CardAction {

    @Override
    public void execute(GameContext context) {
        Deck deck = context.getDeck();
        deck.shuffleDeck();
    }

    @Override
    public boolean isNopeable() {
        return true;
    }

    @Override
    public CardType getCardType() {
        return CardType.SHUFFLE;
    }
}
