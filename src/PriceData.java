import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class PriceData {
    final int SATS_PER_BITCOIN = 100_000_000;
    public double bitcoinSpotPrice = btcPriceCheck();
    public String formattedSpotPrice = formatUSD(bitcoinSpotPrice);
    public int satsPerDollar = satsPerDollar(bitcoinSpotPrice);
    public String formattedSats = formatSats(satsPerDollar);
    public String[] labeledData = {
            addPriceLabel(formattedSpotPrice),
            addSatsPerDollarLabel(formattedSats)
    };

    private float btcPriceCheck() {
        final String[] JSON_DATA = {
                "https://api.coindesk.com/v1/bpi/currentprice.json",
                "bpi",
                "USD",
                "rate_float"
        };
        StringBuilder jsonString = new StringBuilder();

        try {
            final URL url = new URL(JSON_DATA[0]);
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                jsonString.append(scanner.next());
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(jsonString.toString());
        JSONObject bitcoinPriceIndex = jsonObject.getJSONObject(JSON_DATA[1]);
        JSONObject usdPrice = bitcoinPriceIndex.getJSONObject(JSON_DATA[2]);

        return usdPrice.getFloat(JSON_DATA[3]);
    }

    public int satsPerDollar(double price) {
        return (int) (SATS_PER_BITCOIN / price);
    }

    public String formatSats(int amount) {
        //used for adding a comma to output
        final NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        final String UNIT_LABEL;

        if (amount > SATS_PER_BITCOIN) {
            UNIT_LABEL = "\sBTC";
            return ((double) amount / SATS_PER_BITCOIN) + UNIT_LABEL;
        } else {
            UNIT_LABEL = "\ssats";
            return formatter.format(amount) + UNIT_LABEL;
        }
    }

    public String formatUSD(double price) {
        //add dollar sign to output
        final NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return usdFormatter.format(price);
    }

    public String addPriceLabel(String price) {
        final String PRICE_LABEL = "Bitcoin price:\s";
        return PRICE_LABEL + price;
    }

    public String addSatsPerDollarLabel(String sats) {
        final String SATS_LABEL = "Sats per dollar:\s";
        return SATS_LABEL + sats;
    }

    public void writePriceFile(String[] priceData) throws IOException {
        final String FILE_NAME = "priceDataFile";
        final FileWriter fileWriter = new FileWriter(FILE_NAME, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(getDate() + ",\s" + getTime());
        bufferedWriter.newLine();
        for (String priceDatum : priceData) {
            bufferedWriter.write("\t" + priceDatum);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public String getDate() {
        return java.time.LocalDate.now().toString();
    }

    public String getTime() {
        final String timeFormat = "HH:mm:ss";     //HH results in 24H format
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);

        return formatter.format(new Date());
    }

}
