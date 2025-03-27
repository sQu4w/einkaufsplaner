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
        DataInitializer.initializeFiles();  // Initialisierung der Dateien

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homescreen.fxml"));
        Parent root = loader.load(); // lädt die FXML-Datei

        stage.setTitle("Einkaufsplaner");
        stage.setScene(new Scene(root)); // Setze die Szene auf die geladene FXML-Datei
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
