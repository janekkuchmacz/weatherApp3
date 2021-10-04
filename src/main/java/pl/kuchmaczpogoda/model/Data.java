package pl.kuchmaczpogoda.model;

import io.github.cdimascio.dotenv.DotenvException;
import org.json.JSONArray;
import pl.kuchmaczpogoda.utils.DialogUtils;
import pl.kuchmaczpogoda.utils.DotenvLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public abstract class Data {

    private URL url;
    private String result = "";
    private int responseCode;

    public Data() {
    }

    protected abstract String getMainAPIPart();

    protected abstract String getAdditionalAPIPart();

    public void connectToDatabase(String cityName) {
        connect(cityName, getMainAPIPart(), getAdditionalAPIPart());
    }

    public int getResponseCode() {
        return responseCode;
    }

    private void connect(String cityName, String mainAPIPart, String additionalAPIPart) {

        try {
            url = new URL(mainAPIPart + cityName + "&appid=" + getApiKey() + additionalAPIPart);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            responseCode = connection.getResponseCode();
        } catch (ProtocolException e) {
            DialogUtils.errorDialog(e.getMessage());
        } catch (MalformedURLException e) {
            DialogUtils.errorDialog(e.getMessage());
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    protected String getApiKey() {
        final String apiKey;
        DotenvLoader dotenvLoader = new DotenvLoader(".env");
        try {
            dotenvLoader.loadEnvFile();
        } catch (DotenvException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
        apiKey = dotenvLoader.getEnvVariable("API_KEY");
        return apiKey;
    }

    public String getResult() throws IOException, RuntimeException {
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                result += scanner.nextLine();
            }
            scanner.close();
            return result;
        }
    }

    public static String getIconUrl(JSONArray jsonArray, int arrayIndex) {
        String weatherIconId = jsonArray.getJSONObject(arrayIndex).getJSONArray("weather").getJSONObject(0).getString("icon");
        return "http://openweathermap.org/img/wn/" + weatherIconId + "@2x.png";
    }
}
