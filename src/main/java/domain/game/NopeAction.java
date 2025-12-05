package domain.game;

public class NopeAction implements CardAction {

    @Override
    public void execute(GameContext context) {
        // Nope's effect is handled by PendingAction.addNope()
        // This execute method is called but the actual cancellation
        // logic is in PendingAction.isNoped() and resolve()
    }

    @Override
    public boolean isNopeable() {
        return true;
    }

    @Override
    public CardType getCardType() {
        return CardType.NOPE;
    }
}
