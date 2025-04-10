package com.brh.einkaufsplaner_desktop;

import com.brh.einkaufsplaner_desktop.service.DataInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Service-Klasse zum Initialisieren der Daten
        DataInitializer.initializeFiles();

        // Lädt die FXML-Datei für die Startseite
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/brh/einkaufsplaner_desktop/homescreen.fxml"));
        Parent root = loader.load(); // lädt die FXML-Datei

        // Setzt die Szene auf die geladene FXML-Datei und ihre Größe mit der sie erstellt wurde
        stage.setScene(new Scene(root));
        stage.setTitle("MealMatch Einkaufsplaner");
        stage.show();
        stage.setY(50);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
