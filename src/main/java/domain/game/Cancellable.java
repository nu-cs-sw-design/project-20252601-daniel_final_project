package domain.game;

public interface Cancellable {
    void cancel();
    boolean isCancelled();
}
