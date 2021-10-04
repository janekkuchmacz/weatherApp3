package pl.kuchmaczpogoda.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class PersistanceDataTest {

    @Test
    void addingCityToCitiesShouldIncreaseCitiesSize(){
        //given
        City city1 = new City("Warsaw");
        City city2 = new City("Moscow");
        List<City> cities = Arrays.asList(city1, city2);
        PersistenceData persistenceData = new PersistenceData();
        persistenceData.setCities(cities);
        //when //then
        assertThat(persistenceData.getCities(), hasSize(2));
    }
}
