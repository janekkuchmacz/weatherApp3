package pl.kuchmaczpogoda.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import pl.kuchmaczpogoda.model.Data;
import pl.kuchmaczpogoda.model.WeatherDataClient;
import pl.kuchmaczpogoda.model.WeatherInfo;
import pl.kuchmaczpogoda.utils.DialogUtils;
import pl.kuchmaczpogoda.utils.ImageUtils;
import pl.kuchmaczpogoda.utils.JSONConverter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeatherController {

    private static final int ONE_DAY_MILISECONDS = 86400000;

    protected void fillCurrentWeatherData(String cityName, String timeZone, Label temperatureCityOne, Label pressureCityOne, Label humidityCityOne, ImageView weatherCityOneImg) throws IOException, ParseException {

        WeatherDataClient weatherDataClient = new WeatherDataClient();
        weatherDataClient.loadWeatherData(cityName);
        for (int i = 0; i < weatherDataClient.getJsonArraySize(); i++) {
            Date futureDateAsDateObject = weatherDataClient.getDate(i);
            Date date = getTimeInZone(timeZone);
            if (date.before(futureDateAsDateObject)) {
                temperatureCityOne.setText(weatherDataClient.getTemperature(i));
                pressureCityOne.setText(weatherDataClient.getPressure(i));
                humidityCityOne.setText(weatherDataClient.getHumidity(i));
                weatherCityOneImg.setImage(ImageUtils.setImageUrl(weatherDataClient.getIconUrl(i)));
                break;
            }
        }
    }

    public Date getTimeInZone(String timeZone) throws ParseException {
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        String formattedString = formatter1.format(zonedDateTime);
        formattedString = formattedString.substring(0, 21);
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");
        Date date = formatter2.parse(formattedString);
        return date;
    }

    public static Date trim(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        return calendar.getTime();
    }

    public void fillFutureWeather(String cityName, String timeZone, List<Label> temperatures, List<ImageView> images) throws ParseException {
        WeatherDataClient weatherDataClient = new WeatherDataClient();
        weatherDataClient.loadWeatherData(cityName);

        int j = 0; //temperature and images index
        Date date = getTimeInZone(timeZone);
        Date tomorrow = new Date(date.getTime() + ONE_DAY_MILISECONDS);
        tomorrow = trim(tomorrow);

        for (int i = 0; i < weatherDataClient.getJsonArraySize(); i++) {
            Date futureDateAsDateObject = weatherDataClient.getDate(i);
            if (futureDateAsDateObject.equals(tomorrow) && j < 4) {
                temperatures.get(j).setText(weatherDataClient.getTemperature(i));
                images.get(j).setImage(ImageUtils.setImageUrl(weatherDataClient.getIconUrl(i)));
                j++;
                tomorrow = new Date(tomorrow.getTime() + ONE_DAY_MILISECONDS);
            }
        }
    }
}
