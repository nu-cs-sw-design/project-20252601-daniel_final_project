package domain.game;

public class ShuffleAction implements CardAction, Cancellable {
    
    private boolean cancelled;
    private int numberOfShuffles;
    
    public ShuffleAction() {
        this.cancelled = false;
        this.numberOfShuffles = 1;
    }
    
    public void setNumberOfShuffles(int numberOfShuffles) {
        this.numberOfShuffles = numberOfShuffles;
    }

    @Override
    public ActionResult execute(GameContext context) {
        if (cancelled) {
            return ActionResult.cancelled("Shuffle was cancelled by Nope");
        }
        
        Deck deck = context.getDeck();
        for (int i = 0; i < numberOfShuffles; i++) {
            deck.shuffleDeck();
        }
        
        return ActionResult.success("Deck shuffled");
    }

    @Override
    public boolean canExecute(GameContext context) {
        return !cancelled;
    }

    @Override
    public CardType getCardType() {
        return CardType.SHUFFLE;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}

