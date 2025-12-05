package domain.game;

public interface GameObserver {
    void onTurnStarted(int playerIndex);
    void onCardPlayed(int playerIndex, CardType cardType);
    void onNopeWindowOpened(int sourcePlayerIndex, CardType cardType);
    void onActionResolved(String message);
    void onActionCancelled();
    void onExplosionTriggered(int playerIndex);
    void onDefuseUsed(int playerIndex);
    void onPlayerEliminated(int playerIndex);
    void onGameOver(int winnerIndex);
    void onError(String message);
}

