package ch.parisi.montyhall.strategy;

public class NeverSwitchStrategy implements SwitchStrategy{
    @Override
    public boolean isSwitching() {
        return false;
    }
}
