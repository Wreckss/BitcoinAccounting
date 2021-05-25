import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        OpportunityCost oCost;
        int opportunityCost;
        try {
            oCost = new OpportunityCost();
            oCost.writePriceFile(oCost.labeledData);
            while (!oCost.askQuit()) {
                opportunityCost = oCost.calculateOpportunityCost(oCost.askProductPrice());
                oCost.displayOpportunityCost(opportunityCost);
                oCost.calculateFutureCost(opportunityCost);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
