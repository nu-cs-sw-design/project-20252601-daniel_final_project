package controller;

import domain.game.*;
import view.GameUI;

public class GameController {
    
    private Game game;
    private GameUI ui;
    
    public GameController(Game game, GameUI ui) {
        this.game = game;
        this.ui = ui;
        ui.setController(this);
        game.addObserver(ui);
    }
    
    public void initialize() {
        game.start();
    }
    
    public void handleDrawCard() {
        if (game.getGameState() == GameState.PLAYER_TURN) {
            game.drawCard();
            processGameState();
        }
    }
    
    public void handlePlayCard(int cardIndex) {
        if (game.getGameState() == GameState.PLAYER_TURN) {
            game.playCard(cardIndex);
            processGameState();
        }
    }
    
    public void handleNopeResponse(boolean wantsToNope) {
        if (game.getGameState() == GameState.NOPE_WINDOW) {
            game.resolveNopeResponse(wantsToNope);
            processGameState();
        }
    }
    
    public void handleDefusePosition(int position) {
        if (game.getGameState() == GameState.EXPLOSION_PENDING) {
            game.resolveDefusePosition(position);
            processGameState();
        }
    }
    
    private void processGameState() {
        GameState state = game.getGameState();
        switch (state) {
            case PLAYER_TURN:
                break;
            case NOPE_WINDOW:
                break;
            case EXPLOSION_PENDING:
                break;
            case GAME_OVER:
                break;
            default:
                break;
        }
    }
    
    public Game getGame() {
        return game;
    }
}

