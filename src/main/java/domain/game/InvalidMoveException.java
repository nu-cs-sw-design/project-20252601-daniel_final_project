package domain.game;

public class InvalidMoveException extends GameException {
    
    public InvalidMoveException(String message) {
        super(message);
    }
}

