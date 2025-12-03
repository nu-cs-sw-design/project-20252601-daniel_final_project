package domain.game;

public class DefuseAction implements CardAction {
    
    private int insertIndex;
    
    public DefuseAction() {
        this.insertIndex = 0;
    }
    
    public void setInsertIndex(int insertIndex) {
        this.insertIndex = insertIndex;
    }
    
    public int getInsertIndex() {
        return insertIndex;
    }

    @Override
    public ActionResult execute(GameContext context) {
        Player currentPlayer = context.getCurrentPlayer();
        Deck deck = context.getDeck();
        
        if (!currentPlayer.hasCard(CardType.DEFUSE)) {
            return ActionResult.failure("Player does not have a Defuse card");
        }
        
        int defuseIdx = currentPlayer.getIndexOfCard(CardType.DEFUSE);
        currentPlayer.removeCardFromHand(defuseIdx);
        
        deck.insertExplodingKittenAtIndex(insertIndex);
        
        return ActionResult.success("Defuse played - Exploding Kitten inserted back into deck");
    }

    @Override
    public boolean canExecute(GameContext context) {
        return context.getCurrentPlayer().hasCard(CardType.DEFUSE);
    }

    @Override
    public CardType getCardType() {
        return CardType.DEFUSE;
    }
}

