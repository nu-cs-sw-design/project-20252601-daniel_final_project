package domain.game;

public class NopeAction implements CardAction {
    
    private Cancellable targetAction;
    
    public NopeAction() {
        this.targetAction = null;
    }
    
    public NopeAction(Cancellable targetAction) {
        this.targetAction = targetAction;
    }
    
    public void setTargetAction(Cancellable targetAction) {
        this.targetAction = targetAction;
    }
    
    public Cancellable getTargetAction() {
        return targetAction;
    }

    @Override
    public ActionResult execute(GameContext context) {
        if (targetAction == null) {
            return ActionResult.failure("No target action to cancel");
        }
        
        targetAction.cancel();
        return ActionResult.success("Action cancelled by Nope");
    }

    @Override
    public boolean canExecute(GameContext context) {
        return targetAction != null && !targetAction.isCancelled();
    }

    @Override
    public CardType getCardType() {
        return CardType.NOPE;
    }
}

