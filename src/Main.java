import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        OpportunityCost oCost = new OpportunityCost();
        int opportunityCost;
        double productPrice;
        try {
            oCost.writePriceFile(oCost.labeledData);
        } catch (IOException io) {
            io.printStackTrace();
        }
        do {
            productPrice = oCost.askProductPrice();
            opportunityCost = oCost.calculateOpportunityCost(productPrice);
            oCost.displayOpportunityCost(opportunityCost);
            oCost.calculateFutureCost(opportunityCost);
        } while (!oCost.askQuit());
    }
}
