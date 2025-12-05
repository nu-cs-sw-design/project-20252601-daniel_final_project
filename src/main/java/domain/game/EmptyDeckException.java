package domain.game;

public class EmptyDeckException extends GameException {
    
    public EmptyDeckException() {
        super("Cannot draw from empty deck");
    }
}

