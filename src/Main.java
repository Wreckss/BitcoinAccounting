import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PriceData priceData = new PriceData();
        try {
            priceData.writePriceFile(priceData.addPriceLabel(priceData.formattedSpotPrice));
            priceData.writePriceFile(priceData.addSatsPerDollarLabel(priceData.formattedSats));
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
