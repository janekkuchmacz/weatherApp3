package pl.kuchmaczpogoda.model;

import java.io.Serializable;

public class SelectedCityData implements Serializable {
    private final String cityName;
    private final String timeZoneName;

    public SelectedCityData(String cityName, String timeZoneName) {
        this.cityName = cityName;
        this.timeZoneName = timeZoneName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

}
