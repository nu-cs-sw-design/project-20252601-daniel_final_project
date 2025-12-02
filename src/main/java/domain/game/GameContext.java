package domain.game;

public class GameContext {
    
    private Game game;
    private Player currentPlayer;
    private Deck deck;
    private int currentPlayerIndex;
    
    public GameContext(Game game, Player currentPlayer, Deck deck, int currentPlayerIndex) {
        this.game = game;
        this.currentPlayer = currentPlayer;
        this.deck = deck;
        this.currentPlayerIndex = currentPlayerIndex;
    }
    
    public Game getGame() {
        return game;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Deck getDeck() {
        return deck;
    }
    
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    
    public Player getPlayerAtIndex(int playerIndex) {
        return game.getPlayerAtIndex(playerIndex);
    }
    
    public int getNumberOfPlayers() {
        return game.getNumberOfPlayers();
    }
}
