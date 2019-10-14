package ch.parisi.montyhall;

import java.util.Random;
import java.util.Scanner;

class MontyHallCli {
    private MontyHallCli() {
    }

    static void askAndStartSimulation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many doors would you like?");
        int numberOfDoors = ExOptional.untilSuccess(scanner::nextInt);
        System.out.println("How many simulation runs would you like?");
        int simulations = ExOptional.untilSuccess(scanner::nextInt);
        System.out.println("Do you want to switch always?");
        boolean alwaysSwitching = ExOptional.untilSuccess(scanner::nextBoolean);
        startSimulations(new Options(alwaysSwitching, simulations, numberOfDoors));
    }

    static void startSimulations(Options options) {
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

    private static boolean montyHall(Options options) {
        if (options.numberOfDoors == 1) {
            System.out.println("You won!");
            return true;
        }

        Boolean[] doors = new Boolean[options.numberOfDoors];

        for (int i = 0; i < options.numberOfDoors; i++) {
            doors[i] = false;
        }

        Random random = new Random();
        int winningDoorIndex = random.nextInt(options.numberOfDoors);
        System.out.println("The winning door index is = " + winningDoorIndex);

        doors[winningDoorIndex] = true;

        System.out.println("Welcome to Monty Hall");
        System.out.println("Pick a door you like");

        //generate first choice
        int firstChoiceIndex = random.nextInt(options.numberOfDoors);


        System.out.println("I've picked door with index " + firstChoiceIndex);

        if (firstChoiceIndex != winningDoorIndex) {
            //printAllExceptForWinningAndPick
            System.out.println("I show you all other false doors.");

            doors[winningDoorIndex] = null;
            doors[firstChoiceIndex] = null;
            for (int i = 0; i < options.numberOfDoors; i++) {
                if (doors[i] != null) {
                    System.out.println("Door with index " + i + " is " + false);
                }
            }

            System.out.println("Would you like to switch?");

            //switch
            if (options.alwaysSwitching) {
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
            if (options.alwaysSwitching) {
                System.out.println("Yes I do");
                System.out.println("Your initial choice was changed to " + (firstChoiceIndex + 1));
                System.out.println("annnd youuuu ....");
                System.out.println("LOST!");
                return false;

            } else {
                System.out.println("No I don't");
                System.out.println("annnd youuuu ....");
                System.out.println("WON!");
                return true;
            }
        }
    }

    private static class Options {
        private final boolean alwaysSwitching;
        private final int simulations;
        private final int numberOfDoors;

        private Options(boolean alwaysSwitching, int simulations, int numberOfDoors) {
            this.alwaysSwitching = alwaysSwitching;
            this.simulations = simulations;
            this.numberOfDoors = numberOfDoors;
        }
    }
}
