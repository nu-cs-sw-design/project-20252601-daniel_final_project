package domain.game;

public class ExplodingKittenAction implements CardAction {

    @Override
    public ActionResult execute(GameContext context) {
        Player currentPlayer = context.getCurrentPlayer();
        int playerIndex = context.getCurrentPlayerIndex();
        
        if (currentPlayer.hasCard(CardType.DEFUSE)) {
            return ActionResult.success("Player has Defuse card - explosion prevented");
        }
        
        currentPlayer.setIsDead();
        return ActionResult.success("Player exploded and is out of the game");
    }

    @Override
    public boolean canExecute(GameContext context) {
        return true;
    }

    @Override
    public CardType getCardType() {
        return CardType.EXPLODING_KITTEN;
    }
}

