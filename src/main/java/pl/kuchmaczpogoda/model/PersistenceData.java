package pl.kuchmaczpogoda.model;

import java.io.Serializable;
import java.util.List;

public class PersistenceData implements Serializable {
    private SelectedCityData firstCityData;
    private SelectedCityData secondCityData;
    private List<City> cities;

    public SelectedCityData getFisrtCityData() {
        return firstCityData;
    }

    public void setFisrtCityData(SelectedCityData fisrtCityData) {
        this.firstCityData = fisrtCityData;
    }

    public SelectedCityData getSecondCityData() {
        return secondCityData;
    }

    public void setSecondCityData(SelectedCityData secondCityData) {
        this.secondCityData = secondCityData;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
