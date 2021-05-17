import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PriceData {
    public float bitcoinSpotPrice = btcPriceCheck();
    public String formattedSpotPrice = formatUSD(bitcoinSpotPrice);
    public int satsPerDollar = satsPerDollar();
    public String formattedSats = formatSats(satsPerDollar);

    private float btcPriceCheck() {
        String urlString = "https://api.coindesk.com/v1/bpi/currentprice.json";
        StringBuilder jsonString = new StringBuilder();
        try {
            URL url = new URL(urlString);
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                jsonString.append(scanner.next());
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(jsonString.toString());
        JSONObject bitcoinPriceIndex = jsonObject.getJSONObject("bpi");
        JSONObject usdPrice = bitcoinPriceIndex.getJSONObject("USD");

        return usdPrice.getFloat("rate_float");
    }

    private int satsPerDollar() {
        final int SATS_PER_BITCOIN = 100_000_000;
        return (int) (SATS_PER_BITCOIN / bitcoinSpotPrice);
    }

    private String formatSats(int sats) {
        final String UNIT = " sats";
        //used for adding a comma to output
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        return formatter.format(sats) + UNIT;
    }

    private String formatUSD(float price) {
        //add dollar sign to output
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(price);
    }

    public String addPriceLabel(String price) {
        final String priceLabel = "Bitcoin price: ";
        return priceLabel + price;
    }

    public String addSatsPerDollarLabel(String sats) {
        final String satsPerDollarLabel = "Sats per dollar: ";
        return satsPerDollarLabel + sats;
    }

    public void writePriceFile(String priceData) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("priceDataFile", true));
        bufferedWriter.write(getDate() + getTime() + priceData);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public String getDate() {
        return java.time.LocalDate.now() + ",\s";
    }

    public String getTime() {
        String timeFormat = "HH:mm:ss a";
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
        Date date = new Date();

        return formatter.format(date) + ",\s";
    }


}
