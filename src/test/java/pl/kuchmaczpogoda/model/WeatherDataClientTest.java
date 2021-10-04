package pl.kuchmaczpogoda.model;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import pl.kuchmaczpogoda.utils.JSONConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class WeatherDataClientTest {
    private static final String EXAMPLE_CITY_NAME = "Warszawa";
    private static final String EXAMPLE_WEATHER_DATA ="{\"cod\":\"200\",\"message\":0,\"cnt\":40,\"list\":[{\"dt\":1633014000,\"main\":{\"temp\":8.69,\"feels_like\":8.69,\"temp_min\":8.69,\"temp_max\":8.9,\"pressure\":1028,\"sea_level\":1028,\"grnd_level\":931,\"humidity\":98,\"temp_kf\":-0.21},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"słabe opady deszczu\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.21,\"deg\":8,\"gust\":1.46},\"visibility\":5080,\"pop\":0.64,\"rain\":{\"3h\":1.3},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-09-30 15:00:00\"},{\"dt\":1633024800,\"main\":{\"temp\":8.57,\"feels_like\":8.57,\"temp_min\":8.57,\"temp_max\":8.57,\"pressure\":1028,\"sea_level\":1028,\"grnd_level\":932,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"słabe opady deszczu\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.02,\"deg\":9,\"gust\":1.31},\"visibility\":5078,\"pop\":0.64,\"rain\":{\"3h\":1.22},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-09-30 18:00:00\"},{\"dt\":1633035600,\"main\":{\"temp\":8.03,\"feels_like\":8.03,\"temp_min\":8.03,\"temp_max\":8.03,\"pressure\":1029,\"sea_level\":1029,\"grnd_level\":933,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"słabe opady deszczu\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.25,\"deg\":358,\"gust\":1.6},\"visibility\":5042,\"pop\":0.45,\"rain\":{\"3h\":0.95},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-09-30 21:00:00\"},{\"dt\":1633046400,\"main\":{\"temp\":7.48,\"feels_like\":7.48,\"temp_min\":7.48,\"temp_max\":7.48,\"pressure\":1029,\"sea_level\":1029,\"grnd_level\":932,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"słabe opady deszczu\",\"icon\":\"10n\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":0.99,\"deg\":92,\"gust\":1.16},\"visibility\":9062,\"pop\":0.44,\"rain\":{\"3h\":0.23},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-01 00:00:00\"},{\"dt\":1633057200,\"main\":{\"temp\":7.33,\"feels_like\":6.79,\"temp_min\":7.33,\"temp_max\":7.33,\"pressure\":1028,\"sea_level\":1028,\"grnd_level\":932,\"humidity\":96,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":1.35,\"deg\":125,\"gust\":1.66},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-01 03:00:00\"},{\"dt\":1633068000,\"main\":{\"temp\":7.49,\"feels_like\":7.49,\"temp_min\":7.49,\"temp_max\":7.49,\"pressure\":1028,\"sea_level\":1028,\"grnd_level\":932,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.3,\"deg\":164,\"gust\":1.87},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-01 06:00:00\"},{\"dt\":1633078800,\"main\":{\"temp\":11.43,\"feels_like\":10.59,\"temp_min\":11.43,\"temp_max\":11.43,\"pressure\":1027,\"sea_level\":1027,\"grnd_level\":932,\"humidity\":75,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":1.09,\"deg\":178,\"gust\":1.88},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-01 09:00:00\"},{\"dt\":1633089600,\"main\":{\"temp\":12.87,\"feels_like\":11.81,\"temp_min\":12.87,\"temp_max\":12.87,\"pressure\":1025,\"sea_level\":1025,\"grnd_level\":931,\"humidity\":61,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":96},\"wind\":{\"speed\":1.04,\"deg\":213,\"gust\":1.38},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-01 12:00:00\"},{\"dt\":1633100400,\"main\":{\"temp\":11.9,\"feels_like\":10.87,\"temp_min\":11.9,\"temp_max\":11.9,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":930,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"zachmurzenie umiarkowane\",\"icon\":\"04d\"}],\"clouds\":{\"all\":83},\"wind\":{\"speed\":0.77,\"deg\":180,\"gust\":1.31},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-01 15:00:00\"},{\"dt\":1633111200,\"main\":{\"temp\":7.42,\"feels_like\":6.16,\"temp_min\":7.42,\"temp_max\":7.42,\"pressure\":1026,\"sea_level\":1026,\"grnd_level\":929,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":89},\"wind\":{\"speed\":2.03,\"deg\":174,\"gust\":2.05},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-01 18:00:00\"},{\"dt\":1633122000,\"main\":{\"temp\":6.7,\"feels_like\":5.21,\"temp_min\":6.7,\"temp_max\":6.7,\"pressure\":1025,\"sea_level\":1025,\"grnd_level\":929,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.15,\"deg\":189,\"gust\":2.61},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-01 21:00:00\"},{\"dt\":1633132800,\"main\":{\"temp\":6.15,\"feels_like\":4.58,\"temp_min\":6.15,\"temp_max\":6.15,\"pressure\":1025,\"sea_level\":1025,\"grnd_level\":928,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.13,\"deg\":192,\"gust\":2.69},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-02 00:00:00\"},{\"dt\":1633143600,\"main\":{\"temp\":5.89,\"feels_like\":4.27,\"temp_min\":5.89,\"temp_max\":5.89,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":927,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"zachmurzenie umiarkowane\",\"icon\":\"04n\"}],\"clouds\":{\"all\":55},\"wind\":{\"speed\":2.14,\"deg\":190,\"gust\":2.67},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-02 03:00:00\"},{\"dt\":1633154400,\"main\":{\"temp\":8.2,\"feels_like\":7.12,\"temp_min\":8.2,\"temp_max\":8.2,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":927,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"zachmurzenie małe\",\"icon\":\"03d\"}],\"clouds\":{\"all\":34},\"wind\":{\"speed\":1.97,\"deg\":188,\"gust\":2.9},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-02 06:00:00\"},{\"dt\":1633165200,\"main\":{\"temp\":13.83,\"feels_like\":12.97,\"temp_min\":13.83,\"temp_max\":13.83,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":928,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"pochmurnie\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":1.93,\"deg\":207,\"gust\":2.57},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-02 09:00:00\"},{\"dt\":1633176000,\"main\":{\"temp\":15.7,\"feels_like\":14.81,\"temp_min\":15.7,\"temp_max\":15.7,\"pressure\":1020,\"sea_level\":1020,\"grnd_level\":927,\"humidity\":57,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"pochmurnie\",\"icon\":\"02d\"}],\"clouds\":{\"all\":15},\"wind\":{\"speed\":1.66,\"deg\":222,\"gust\":1.98},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-02 12:00:00\"},{\"dt\":1633186800,\"main\":{\"temp\":13.54,\"feels_like\":12.7,\"temp_min\":13.54,\"temp_max\":13.54,\"pressure\":1020,\"sea_level\":1020,\"grnd_level\":926,\"humidity\":67,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"zachmurzenie małe\",\"icon\":\"03d\"}],\"clouds\":{\"all\":27},\"wind\":{\"speed\":1.03,\"deg\":219,\"gust\":2.12},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-02 15:00:00\"},{\"dt\":1633197600,\"main\":{\"temp\":8.59,\"feels_like\":7.36,\"temp_min\":8.59,\"temp_max\":8.59,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":926,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"zachmurzenie umiarkowane\",\"icon\":\"04n\"}],\"clouds\":{\"all\":62},\"wind\":{\"speed\":2.23,\"deg\":178,\"gust\":2.37},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-02 18:00:00\"},{\"dt\":1633208400,\"main\":{\"temp\":7.44,\"feels_like\":5.78,\"temp_min\":7.44,\"temp_max\":7.44,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":926,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"zachmurzenie umiarkowane\",\"icon\":\"04n\"}],\"clouds\":{\"all\":52},\"wind\":{\"speed\":2.52,\"deg\":186,\"gust\":3.13},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-02 21:00:00\"},{\"dt\":1633219200,\"main\":{\"temp\":7.27,\"feels_like\":5.42,\"temp_min\":7.27,\"temp_max\":7.27,\"pressure\":1021,\"sea_level\":1021,\"grnd_level\":925,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"zachmurzenie małe\",\"icon\":\"03n\"}],\"clouds\":{\"all\":42},\"wind\":{\"speed\":2.73,\"deg\":180,\"gust\":3.52},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-03 00:00:00\"},{\"dt\":1633230000,\"main\":{\"temp\":6.96,\"feels_like\":4.94,\"temp_min\":6.96,\"temp_max\":6.96,\"pressure\":1020,\"sea_level\":1020,\"grnd_level\":924,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"zachmurzenie małe\",\"icon\":\"03n\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":2.88,\"deg\":177,\"gust\":3.98},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-03 03:00:00\"},{\"dt\":1633240800,\"main\":{\"temp\":8.48,\"feels_like\":6.69,\"temp_min\":8.48,\"temp_max\":8.48,\"pressure\":1020,\"sea_level\":1020,\"grnd_level\":925,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"zachmurzenie umiarkowane\",\"icon\":\"04d\"}],\"clouds\":{\"all\":52},\"wind\":{\"speed\":2.99,\"deg\":178,\"gust\":4.45},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-03 06:00:00\"},{\"dt\":1633251600,\"main\":{\"temp\":13.67,\"feels_like\":12.48,\"temp_min\":13.67,\"temp_max\":13.67,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":925,\"humidity\":53,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.94,\"deg\":178,\"gust\":4.49},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-03 09:00:00\"},{\"dt\":1633262400,\"main\":{\"temp\":15.49,\"feels_like\":14.56,\"temp_min\":15.49,\"temp_max\":15.49,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":924,\"humidity\":56,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.45,\"deg\":166,\"gust\":5.64},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-03 12:00:00\"},{\"dt\":1633273200,\"main\":{\"temp\":13.17,\"feels_like\":12.45,\"temp_min\":13.17,\"temp_max\":13.17,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":923,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.86,\"deg\":164,\"gust\":4.71},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-03 15:00:00\"},{\"dt\":1633284000,\"main\":{\"temp\":9.26,\"feels_like\":7.48,\"temp_min\":9.26,\"temp_max\":9.26,\"pressure\":1019,\"sea_level\":1019,\"grnd_level\":924,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":90},\"wind\":{\"speed\":3.25,\"deg\":167,\"gust\":4.39},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-03 18:00:00\"},{\"dt\":1633294800,\"main\":{\"temp\":9.36,\"feels_like\":7.78,\"temp_min\":9.36,\"temp_max\":9.36,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":923,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.94,\"deg\":160,\"gust\":3.64},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-03 21:00:00\"},{\"dt\":1633305600,\"main\":{\"temp\":9.85,\"feels_like\":8.14,\"temp_min\":9.85,\"temp_max\":9.85,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":923,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.35,\"deg\":157,\"gust\":4.67},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-04 00:00:00\"},{\"dt\":1633316400,\"main\":{\"temp\":9.61,\"feels_like\":7.9,\"temp_min\":9.61,\"temp_max\":9.61,\"pressure\":1016,\"sea_level\":1016,\"grnd_level\":922,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":85},\"wind\":{\"speed\":3.25,\"deg\":155,\"gust\":4.98},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-04 03:00:00\"},{\"dt\":1633327200,\"main\":{\"temp\":10.43,\"feels_like\":9.38,\"temp_min\":10.43,\"temp_max\":10.43,\"pressure\":1016,\"sea_level\":1016,\"grnd_level\":922,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":90},\"wind\":{\"speed\":3.1,\"deg\":154,\"gust\":5.13},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-04 06:00:00\"},{\"dt\":1633338000,\"main\":{\"temp\":13.61,\"feels_like\":12.65,\"temp_min\":13.61,\"temp_max\":13.61,\"pressure\":1016,\"sea_level\":1016,\"grnd_level\":923,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":89},\"wind\":{\"speed\":3.03,\"deg\":164,\"gust\":4.71},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-04 09:00:00\"},{\"dt\":1633348800,\"main\":{\"temp\":15.84,\"feels_like\":15.02,\"temp_min\":15.84,\"temp_max\":15.84,\"pressure\":1016,\"sea_level\":1016,\"grnd_level\":923,\"humidity\":59,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":86},\"wind\":{\"speed\":2.72,\"deg\":170,\"gust\":4.45},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-04 12:00:00\"},{\"dt\":1633359600,\"main\":{\"temp\":12.57,\"feels_like\":11.81,\"temp_min\":12.57,\"temp_max\":12.57,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":923,\"humidity\":74,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04d\"}],\"clouds\":{\"all\":96},\"wind\":{\"speed\":2.37,\"deg\":180,\"gust\":4.19},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-04 15:00:00\"},{\"dt\":1633370400,\"main\":{\"temp\":11.55,\"feels_like\":10.77,\"temp_min\":11.55,\"temp_max\":11.55,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":924,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":2.45,\"deg\":168,\"gust\":3.43},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-04 18:00:00\"},{\"dt\":1633381200,\"main\":{\"temp\":11.21,\"feels_like\":10.29,\"temp_min\":11.21,\"temp_max\":11.21,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":924,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"zachmurzenie duże\",\"icon\":\"04n\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":2.61,\"deg\":169,\"gust\":3.46},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2021-10-04 21:00:00\"},{\"dt\":1633392000,\"main\":{\"temp\":10.74,\"feels_like\":9.78,\"temp_min\":10.74,\"temp_max\":10.74,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":924,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"zachmurzenie umiarkowane\",\"icon\":\"04d\"}],\"clouds\":{\"all\":83},\"wind\":{\"speed\":2.9,\"deg\":166,\"gust\":3.67},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-05 00:00:00\"},{\"dt\":1633402800,\"main\":{\"temp\":9.57,\"feels_like\":8.04,\"temp_min\":9.57,\"temp_max\":9.57,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":923,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"bezchmurnie\",\"icon\":\"01d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":2.92,\"deg\":173,\"gust\":3.7},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-05 03:00:00\"},{\"dt\":1633413600,\"main\":{\"temp\":11.16,\"feels_like\":10.24,\"temp_min\":11.16,\"temp_max\":11.16,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":923,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"pochmurnie\",\"icon\":\"02d\"}],\"clouds\":{\"all\":19},\"wind\":{\"speed\":2.87,\"deg\":171,\"gust\":4.14},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-05 06:00:00\"},{\"dt\":1633424400,\"main\":{\"temp\":16.57,\"feels_like\":15.61,\"temp_min\":16.57,\"temp_max\":16.57,\"pressure\":1016,\"sea_level\":1016,\"grnd_level\":923,\"humidity\":51,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"bezchmurnie\",\"icon\":\"01d\"}],\"clouds\":{\"all\":10},\"wind\":{\"speed\":3.13,\"deg\":173,\"gust\":5.64},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-05 09:00:00\"},{\"dt\":1633435200,\"main\":{\"temp\":17.78,\"feels_like\":16.81,\"temp_min\":17.78,\"temp_max\":17.78,\"pressure\":1015,\"sea_level\":1015,\"grnd_level\":923,\"humidity\":46,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"bezchmurnie\",\"icon\":\"01d\"}],\"clouds\":{\"all\":6},\"wind\":{\"speed\":3.31,\"deg\":177,\"gust\":5.4},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2021-10-05 12:00:00\"}],\"city\":{\"id\":3080866,\"name\":\"Zakopane\",\"coord\":{\"lat\":49.299,\"lon\":19.9489},\"country\":\"PL\",\"population\":27580,\"timezone\":7200,\"sunrise\":1632976698,\"sunset\":1633018916}}\n";
    private static final JSONArray EXAMPLE_JSON_ARRAY = JSONConverter.convertStringObjectToJSONArrayWithWeatherData(EXAMPLE_WEATHER_DATA);

    @Test
    void shouldReturnCorrectDescription(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        String exampleDescription = "słabe opady deszczu";
        //when
        String description=weatherDataClientSpy.getWeatherDescription(0);
        //then
        assertThat(description, equalTo(exampleDescription));
    }
    @Test
    void shouldReturnCorrectTemperature(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        String exampleTemperature = "8°C";
        //when, then
        assertThat(weatherDataClientSpy.getTemperature(0), equalTo(exampleTemperature));
    }
    @Test
    void temperatureShouldBeAStringValue(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when, then
        assertThat(weatherDataClientSpy.getTemperature(0), isA(String.class));
    }
    @Test
    void shouldReturnTemperatureWithCorrectUnit(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when, then
        assertTrue(weatherDataClientSpy.getTemperature(0).contains("°C"));
    }
    @Test
    void shouldReturnCorrectPressure(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        String examplePressure = "1028 hPa";
        //when
        String pressure = weatherDataClientSpy.getPressure(0);
        //then
        assertThat(pressure, equalTo(examplePressure));
    }
    @Test
    void shouldReturnPressureWithCorrectUnit(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when, then
        assertTrue(weatherDataClientSpy.getPressure(0).contains("hPa"));
    }
    @Test
    void pressureShouldBeGreaterThanZero(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when
        int pressure = extractIntFromString(weatherDataClientSpy.getPressure(0));
        //then
        assertThat(pressure, greaterThan(0));
    }
    @Test
    void shouldReturnCorrectHumidity(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        String exampleHumidity = "98%";
        //when
        String humidity = weatherDataClientSpy.getHumidity(0);
        //then
        assertThat(humidity, equalTo(exampleHumidity));
    }
    @Test
    void shouldReturnHumidityWithCorrectUnit(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when
        String humidity = weatherDataClientSpy.getHumidity(0);
        //then
        assertTrue(humidity.contains("%"));
    }
    @Test
    void humidityShouldBeGreaterThanZero(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when
        String humidityAsStringWithPercent = weatherDataClientSpy.getHumidity(0);
        String humidityAsStringWithoutPercent = humidityAsStringWithPercent.substring(0, humidityAsStringWithPercent.length()-1);
        int humidityAsInteger = Integer.parseInt(humidityAsStringWithoutPercent);
        //then
        assertThat(humidityAsInteger, greaterThan(0));
    }
    @Test
    void shouldReturnCorrectDate(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        Date exampleDate = new GregorianCalendar(2021, 8, 30).getTime();
        exampleDate = prepareDateForComparison(exampleDate);
        //when
        Date date = weatherDataClientSpy.getDate(0);
        //then
        assertThat(date, equalTo(exampleDate));
    }
    @Test
    void shouldReturndateAsDateObject(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when
        Date date = weatherDataClientSpy.getDate(0);
        //then
        assertThat(date, isA(Date.class));
    }
    @Test
    void shouldReturnCorrectJsonArraySize(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        //when
        int jsonArraySize = weatherDataClientSpy.getJsonArraySize();
        //then
        assertThat(jsonArraySize, is(40));
    }
    @Test
    void shouldReturnCorrectIconUrl(){
        //given
        WeatherDataClient weatherDataClientSpy = spy(WeatherDataClient.class);
        given(weatherDataClientSpy.getDataJsonArray()).willReturn(EXAMPLE_JSON_ARRAY);
        String exampleURL = "http://openweathermap.org/img/wn/10d@2x.png";
        //when
        String iconURL = weatherDataClientSpy.getIconUrl(0);
        //then
        assertThat(iconURL, is(exampleURL));
    }

    private int extractIntFromString (String str){
        int result=0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i)==' '){
                result = Integer.parseInt(str.substring(0, i));
            }
        }
        return result;
    }
    private Date prepareDateForComparison(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        return calendar.getTime();
    }
}
