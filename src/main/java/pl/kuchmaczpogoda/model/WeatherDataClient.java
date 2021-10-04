package pl.kuchmaczpogoda.model;

import org.json.JSONArray;
import pl.kuchmaczpogoda.utils.DialogUtils;
import pl.kuchmaczpogoda.utils.JSONConverter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDataClient {

    private JSONArray dataJsonArray;

    public WeatherDataClient() {
    }

    public JSONArray getDataJsonArray() {
        return dataJsonArray;
    }

    public void loadWeatherData(String cityName){
       dataJsonArray = getJSONArrayOfWeatherData(cityName);
    }

    private JSONArray getJSONArrayOfWeatherData(String cityName) {
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.connectToDatabase(cityName);
        String weatherData = null;
        try {
            weatherData = weatherInfo.getResult();
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        } catch (RuntimeException e){
            DialogUtils.errorDialog(e.getMessage());
        }
        JSONArray jsonArray = JSONConverter.convertStringObjectToJSONArrayWithWeatherData(weatherData);
        return jsonArray;
    }
    public String getWeatherDescription(int index) {
        return  getDataJsonArray().getJSONObject(index)
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");
    }
    public String getTemperature (int index){
        return getDataJsonArray().getJSONObject(index)
                .getJSONObject("main")
                .getInt("temp")+"°C";
    }

    public String getPressure(int index) {
        return getDataJsonArray().getJSONObject(index)
                .getJSONObject("main")
                .getInt("pressure")+" hPa";
    }

    public String getHumidity(int index) {
        return getDataJsonArray().getJSONObject(index)
                .getJSONObject("main")
                .getInt("humidity")+"%";
    }

    public Date getDate(int index) {
        String dateAsString =  getDataJsonArray().getJSONObject(index).getString("dt_txt");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateAsDateObject = new Date();
        try {
         dateAsDateObject = formatter.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAsDateObject;
    }

    public int getJsonArraySize() {
        return getDataJsonArray().length();
    }
    public String getIconUrl(int index) {
        String weatherIconId = getDataJsonArray().getJSONObject(index).getJSONArray("weather").getJSONObject(0).getString("icon");
        return "http://openweathermap.org/img/wn/" + weatherIconId + "@2x.png";
    }
}
