package pl.kuchmaczpogoda;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.kuchmaczpogoda.view.ViewFactory;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();

    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
