package domain.game;

public class PendingAction {
    
    private final CardAction cardAction;
    private final int sourcePlayerIndex;
    private int nopeCount;
    private boolean resolved;
    
    public PendingAction(CardAction action, int playerIndex) {
        this.cardAction = action;
        this.sourcePlayerIndex = playerIndex;
        this.nopeCount = 0;
        this.resolved = false;
    }
    
    public CardAction getCardAction() {
        return cardAction;
    }
    
    public int getSourcePlayerIndex() {
        return sourcePlayerIndex;
    }
    
    public int getNopeCount() {
        return nopeCount;
    }
    
    public void addNope() {
        nopeCount++;
    }
    
    public boolean isNoped() {
        return nopeCount % 2 == 1;
    }
    
    public void resolve(GameContext context) {
        if (!resolved && !isNoped()) {
            cardAction.execute(context);
        }
        resolved = true;
    }
    
    public boolean isResolved() {
        return resolved;
    }
}

