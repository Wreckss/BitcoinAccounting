import java.util.Scanner;

public class OpportunityCost extends PriceData {
    public final Scanner scanner = new Scanner(System.in);

    public String askProductName() {
        System.out.println("Enter the product name you're considering purchasing:");
        return scanner.next().toLowerCase();
    }

    public double askProductPrice() {
        System.out.printf("Enter the price of the %s:\n", askProductName());
        System.out.print("$");
        while (!scanner.hasNextDouble()) {
            System.out.println("Submit a number");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    public int calculateOpportunityCost(double productPrice) {
        return (int) productPrice * satsPerDollar;
    }

    public void displayOpportunityCost(int opportunityCost) {
        System.out.printf("The opportunity cost of this purchase is %s\n", formatSats(opportunityCost));
    }

    public void calculateFutureCost(int opportunityCost) {
        final double ANNUAL_GROWTH = 2.1382;      //213.82% annually
        double originalSpot = bitcoinSpotPrice;
        double futureSpot;
        double[] projections;

        System.out.println("How many years out would you like to project this purchase?");
        while (!scanner.hasNextInt()) {
            System.out.println("Submit a number");
            scanner.next();
        }
        projections = new double[scanner.nextInt()];

        for (int i = 0; i < projections.length; i++) {
            futureSpot = originalSpot * ANNUAL_GROWTH;
            originalSpot = futureSpot;
            projections[i] = opportunityCost / (double) satsPerDollar(futureSpot);
            System.out.printf("Year\s%s:\s%s\n", i + 1, formatUSD(projections[i]));
        }
    }

    public boolean askQuit() {
        boolean validAnswer;
        boolean quit = false;

        do {
            System.out.println("1. To run again");
            System.out.println("2. To quit");
            while (!scanner.hasNextInt()) {
                System.out.println("Submit a number");
                scanner.next();
            }
            switch (scanner.nextInt()) {
                case 1 ->  validAnswer = true;
                case 2 -> {
                    System.out.println("Quitting..");
                    quit = true;
                    validAnswer = true;
                }
                default -> {
                    System.out.println("Use 1 or 2 for inputs");
                    validAnswer = false;
                }
            }
        } while (!validAnswer);
        return quit;
    }

}
