module pl.kuchmaczpogoda{
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.json;
    requires dotenv.java;
    requires jdk.jsobject;

    opens pl.kuchmaczpogoda;
    opens pl.kuchmaczpogoda.controller to javafx.fxml;
    opens pl.kuchmaczpogoda.model to javafx.base;

}