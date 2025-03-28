package com.brh.einkaufsplaner_desktop.controller;

import com.brh.einkaufsplaner_desktop.model.Article;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ShoppingListController {

    @FXML
    private Button openRecipeManagementBtn;

    @FXML
    private TextField articleNameTF;

    @FXML
    private TextField articleAmountTF;

    @FXML
    private TextField articleUnitTF;

    @FXML
    private TableView<Article> shoppingListTV;


    // Todo: Initalisierungs-Methode, die beim Start des Controllers aufgerufen wird
    //    @FXML
    //    private void initialize(){
    //
    //    }

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

    @FXML
    private void onAddArticle(){
        //Todo: Hinzufügen eines Artikels zur Einkaufsliste
        String name = articleNameTF.getText();
        String amount = articleAmountTF.getText();
        String unit = articleUnitTF.getText();

        // Überprüfen, ob alle Felder ausgefüllt sind
        if (name.isEmpty() || amount.isEmpty() || unit.isEmpty()){

        }
    }

    @FXML
    private void onDeleteArticle(){
        //Todo: Löschen eines Artikels aus der Einkaufsliste
    }

    @FXML
    private void onDeleteShoppingList(){
        //Todo: Löschen der gesamten Einkaufsliste
    }

    @FXML
    private void onAddRecipe(){
        //Todo: Hinzufügen eines Rezepts zur Einkaufsliste
    }

    @FXML
    private void onSelectRecipe(){
        //Todo: Auswählen eines Rezepts zur aus der Rezeptsammlung
        // von der Rezeptverwaltung
    }

    @FXML
    private void onMoveUp(){
        //Todo: Verschieben eines Artikels in der Einkaufsliste nach oben
    }

    @FXML
    private void onMoveDown(){
        //Todo: Verschieben eines Artikels in der Einkaufsliste nach unten
    }
}
