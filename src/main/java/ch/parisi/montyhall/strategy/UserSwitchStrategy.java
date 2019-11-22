package ch.parisi.montyhall.strategy;

import java.util.Scanner;

public class UserSwitchStrategy implements SwitchStrategy {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public boolean isSwitching() {
        String userInput = scanner.next();
        return userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("true");
    }
}
