import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Scanner;

public class PriceData {
    public float bitcoinSpotPrice = btcPriceCheck();
    public int satsPerDollar = satsPerDollar();
    public String formattedSats = formatSatsPerDollar(satsPerDollar);

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
        long satsPerBitcoin = 100_000_000;
        return (int) (satsPerBitcoin / bitcoinSpotPrice);
    }

    private String formatSatsPerDollar(int sats) {
        //used for adding a comma to output
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        return formatter.format(sats) + " sats";
    }
}
