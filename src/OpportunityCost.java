import java.util.Scanner;

public class OpportunityCost extends PriceData {
    private final Scanner scanner = new Scanner(System.in);
    private final String productName = askProductName();
    public double productPrice = askProductPrice(productName);
    public int opportunityCost = calculateOpportunityCost(productPrice);
    public String labeledCost = labelOpportunityCost(opportunityCost);


    public String askProductName() {
        System.out.println("Enter the product name you're considering purchasing:");
        return scanner.next();
    }

    public double askProductPrice(String productName) {
        System.out.printf("Enter the price of the %s:\n", productName);
        return scanner.nextDouble();
    }

    public int calculateOpportunityCost(double productPrice) {
        return (int) productPrice * satsPerDollar;
    }

    public String labelOpportunityCost(int cost) {
        return String.format("The opportunity cost of this %s is %s!", productName, formatSats(cost));
    }

    public void calculateFutureCost(int opportunityCost) {
        final double annualGrowth = 2.135;      //213.5% annually
        double futureSpot;
        double[] projections;
        int userAnswer;

        System.out.println("How many years would you like to project?");
        userAnswer = scanner.nextInt();
        projections = new double[userAnswer];

        for (int i = 0; i < projections.length; i++) {
            futureSpot = bitcoinSpotPrice * annualGrowth;
            projections[i] = opportunityCost / (double) satsPerDollar(futureSpot);
            bitcoinSpotPrice = futureSpot;
            System.out.printf("Year %s: %s\n", i+1, formatUSD(projections[i]));
        }
    }

}
