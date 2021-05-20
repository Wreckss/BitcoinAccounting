import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PriceData priceData = new PriceData();
        OpportunityCost oCost = new OpportunityCost();

        try {
            priceData.writePriceFile(priceData.labeledData);
        } catch (IOException io) {
            io.printStackTrace();
        }
        System.out.println(oCost.labeledCost);
        oCost.calculateFutureCost(oCost.productPrice);
    }
}
