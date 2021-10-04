package pl.kuchmaczpogoda.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.kuchmaczpogoda.model.*;
import pl.kuchmaczpogoda.view.ViewFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public class MainWindowController extends BaseController implements Initializable {

    private Service<Void> service;
    WeatherController weatherController;
    private static final String DEFAULT_FIRST_CITY = "Warszawa";
    private static final String DEFAULT_SECOND_CITY = "Londyn";
    private static final String DEFAULT_FIRST_TIME_ZONE = "Europe/Warsaw";
    private static final String DEFAULT_SECOND_TIME_ZONE = "Europe/London";

    @FXML
    private ChoiceBox<String> firstCityChoiceBox;

    @FXML
    private ChoiceBox<String> secondCityChoiceBox;

    @FXML
    private ImageView weatherCityOneImg;

    @FXML
    private Label temperatureCityOne;

    @FXML
    private Label pressureCityOne;

    @FXML
    private Label humidityCityOne;

    @FXML
    private Label humidityCityOne1;

    @FXML
    private ImageView tomorrowIconCityOne;

    @FXML
    private Label tomorrowTempCityOne;

    @FXML
    private ImageView plusTwoIconCityOne;

    @FXML
    private Label plusTwoTemperatureCityOne;

    @FXML
    private ImageView plusThreeIconCityOne;

    @FXML
    private Label plusThreeTemperatureCityOne;

    @FXML
    private ImageView plusFourIconCityOne;

    @FXML
    private Label plusFourTemperatureCityOne;

    @FXML
    private ChoiceBox<String> timeZone;

    @FXML
    private ChoiceBox<String> timeZoneCityTwo;

    @FXML
    private ImageView weatherCityTwoImg;

    @FXML
    private Label temperatureCityTwo;

    @FXML
    private Label pressureCityTwo;

    @FXML
    private Label humidityCityTwo;

    @FXML
    private ImageView tomorrowCityTwo;

    @FXML
    private Label tomorrowTempCityTwo;

    @FXML
    private ImageView plusTwoIconCityTwo;

    @FXML
    private Label plusTwoTmperatureCityTwo;

    @FXML
    private ImageView plusThreeIconCityTwo;

    @FXML
    private Label plusThreeTmperatureCityTwo;

    @FXML
    private ImageView plusFourIconCityTwo;

    @FXML
    private Label plusFourTmperatureCityTwo;

    PersistenceData persistenceData = new PersistenceData();

    @FXML
    void addCityAction() {
        viewFactory.showAddCityWindow();
        viewFactory.disableStage((Stage) temperatureCityOne.getScene().getWindow());
    }

    @FXML
    public void showFirstCityWeatherAction() {
        updateWeatherInFirstCity();
    }

    @FXML
    void showSecondCityWeatherAction() {
        updateWeatherInSecondCity();
    }

    @FXML
    void closeAction() {
        viewFactory.closeStage((Stage) firstCityChoiceBox.getScene().getWindow());
        closeWindowByX();
    }

    @FXML
    void infoAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aplikacja pogodowa by Janek Kuchmacz");
        alert.setHeaderText("Dzięki za skorzystanie z mojej apki");
        alert.setContentText("Autor: Jan Kuchmacz\nhttps://jankuchmacz.pl/");
        alert.showAndWait();
    }

    @FXML
    void refreshCityAction() {
        updateChoiceBoxes();
    }

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        weatherController = new WeatherController();
    }

    @Override
    public void closeWindowByX() {
        SelectedCityData firstCityData = new SelectedCityData(firstCityChoiceBox.getValue(), timeZone.getValue());
        SelectedCityData secondCityData = new SelectedCityData(secondCityChoiceBox.getValue(), timeZoneCityTwo.getValue());
        List<City> cities = new ArrayList<>();
        List<String> citiesFromChoiceBox = firstCityChoiceBox.getItems();
        for (int i = 0; i < citiesFromChoiceBox.size(); i++) {
            cities.add(new City(citiesFromChoiceBox.get(i)));
        }
        PersistenceData persistenceData = new PersistenceData();
        persistenceData.setFisrtCityData(firstCityData);
        persistenceData.setSecondCityData(secondCityData);
        persistenceData.setCities(cities);
        PersistenceService persistenceService = new PersistenceService();
        persistenceService.saveToPersistenceMethod(persistenceData);
        System.out.println("Do zobaczenia");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataFromFile();
        updateChoiceBoxes();
        updateTimeZoneChoiceBoxes();
        updateWeatherInFirstCity();
        updateWeatherInSecondCity();
    }

    private void getDataFromFile() {
        PersistenceService persistenceService = new PersistenceService();
        File tempFile = new File(persistenceService.getDataLocation());
        if (tempFile.exists()) {
            persistenceData = persistenceService.loadFromPersistenceMethod();
        }
    }

    private void updateTimeZoneChoiceBoxes() {
        List<String> timeZones = Cities.getTimeZones();
        Collections.sort(timeZones);
        SelectedCityData firstCityData = persistenceData.getFisrtCityData();
        SelectedCityData secondCityData = persistenceData.getSecondCityData();
        for (int i = 0; i < timeZones.size(); i++) {
            timeZone.getItems().add(timeZones.get(i));
            timeZoneCityTwo.getItems().add(timeZones.get(i));
        }
        if (firstCityData != null && firstCityData.getTimeZoneName() != null) {
            timeZone.setValue(firstCityData.getTimeZoneName());
        } else {
            timeZone.setValue(DEFAULT_FIRST_TIME_ZONE);
        }
        if (secondCityData != null && secondCityData.getTimeZoneName() != null) {
            timeZoneCityTwo.setValue(secondCityData.getTimeZoneName());
        } else {
            timeZoneCityTwo.setValue(DEFAULT_SECOND_TIME_ZONE);
        }
    }

    private void updateChoiceBoxes() {
        List<String> citiesInApp = Cities.getCities();
        List<String> cities = new ArrayList<>();
        for (int i = 0; i < citiesInApp.size(); i++) {
            cities.add(citiesInApp.get(i));
        }
        if (persistenceData.getCities() != null) {
            for (int i = 0; i < persistenceData.getCities().size(); i++) {
                cities.add(persistenceData.getCities().get(i).getCityName());
            }
        }
        cities = cities.stream().distinct().collect(Collectors.toList());
        java.util.Collections.sort(cities);
        Collections.reverse(cities);
        SelectedCityData firstCityData = persistenceData.getFisrtCityData();
        SelectedCityData secondCityData = persistenceData.getSecondCityData();
        firstCityChoiceBox.getItems().clear();
        secondCityChoiceBox.getItems().clear();
        for (int i = cities.size() - 1; i >= 0; i--) {
            firstCityChoiceBox.getItems().add(cities.get(i));
            secondCityChoiceBox.getItems().add(cities.get(i));
        }
        if (firstCityData != null && firstCityData.getCityName() != null) {
            firstCityChoiceBox.setValue(firstCityData.getCityName());
        } else {
            firstCityChoiceBox.setValue(DEFAULT_FIRST_CITY);
        }
        if (secondCityData != null && secondCityData.getCityName() != null) {
            secondCityChoiceBox.setValue(secondCityData.getCityName());
        } else {
            secondCityChoiceBox.setValue(DEFAULT_SECOND_CITY);
        }
    }

    private void updateWeatherInFirstCity() {
        service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            try {
                                getWeatherForFirstCity();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    private void updateWeatherInSecondCity() {
        service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            try {
                                getWeatherForSecondCity();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    private void getWeatherForFirstCity() throws IOException, ParseException {
        List<Label> temperaturesFuture = Arrays.asList(tomorrowTempCityOne, plusTwoTemperatureCityOne, plusThreeTemperatureCityOne, plusFourTemperatureCityOne);
        List<ImageView> imagesFuture = Arrays.asList(tomorrowIconCityOne, plusTwoIconCityOne, plusThreeIconCityOne, plusFourIconCityOne);
        weatherController.fillCurrentWeatherData(getCityName(firstCityChoiceBox), getTimeZone(timeZone), temperatureCityOne, pressureCityOne, humidityCityOne, weatherCityOneImg);
        weatherController.fillFutureWeather(getCityName(firstCityChoiceBox), getTimeZone(timeZone), temperaturesFuture, imagesFuture);
    }

    private void getWeatherForSecondCity() throws IOException, ParseException {
        List<Label> temperaturesFuture = Arrays.asList(tomorrowTempCityTwo, plusTwoTmperatureCityTwo, plusThreeTmperatureCityTwo, plusFourTmperatureCityTwo);
        List<ImageView> imagesFuture = Arrays.asList(tomorrowCityTwo, plusTwoIconCityTwo, plusThreeIconCityTwo, plusFourIconCityTwo);
        weatherController.fillCurrentWeatherData(getCityName(secondCityChoiceBox), getTimeZone(timeZoneCityTwo), temperatureCityTwo, pressureCityTwo, humidityCityTwo, weatherCityTwoImg);
        weatherController.fillFutureWeather(getCityName(secondCityChoiceBox), getTimeZone(timeZoneCityTwo), temperaturesFuture, imagesFuture);
    }

    private String getTimeZone(ChoiceBox<String> timeZone) {
        return timeZone.getValue();
    }

    private String getCityName(ChoiceBox<String> cityChoiceBox) {
        return cityChoiceBox.getValue();
    }
}
