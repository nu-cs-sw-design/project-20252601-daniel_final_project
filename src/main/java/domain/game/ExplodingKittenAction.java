package domain.game;

public class ExplodingKittenAction implements CardAction {

    @Override
    public void execute(GameContext context) {
        Player player = context.getCurrentPlayer();
        if (!player.hasCard(CardType.DEFUSE)) {
            player.setIsDead();
        }
    }

    @Override
    public boolean isNopeable() {
        return false;
    }

    @Override
    public CardType getCardType() {
        return CardType.EXPLODING_KITTEN;
    }
}
