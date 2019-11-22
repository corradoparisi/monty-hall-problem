package ch.parisi.montyhall;

import ch.parisi.montyhall.strategy.*;

import java.util.Random;
import java.util.Scanner;

class MontyHallCli {

    private Random random = new Random();

    void montyHallCli() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many doors would you like?");
        int numberOfDoors = ExOptional.untilSuccess(scanner::nextInt);

        System.out.println("How many simulation runs would you like?");
        int simulations = ExOptional.untilSuccess(scanner::nextInt);

        System.out.println("Which game mode would you like?");
        for (GameStrategy value : GameStrategy.values()) {
            System.out.println(value);
        }

        SwitchStrategy switchStrategy = ExOptional.untilSuccess(() -> {
            String mode = scanner.next();
            GameStrategy gameStrategy = GameStrategy.valueOf(mode.toUpperCase());
            switch (gameStrategy) {
                case ALWAYS:
                    return new AlwaysSwitchStrategy();
                case NEVER:
                    return new NeverSwitchStrategy();
                case RANDOM:
                    return new RandomSwitchStrategy();
                case USER:
                    return new UserSwitchStrategy();
                default:
                    throw new IllegalArgumentException("Game strategy " + mode + " does not exist.");
            }
        });

        startSimulations(new Options(switchStrategy, simulations, numberOfDoors));
    }

    void startSimulations(Options options) {
        int numberOfWins = 0;
        int counter = 0;

        while (counter < options.simulations) {
            boolean hasWon = montyHall(options);
            if (hasWon) {
                numberOfWins++;
            }
            counter++;
        }

        System.out.println("On " + options.simulations + " attempts we won: " + numberOfWins + " times.");
    }

    private boolean montyHall(Options options) {
        if (isImmediateWin(options.numberOfDoors)) {
            System.out.println("You won!");
            return true;
        }

        Boolean[] doors = initDoors(options.numberOfDoors);

        int winningDoorIndex = winningDoor(options, doors);
        System.out.println("The winning door index is = " + winningDoorIndex);

        return startSimulation(options, doors, winningDoorIndex);
    }

    private boolean startSimulation(Options options, Boolean[] doors, int winningDoorIndex) {
        System.out.println("Welcome to Monty Hall");
        System.out.println("Pick a door of your choice");

        int firstChoiceIndex = random.nextInt(options.numberOfDoors);
        System.out.println("I've picked the door with index " + firstChoiceIndex);

        if (firstChoiceIndex != winningDoorIndex) {
            System.out.println("I show you all other false doors."); //TODO improve msg.

            doors[winningDoorIndex] = null;
            doors[firstChoiceIndex] = null;
            for (int i = 0; i < options.numberOfDoors; i++) {
                if (doors[i] != null) {
                    System.out.println("Door with index " + i + " is " + false);
                }
            }

            System.out.println("Would you like to switch?");

            //switch
            if (options.switchStrategy.isSwitching()) {
                System.out.println("Yes I do");
                firstChoiceIndex = winningDoorIndex;
                System.out.println("Your initial choice was changed to " + firstChoiceIndex);
                System.out.println("annnd youuuu ....");
                System.out.println("WON!");
                return true;
            } else {
                System.out.println("No I don't");
                System.out.println("annnd youuuu ....");
                System.out.println("LOST!");
                return false;
            }

        } else {
            doors[firstChoiceIndex] = null;
            if (firstChoiceIndex + 1 > (options.numberOfDoors - 1)) {
                doors[0] = null;
            } else {
                doors[firstChoiceIndex + 1] = null;
            }


            for (int i = 0; i < options.numberOfDoors; i++) {
                if (doors[i] != null) {
                    System.out.println("Door with index " + i + "is " + false);
                }
            }

            System.out.println("Would you like to switch?");

            if (options.switchStrategy.isSwitching()) {
                System.out.println("Yes I do");
                System.out.println("Your initial choice was changed to " + (firstChoiceIndex + 1));
                System.out.println("and ... you lost.");
                return false;

            } else {
                System.out.println("No I don't");
                System.out.println("and ... you won.");
                return true;
            }
        }
    }

    private int winningDoor(Options options, Boolean[] doors) {
        int winningDoorIndex = random.nextInt(options.numberOfDoors);
        doors[winningDoorIndex] = true;
        return winningDoorIndex;
    }

    private Boolean[] initDoors(int numberOfDoors) {
        Boolean[] doors = new Boolean[numberOfDoors];

        for (int i = 0; i < numberOfDoors; i++) {
            doors[i] = false;
        }

        return doors;
    }

    private boolean isImmediateWin(int numberOfDoors) {
        return numberOfDoors == 1;
    }

    private static class Options {
        private final SwitchStrategy switchStrategy;
        private final int simulations;
        private final int numberOfDoors;

        private Options(SwitchStrategy switchStrategy, int simulations, int numberOfDoors) {
            this.switchStrategy = switchStrategy;
            this.simulations = simulations;
            this.numberOfDoors = numberOfDoors;
        }
    }
}

