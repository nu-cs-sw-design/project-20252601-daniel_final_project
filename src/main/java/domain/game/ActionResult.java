package domain.game;

public class ActionResult {
    
    private boolean success;
    private String message;
    private boolean cancelled;
    
    public ActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.cancelled = false;
    }
    
    public static ActionResult success(String message) {
        return new ActionResult(true, message);
    }
    
    public static ActionResult failure(String message) {
        return new ActionResult(false, message);
    }
    
    public static ActionResult cancelled(String message) {
        ActionResult result = new ActionResult(false, message);
        result.setCancelled(true);
        return result;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
