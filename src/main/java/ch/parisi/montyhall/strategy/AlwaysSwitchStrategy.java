package ch.parisi.montyhall.strategy;

public class AlwaysSwitchStrategy implements SwitchStrategy {
    @Override
    public boolean isSwitching() {
        return true;
    }
}
