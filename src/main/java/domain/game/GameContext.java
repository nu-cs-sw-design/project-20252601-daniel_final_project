package domain.game;

public interface GameContext {
    Deck getDeck();
    Player getCurrentPlayer();
    Player getPlayerAtIndex(int index);
    int getNumberOfPlayers();
    int getCurrentPlayerIndex();
    GameState getGameState();
}
