package ch.parisi.montyhall.strategy;

public interface SwitchStrategy {
    /**
     * Returns whether a switch was made.
     * @return {@code true} if switched, {@code false} otherwise
     */
    boolean isSwitching();
}
