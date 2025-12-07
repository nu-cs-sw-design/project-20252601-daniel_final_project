package domain.game.behaviors;

public class CardResult {
    
    private final boolean success;
    private final String message;
    private final boolean playerEliminated;
    
    public CardResult(boolean success, String message) {
        this(success, message, false);
    }
    
    public CardResult(boolean success, String message, boolean playerEliminated) {
        this.success = success;
        this.message = message;
        this.playerEliminated = playerEliminated;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public boolean isPlayerEliminated() {
        return playerEliminated;
    }
    
    public static CardResult success(String message) {
        return new CardResult(true, message);
    }
    
    public static CardResult failure(String message) {
        return new CardResult(false, message);
    }
    
    public static CardResult eliminated(String message) {
        return new CardResult(false, message, true);
    }
}

