package com.brh.einkaufsplaner_desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private Button openShoppingListBtn;

    @FXML
    private Button openRecipeManagementBtn;

    // Todo: Initalisierungs-Methode, die beim Start des Controllers aufgerufen wird
    //    @FXML
    //    private void initialize(){
    //
    //    }

    /**
     * Öffnet die Ansicht für die Einkaufsliste
     */
    @FXML
    private void goToShoppingList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/brh/einkaufsplaner_desktop/shoppinglist.fxml"));
        Parent view = loader.load();

        Stage window = (Stage) openShoppingListBtn.getScene().getWindow();
        window.setScene(new Scene(view));
        window.show();
    }

    /**
     * Öffnet die Ansicht für die Rezeptverwaltung
     */
    @FXML
    private void goToRecipeManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/brh/einkaufsplaner_desktop/recipemanagement.fxml"));
        Parent view = loader.load();

        Stage window = (Stage) openRecipeManagementBtn.getScene().getWindow();
        window.setScene(new Scene(view));
        window.show();
    }
}
