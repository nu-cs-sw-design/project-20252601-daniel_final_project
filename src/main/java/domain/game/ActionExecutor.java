package domain.game;

public class ActionExecutor {

    public ActionResult execute(CardAction action, GameContext context) {
        if (!action.canExecute(context)) {
            return ActionResult.failure("Action cannot be executed");
        }
        return action.execute(context);
    }

    public void cancelAction(Cancellable action) {
        if (!action.isCancelled()) {
            action.cancel();
        }
    }
}

