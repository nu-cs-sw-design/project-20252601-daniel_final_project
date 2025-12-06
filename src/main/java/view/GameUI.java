package view;

import domain.game.*;
import controller.GameController;
import java.util.Scanner;
import java.util.List;

public class GameUI implements GameObserver {
    
    private GameController controller;
    private Scanner scanner;
    
    public GameUI() {
        this.scanner = new Scanner(System.in);
    }
    
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    @Override
    public void onTurnStarted(int playerIndex) {
        System.out.println("\n========================================");
        System.out.println("Player " + (playerIndex + 1) + "'s turn");
        System.out.println("========================================");
    }
    
    @Override
    public void onCardPlayed(int playerIndex, CardType cardType) {
        System.out.println("Player " + (playerIndex + 1) + " played: " + cardType);
    }
    
    @Override
    public void onNopeWindowOpened(int sourcePlayerIndex, CardType cardType) {
        System.out.println("\n*** NOPE WINDOW ***");
        System.out.println("Player " + (sourcePlayerIndex + 1) + " is playing " + cardType);
        System.out.println("Any player can play a NOPE card!");
    }
    
    @Override
    public void onActionResolved(String message) {
        System.out.println("Action resolved: " + message);
    }
    
    @Override
    public void onActionCancelled() {
        System.out.println("*** Action was NOPED! ***");
    }
    
    @Override
    public void onExplosionTriggered(int playerIndex) {
        System.out.println("\n!!! BOOM !!!");
        System.out.println("Player " + (playerIndex + 1) + " drew an EXPLODING KITTEN!");
    }
    
    @Override
    public void onDefuseUsed(int playerIndex) {
        System.out.println("Player " + (playerIndex + 1) + " used DEFUSE!");
        System.out.println("The Exploding Kitten is being put back in the deck...");
    }
    
    @Override
    public void onPlayerEliminated(int playerIndex) {
        System.out.println("\n*** Player " + (playerIndex + 1) + " has been ELIMINATED! ***");
    }
    
    @Override
    public void onGameOver(int winnerIndex) {
        System.out.println("\n========================================");
        System.out.println("GAME OVER!");
        System.out.println("Player " + (winnerIndex + 1) + " WINS!");
        System.out.println("========================================");
    }
    
    @Override
    public void onError(String message) {
        System.out.println("Error: " + message);
    }
    
    public void displayPlayerHand(Player player) {
        System.out.println("\nYour hand:");
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + hand.get(i).getType());
        }
        System.out.println();
    }
    
    public int promptForAction() {
        System.out.println("Choose action:");
        System.out.println("  0. Draw a card");
        System.out.println("  1-" + getHandSize() + ". Play a card from your hand");
        System.out.print("> ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public boolean promptForNopeResponse() {
        System.out.println("Do you want to play a NOPE? (y/n)");
        System.out.print("> ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
    
    public int promptForDefusePosition(int maxPosition) {
        System.out.println("Where do you want to put the Exploding Kitten back?");
        System.out.println("Enter position (0 = bottom, " + maxPosition + " = top):");
        System.out.print("> ");
        
        try {
            int pos = Integer.parseInt(scanner.nextLine().trim());
            if (pos < 0) pos = 0;
            if (pos > maxPosition) pos = maxPosition;
            return pos;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private int getHandSize() {
        if (controller != null && controller.getGame() != null) {
            return controller.getGame().getCurrentPlayer().getHandSize();
        }
        return 0;
    }
}

