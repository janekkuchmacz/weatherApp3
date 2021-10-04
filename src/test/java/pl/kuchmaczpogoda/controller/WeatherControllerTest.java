package pl.kuchmaczpogoda.controller;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class WeatherControllerTest {

    @Test
    public void shouldReturnCorrectTimeInTimeZoneAsDateObject() throws ParseException {
        //given
        WeatherController weatherController = new WeatherController();
        String exampleTimeZone = "Europe/Warsaw";
        Date date = new Date();
        //when
        Date currentDate = weatherController.getTimeInZone(exampleTimeZone);
        //then
        assertEquals(date.getTime(), currentDate.getTime(), 1000.0);
    }
    @Test
    public void shouldTrimDateCorrectly(){
        //given
        Date date = new Date();
        Date exampleDate = new Date();
        exampleDate.setHours(12);
        exampleDate.setMinutes(00);
        exampleDate.setSeconds(00);
        //when
        Date dateAfterTrimOperation = WeatherController.trim(date);
        //then
        assertEquals(exampleDate.getTime(), dateAfterTrimOperation.getTime(), 1000.0);
    }
}
