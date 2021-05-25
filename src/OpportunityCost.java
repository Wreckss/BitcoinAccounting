import java.util.Scanner;

public class OpportunityCost extends PriceData {
    private final Scanner scanner = new Scanner(System.in);

    private String askProductName() {
        System.out.println("Enter the product name you're considering purchasing:");
        return scanner.next().toLowerCase();
    }

    public double askProductPrice() {
        final String[] feedback = {
                String.format("Enter the price of the %s:", askProductName()),
                "$",
                "Submit a number"
        };
        System.out.println(feedback[0]);
        System.out.print(feedback[1]);
        while (!scanner.hasNextDouble()) {
            System.out.println(feedback[2]);
            System.out.print(feedback[1]);
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
        final String[] feedback = {
                "\n1. Run",
                "2. Quit",
                "Use 1 or 2 as an input",
                "Quitting.."
        };

        do {
            System.out.println(feedback[0]);
            System.out.println(feedback[1]);
            while (!scanner.hasNextInt()) {
                System.out.println(feedback[2]);
                System.out.println(feedback[0]);
                System.out.println(feedback[1]);
                scanner.next();
            }
            switch (scanner.nextInt()) {
                // run
                case 1 ->  validAnswer = true;
                // quit
                case 2 -> {
                    System.out.println(feedback[3]);
                    validAnswer = true;
                    quit = true;
                }
                default -> {
                    System.out.println(feedback[2]);
                    validAnswer = false;
                }
            }
        } while (!validAnswer);
        return quit;
    }

}
