import java.util.Scanner;

public class OpportunityCost extends PriceData {
    private Scanner scanner = new Scanner(System.in);
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

    public void calculateFutureCost(double productPrice) {
        final double annualGrowth = 2.135;      //213.5% annually

        System.out.println("How many years would you like to project?");
        int answer = scanner.nextInt();
        double[] projections = new double[answer];
        for (int i = 0; i < projections.length; i++) {
            projections[i] = productPrice * annualGrowth;
            productPrice = projections[i];
            System.out.printf("Year %s: %s\n", i+1, formatUSD(projections[i]));
        }
    }

}
