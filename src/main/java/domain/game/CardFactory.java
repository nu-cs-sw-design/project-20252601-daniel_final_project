package domain.game;

public class CardFactory {
    
    public static Card createCard(CardType type) {
        switch (type) {
            case EXPLODING_KITTEN:
                return createExplodingKitten();
            case DEFUSE:
                return createDefuse();
            case NOPE:
                return createNope();
            case SHUFFLE:
                return createShuffle();
            default:
                throw new IllegalArgumentException("Unknown card type: " + type);
        }
    }
    
    public static Card createExplodingKitten() {
        return new Card(CardType.EXPLODING_KITTEN, new ExplodingKittenAction());
    }
    
    public static Card createDefuse() {
        return new Card(CardType.DEFUSE, new DefuseAction());
    }
    
    public static Card createNope() {
        return new Card(CardType.NOPE, new NopeAction());
    }
    
    public static Card createShuffle() {
        return new Card(CardType.SHUFFLE, new ShuffleAction());
    }
}

