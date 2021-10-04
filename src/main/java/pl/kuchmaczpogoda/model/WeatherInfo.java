package pl.kuchmaczpogoda.model;


public class WeatherInfo extends Data {
    private final static String MAIN_API_PART = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private final static String ADDITIONAL_API_PART = "&lang=pl&units=metric";

    public WeatherInfo () {
        super();
    }

    @Override
    protected String getMainAPIPart() {
        return MAIN_API_PART;
    }

    @Override
    protected String getAdditionalAPIPart() {
        return ADDITIONAL_API_PART;
    }
}
