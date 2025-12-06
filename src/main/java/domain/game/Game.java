package domain.game;

import view.GameObserver;
import java.util.List;
import java.util.ArrayList;

public class Game implements GameContext {
    
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private GameState state;
    private TurnManager turnManager;
    private List<GameObserver> observers;
    private PendingAction pendingAction;
    
    public Game(int numberOfPlayers, Deck deck) {
        this.players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(i));
        }
        this.deck = deck;
        this.currentPlayerIndex = 0;
        this.state = GameState.SETUP;
        this.turnManager = new TurnManager(numberOfPlayers);
        this.observers = new ArrayList<>();
        this.pendingAction = null;
    }
    
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyTurnStarted(int playerIndex) {
        for (GameObserver observer : observers) {
            observer.onTurnStarted(playerIndex);
        }
    }
    
    private void notifyCardPlayed(int playerIndex, CardType cardType) {
        for (GameObserver observer : observers) {
            observer.onCardPlayed(playerIndex, cardType);
        }
    }
    
    private void notifyNopeWindow(int playerIndex, CardType cardType) {
        for (GameObserver observer : observers) {
            observer.onNopeWindowOpened(playerIndex, cardType);
        }
    }
    
    private void notifyActionResolved(String message) {
        for (GameObserver observer : observers) {
            observer.onActionResolved(message);
        }
    }
    
    private void notifyActionCancelled() {
        for (GameObserver observer : observers) {
            observer.onActionCancelled();
        }
    }
    
    private void notifyExplosion(int playerIndex) {
        for (GameObserver observer : observers) {
            observer.onExplosionTriggered(playerIndex);
        }
    }
    
    private void notifyDefuseUsed(int playerIndex) {
        for (GameObserver observer : observers) {
            observer.onDefuseUsed(playerIndex);
        }
    }
    
    private void notifyPlayerEliminated(int playerIndex) {
        for (GameObserver observer : observers) {
            observer.onPlayerEliminated(playerIndex);
        }
    }
    
    private void notifyGameOver(int winnerIndex) {
        for (GameObserver observer : observers) {
            observer.onGameOver(winnerIndex);
        }
    }
    
    private void notifyError(String message) {
        for (GameObserver observer : observers) {
            observer.onError(message);
        }
    }
    
    @Override
    public Deck getDeck() {
        return deck;
    }
    
    @Override
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    @Override
    public Player getPlayerAtIndex(int index) {
        return players.get(index);
    }
    
    @Override
    public int getNumberOfPlayers() {
        return players.size();
    }
    
    @Override
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    
    @Override
    public GameState getGameState() {
        return state;
    }
    
    public void setGameState(GameState state) {
        this.state = state;
    }
    
    public PendingAction getPendingAction() {
        return pendingAction;
    }
    
    public void setPendingAction(PendingAction action) {
        this.pendingAction = action;
    }
    
    public void start() {
        deck.initialize(players.size());
        deck.shuffle();
        
        for (Player player : players) {
            player.addCard(CardFactory.createDefuse());
            for (int i = 0; i < 4; i++) {
                if (!deck.isEmpty()) {
                    player.addCard(deck.draw());
                }
            }
        }
        
        for (int i = 0; i < players.size() - 1; i++) {
            deck.insertAt(0, CardFactory.createExplodingKitten());
        }
        deck.shuffle();
        
        state = GameState.PLAYER_TURN;
        notifyTurnStarted(currentPlayerIndex);
    }
    
    public void drawCard() {
        if (state != GameState.PLAYER_TURN) {
            notifyError("Cannot draw card now");
            return;
        }
        
        if (deck.isEmpty()) {
            throw new EmptyDeckException();
        }
        
        Card drawnCard = deck.draw();
        Player player = getCurrentPlayer();
        
        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            notifyExplosion(currentPlayerIndex);
            handleExplosion();
        } else {
            player.addCard(drawnCard);
            advanceTurn();
        }
    }
    
    public void playCard(int cardIndex) {
        if (state != GameState.PLAYER_TURN) {
            notifyError("Cannot play card now");
            return;
        }
        
        Player player = getCurrentPlayer();
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            notifyError("Invalid card index");
            return;
        }
        
        Card card = player.removeCard(cardIndex);
        CardAction action = card.getAction();
        
        notifyCardPlayed(currentPlayerIndex, card.getType());
        
        if (action.isNopeable()) {
            pendingAction = new PendingAction(action, currentPlayerIndex);
            state = GameState.NOPE_WINDOW;
            notifyNopeWindow(currentPlayerIndex, card.getType());
        } else {
            action.execute(this);
            notifyActionResolved(card.getType() + " played successfully");
        }
    }
    
    public void resolveNopeResponse(boolean wantsToNope) {
        if (state != GameState.NOPE_WINDOW || pendingAction == null) {
            return;
        }
        
        if (wantsToNope) {
            Player currentNoper = null;
            for (Player p : players) {
                if (p.hasCardOfType(CardType.NOPE) && p.isAlive()) {
                    currentNoper = p;
                    break;
                }
            }
            
            if (currentNoper != null) {
                int nopeIndex = currentNoper.findCardOfType(CardType.NOPE);
                if (nopeIndex >= 0) {
                    currentNoper.removeCard(nopeIndex);
                    pendingAction.addNope();
                    notifyCardPlayed(currentNoper.getId(), CardType.NOPE);
                    return;
                }
            }
        }
        
        if (pendingAction.isNoped()) {
            notifyActionCancelled();
        } else {
            pendingAction.resolve(this);
            notifyActionResolved("Action completed");
        }
        
        pendingAction = null;
        state = GameState.PLAYER_TURN;
    }
    
    public void resolveDefusePosition(int position) {
        if (state != GameState.EXPLOSION_PENDING) {
            return;
        }
        
        Player player = getCurrentPlayer();
        if (player.hasCardOfType(CardType.DEFUSE)) {
            int defuseIndex = player.findCardOfType(CardType.DEFUSE);
            player.removeCard(defuseIndex);
            
            Card explodingKitten = CardFactory.createExplodingKitten();
            deck.insertAt(position, explodingKitten);
            
            notifyDefuseUsed(currentPlayerIndex);
            state = GameState.PLAYER_TURN;
            advanceTurn();
        } else {
            eliminatePlayer(currentPlayerIndex);
        }
    }
    
    public void advanceTurn() {
        turnManager.decrementTurns();
        
        if (turnManager.getTurnsRemaining() <= 0) {
            currentPlayerIndex = turnManager.advanceToNextPlayer(players);
            turnManager.setTurnsRemaining(1);
        }
        
        checkWinCondition();
        
        if (state != GameState.GAME_OVER) {
            notifyTurnStarted(currentPlayerIndex);
        }
    }
    
    public void eliminatePlayer(int playerIndex) {
        players.get(playerIndex).eliminate();
        notifyPlayerEliminated(playerIndex);
        
        checkWinCondition();
        
        if (state != GameState.GAME_OVER) {
            currentPlayerIndex = turnManager.advanceToNextPlayer(players);
            state = GameState.PLAYER_TURN;
            notifyTurnStarted(currentPlayerIndex);
        }
    }
    
    private void checkWinCondition() {
        int alivePlayers = 0;
        int lastAliveIndex = -1;
        
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isAlive()) {
                alivePlayers++;
                lastAliveIndex = i;
            }
        }
        
        if (alivePlayers == 1) {
            state = GameState.GAME_OVER;
            notifyGameOver(lastAliveIndex);
        }
    }
    
    private void handleExplosion() {
        Player player = getCurrentPlayer();
        
        if (player.hasCardOfType(CardType.DEFUSE)) {
            state = GameState.EXPLOSION_PENDING;
        } else {
            eliminatePlayer(currentPlayerIndex);
        }
    }
}
