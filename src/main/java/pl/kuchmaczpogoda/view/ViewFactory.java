package pl.kuchmaczpogoda.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.kuchmaczpogoda.Launcher;
import pl.kuchmaczpogoda.controller.AddCityController;
import pl.kuchmaczpogoda.controller.BaseController;
import pl.kuchmaczpogoda.controller.MainWindowController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ViewFactory {

    private ArrayList<Stage>activeStages;

    public ViewFactory() {
        activeStages = new ArrayList<>();
    }
    public void showMainWindow(){
        BaseController controller = new MainWindowController(this, "/pl/kuchmaczpogoda/MainWindow.fxml");
        initializeStage(controller);
    }

    public void showAddCityWindow() {
        BaseController controller = new AddCityController(this, "/pl/kuchmaczpogoda/AddCityWindow.fxml");
        initializeStage(controller);
    }
    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }
    private void initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        scene.getStylesheets().add(Objects.requireNonNull(Launcher.class.getResource("css/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Aplikacja pogodowa by Janek Kuchmacz");
        stage.setResizable(false);

        stage.setOnCloseRequest( e -> baseController.closeWindowByX() );

        stage.show();
        activeStages.add(stage);
    }


    public void disableStage(Stage stage) {
        Scene scene = stage.getScene();
        Parent parent = scene.getRoot();
        parent.setDisable(true);
    }

    public void enableMainWindow() {
        for(Stage stage: activeStages){
            Scene scene = stage.getScene();
            Parent parent= scene.getRoot();
            parent.setDisable(false);
        }
    }

}
