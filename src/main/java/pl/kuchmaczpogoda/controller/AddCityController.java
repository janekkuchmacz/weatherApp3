package pl.kuchmaczpogoda.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.kuchmaczpogoda.model.*;
import pl.kuchmaczpogoda.view.ViewFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class AddCityController extends BaseController {

    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;

    @FXML
    void returnButtonAction() {
        viewFactory.closeStage((Stage) cityField.getScene().getWindow());
        viewFactory.enableMainWindow();
    }

    @FXML
    void findButtonAction() throws MalformedURLException {
        String cityName = cityField.getText();
        if (isCityNameUnique(cityName) && isCityNameCorrect(cityName)) {
            Cities.cities.add(cityName);
            errorLabel.setText("Miejscowość dodana do Twoich miejscowości");
        }
    }

    WeatherInfo weatherInfo = new WeatherInfo();

    public AddCityController(){
        super();
    }

    public AddCityController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    private boolean isCityNameUnique(String cityName) {
        //List<String> cities = Cities.getCities();
        PersistenceService persistenceService = new PersistenceService();
        File tempFile = new File(persistenceService.getDataLocation());
        if (tempFile.exists()) {
            PersistenceData persistenceData = persistenceService.loadFromPersistenceMethod();
            List<City> cities = persistenceData.getCities();
            if (cities != null) {
                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).getCityName().equals(cityName)) {
                        errorLabel.setText("Miejscowość już dodana");
                        return false;
                    }
                }
            }
        }
        List<String> citiesInApp = Cities.getCities();
        for (int i = 0; i < citiesInApp.size(); i++) {
            if (citiesInApp.get(i).equals(cityName)) {
                errorLabel.setText("Miejscowość już dodana");
                return false;
            }
        }
        return true;
    }

    public boolean isCityNameCorrect(String cityName) throws MalformedURLException {
        weatherInfo.connectToDatabase(cityName);
        if (weatherInfo.getResponseCode() != 200) {
            errorLabel.setText("Brak miejscowości w bazie danych");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void closeWindowByX() {
        viewFactory.closeStage((Stage) cityField.getScene().getWindow());
        viewFactory.enableMainWindow();
    }
}
