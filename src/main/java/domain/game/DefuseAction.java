package domain.game;

public class DefuseAction implements CardAction {
    
    private int insertPosition;
    
    public DefuseAction() {
        this.insertPosition = 0;
    }
    
    public void setInsertPosition(int position) {
        this.insertPosition = position;
    }
    
    public int getInsertPosition() {
        return insertPosition;
    }

    @Override
    public void execute(GameContext context) {
        Player player = context.getCurrentPlayer();
        Deck deck = context.getDeck();
        
        int defuseIdx = player.getIndexOfCard(CardType.DEFUSE);
        player.removeCardFromHand(defuseIdx);
        
        deck.insertExplodingKittenAtIndex(insertPosition);
    }

    @Override
    public boolean isNopeable() {
        return false;
    }

    @Override
    public CardType getCardType() {
        return CardType.DEFUSE;
    }
}
