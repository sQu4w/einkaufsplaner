package com.brh.einkaufsplaner_desktop.controller;

import com.brh.einkaufsplaner_desktop.helper.DialogHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RecipeManagementController {

    @FXML
    private Button openShoppingListBtn;

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
        // Lade die FXML-Datei für die Einkaufsliste
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/brh/einkaufsplaner_desktop/shoppinglist.fxml"));
        Parent view = loader.load();

        // Erzeugt das Fenster (Stage) und setzt die Szene auf die geladene FXML-Datei
        Stage window = (Stage) openShoppingListBtn.getScene().getWindow();

        // Zeigt das neue Fenster mit dem geladenen Inhalt
        window.setScene(new Scene(view));
        window.show();
    }

    @FXML
    private void onAddRecipe(){
        //Todo: Hinzufügen eines Rezeptes
    }

    @FXML
    private void onDeleteRecipe(){
        //Todo: Löschen eines Rezeptes
    }

    @FXML
    private void onServingsInfo(){
        DialogHelper.infoDialog("Portionen",
                "Hier können Sie Basisportionen für das Rezept festlegen.");
    }

    @FXML
    private void onAddIngredient(){
        //Todo: Zutat zur Zutatenliste (des Rezeptes) hinzufügen
    }

    @FXML
    private void onDeleteIngredient(){
        //Todo: Zutat aus der Zutatenliste (des Rezeptes) löschen
    }

    @FXML
    private void onSaveRecipe(){
        //Todo: Rezept speichern
    }
}
