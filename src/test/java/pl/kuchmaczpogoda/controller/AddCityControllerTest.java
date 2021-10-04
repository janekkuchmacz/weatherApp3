package pl.kuchmaczpogoda.controller;

import org.junit.Test;
import pl.kuchmaczpogoda.model.WeatherInfo;

import java.net.MalformedURLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

public class AddCityControllerTest {

    @Test
    public void shouldReturnTrueForCorrectCityName() throws MalformedURLException {
        //given
        String cityName = "Warsaw";
        AddCityController addCityController = new AddCityController();
        WeatherInfo weatherInfoMock = mock(WeatherInfo.class);
        willDoNothing().given(weatherInfoMock).connectToDatabase(cityName);
        given(weatherInfoMock.getResponseCode()).willReturn(200);
        //when, then
        assertTrue(addCityController.isCityNameCorrect(cityName));
    }
}
