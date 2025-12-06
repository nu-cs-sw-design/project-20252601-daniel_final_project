package controller;

import domain.game.*;
import view.GameUI;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== EXPLODING KITTENS ===");
        System.out.println("Enter number of players (2-4):");
        
        int numberOfPlayers = 2;
        try {
            numberOfPlayers = Integer.parseInt(scanner.nextLine().trim());
            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                numberOfPlayers = 2;
                System.out.println("Invalid number, defaulting to 2 players.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, defaulting to 2 players.");
        }
        
        Deck deck = new Deck();
        Game game = new Game(numberOfPlayers, deck);
        GameUI ui = new GameUI();
        GameController controller = new GameController(game, ui);
        
        controller.initialize();
        
        while (game.getGameState() != GameState.GAME_OVER) {
            GameState state = game.getGameState();
            
            switch (state) {
                case PLAYER_TURN:
                    ui.displayPlayerHand(game.getCurrentPlayer());
                    int action = ui.promptForAction();
                    if (action == 0) {
                        controller.handleDrawCard();
                    } else {
                        controller.handlePlayCard(action - 1);
                    }
                    break;
                    
                case NOPE_WINDOW:
                    boolean wantsToNope = ui.promptForNopeResponse();
                    controller.handleNopeResponse(wantsToNope);
                    break;
                    
                case EXPLOSION_PENDING:
                    int position = ui.promptForDefusePosition(deck.getSize());
                    controller.handleDefusePosition(position);
                    break;
                    
                default:
                    break;
            }
        }
        
        scanner.close();
    }
}

