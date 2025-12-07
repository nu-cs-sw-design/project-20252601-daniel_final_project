package domain.game.behaviors;

import domain.game.CardType;

public final class CardBehaviorFactory {
    
    private CardBehaviorFactory() {
    }
    
    public static CardBehavior createBehavior(CardType cardType) {
        switch (cardType) {
            case SHUFFLE:
                return new ShuffleBehavior();
            case NOPE:
                return new NopeBehavior();
            case EXPLODING_KITTEN:
                return new ExplodingKittenBehavior();
            case DEFUSE:
                return new DefuseBehavior();
            default:
                return null;
        }
    }
    
    public static boolean hasBehavior(CardType cardType) {
        return cardType == CardType.SHUFFLE 
            || cardType == CardType.NOPE
            || cardType == CardType.EXPLODING_KITTEN
            || cardType == CardType.DEFUSE;
    }
}

