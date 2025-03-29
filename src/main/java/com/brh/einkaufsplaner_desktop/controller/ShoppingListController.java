package com.brh.einkaufsplaner_desktop.controller;

import com.brh.einkaufsplaner_desktop.model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static com.brh.einkaufsplaner_desktop.helper.DialogHelper.*;
import static com.brh.einkaufsplaner_desktop.helper.ValidationHelper.validateNumber;
import static com.brh.einkaufsplaner_desktop.helper.ValidationHelper.validateTextField;

public class ShoppingListController {

    @FXML private TextField articleNameTF;
    @FXML private TextField articleAmountTF;
    @FXML private TextField articleUnitTF;

    @FXML private TableView<Article> shoppingListTV;
    @FXML private TableColumn<Article, String> articleItemCol;
    @FXML private TableColumn<Article, Double> articleAmountCol;
    @FXML private TableColumn<Article, String> articleUnitCol;
    @FXML private TableColumn<Article, Boolean> articleBoughtCol;

    @FXML private Button openRecipeManagementBtn;

    private final ObservableList<Article> shoppingList = FXCollections.observableArrayList();

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

    /**
     * Initialisiert die Einkaufsliste und verbindet die Spalten mit den Daten.
     */
    @FXML
    private void initialize() {
        // TableView mit Liste verbinden
        shoppingListTV.setItems(shoppingList);

        // Spalten verbinden
        articleItemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        articleAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        articleUnitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

        // Checkbox-Spalte
        articleBoughtCol.setCellValueFactory(new PropertyValueFactory<>("bought"));
        articleBoughtCol.setCellFactory(CheckBoxTableCell.forTableColumn(articleBoughtCol));
    }

    /**
     * Fügt einen Artikel zur Einkaufsliste hinzu.
     */
    @FXML
    private void onAddArticle() {

        // Eingabefelder validieren (String)
        if (!validateTextField(articleNameTF, "Artikelname")) return;
        if (!validateTextField(articleUnitTF, "Einheit")) return;

        // Eingabefeld validieren (Double)
        Double amount = validateNumber(articleAmountTF, "Menge");
        if (amount == null) return;

        // Artikel erstellen und zur Liste hinzufügen
        String name = articleNameTF.getText().trim();
        String unit = articleUnitTF.getText().trim();
        Article article = new Article(false, name, amount, unit);
        shoppingList.add(article);

        // Eingabefelder leeren
        articleNameTF.clear();
        articleAmountTF.clear();
        articleUnitTF.clear();

        // Fokus zurück auf das Eingabefeld um den nächsten Artikel hinzuzufügen
        articleNameTF.requestFocus();
    }


    @FXML
    private void onDeleteArticle() {
        Article selectedArticle = shoppingListTV.getSelectionModel().getSelectedItem();

        if (selectedArticle == null) {
            warningDialog("Kein Artikel ausgewählt",
                    "Bitte wählen Sie einen Artikel aus der Liste, den Sie löschen möchten.");
            return;
        }

        boolean confirmed = confirmDeleteDialog(selectedArticle.getName());

        if (confirmed) {
            shoppingListTV.getItems().remove(selectedArticle);
        }
    }


    @FXML
    private void onDeleteShoppingList() {
        boolean confirmed = confirmDialog(
                "Einkaufsliste löschen",
                "Möchten Sie wirklich die gesamte Einkaufsliste löschen?");

        if (confirmed) {
            shoppingList.clear();
            shoppingListTV.getItems().clear(); // View aktualisieren
            infoDialog("Liste gelöscht", "Die Einkaufsliste wurde erfolgreich gelöscht.");
        }
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
