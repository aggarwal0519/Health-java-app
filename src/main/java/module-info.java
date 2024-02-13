module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens sample to javafx.fxml;
    exports sample;
    exports sample.controller;
    opens sample.controller to javafx.fxml;
    exports sample.model;
    opens sample.model to javafx.fxml;
}