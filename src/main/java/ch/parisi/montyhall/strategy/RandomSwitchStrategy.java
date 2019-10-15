package ch.parisi.montyhall.strategy;

import java.util.Random;

public class RandomSwitchStrategy implements SwitchStrategy {

    private Random random = new Random();

    @Override
    public boolean isSwitching() {
        return random.nextBoolean();
    }
}
