import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        OpportunityCost oCost = new OpportunityCost();
        int opportunityCost;
        try {
            oCost.writePriceFile(oCost.labeledData);
        } catch (IOException io) {
            io.printStackTrace();
        }
        while (!oCost.askQuit()) {
            opportunityCost = oCost.calculateOpportunityCost(oCost.askProductPrice());
            oCost.displayOpportunityCost(opportunityCost);
            oCost.calculateFutureCost(opportunityCost);
        }
    }
}
