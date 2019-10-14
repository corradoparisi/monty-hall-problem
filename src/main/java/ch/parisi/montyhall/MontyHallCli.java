package ch.parisi.montyhall;

import java.util.Random;
import java.util.Scanner;

class MontyHallCli {

    //TODO implement random switching Strategy
    private boolean alwaysSwitching = true;
    private int simulations = 1000;
    private int numberOfDoors = 3;
    private Scanner scanner = new Scanner(System.in);


    void userConfiguration() {
        System.out.println("How many doors would you like?");
        numberOfDoors = scanner.nextInt();

        System.out.println("How many simulation runs would you like?");
        simulations = scanner.nextInt();

        System.out.println("Do you want to switch always?");
        alwaysSwitching = scanner.nextBoolean();
    }

    void startSimulations() {
        int numberOfWins = 0;
        int counter = 0;

        while (counter < simulations) {
            boolean hasWon = montyHall();
            if (hasWon) {
                numberOfWins++;
            }
            counter++;
        }

        System.out.println("On " + simulations + " attempts we won: " + numberOfWins + " times.");
    }

    private boolean montyHall() {
        if(numberOfDoors == 1)  {
            System.out.println("You won!");
            return true;
        }

        Boolean[] doors = new Boolean[numberOfDoors];

        for (int i = 0; i < numberOfDoors; i++) {
            doors[i] = false;
        }

        Random random = new Random();
        int winningDoorIndex = random.nextInt(numberOfDoors);
        System.out.println("The winning door index is = " + winningDoorIndex);

        doors[winningDoorIndex] = true;

        System.out.println("Welcome to Monty Hall");
        System.out.println("Pick a door you like");

        //generate first choice
        int firstChoiceIndex = random.nextInt(numberOfDoors);


        System.out.println("I've picked door with index " + firstChoiceIndex);

        if (firstChoiceIndex != winningDoorIndex) {
            //printAllExceptForWinningAndPick
            System.out.println("I show you all other false doors.");

            doors[winningDoorIndex] = null;
            doors[firstChoiceIndex] = null;
            for (int i = 0; i < numberOfDoors; i++) {
                if (doors[i] != null) {
                    System.out.println("Door with index " + i + " is " + false);
                }
            }

            System.out.println("Would you like to switch?");

            //switch
            if (alwaysSwitching) {
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
            if (firstChoiceIndex + 1 > (numberOfDoors - 1)) {
                doors[0] = null;
            } else {
                doors[firstChoiceIndex + 1] = null;
            }


            for (int i = 0; i < numberOfDoors; i++) {
                if (doors[i] != null) {
                    System.out.println("Door with index " + i + "is " + false);
                }
            }

            System.out.println("Would you like to switch?");
            if (alwaysSwitching) {
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
}

