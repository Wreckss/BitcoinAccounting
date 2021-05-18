import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PriceData priceData = new PriceData();
        try {
            priceData.writePriceFile(priceData.labeledData);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
