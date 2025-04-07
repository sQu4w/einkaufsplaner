package com.brh.einkaufsplaner_desktop.controller;
import com.brh.einkaufsplaner_desktop.helper.ValidationHelper;
import com.brh.einkaufsplaner_desktop.model.Article;
import com.brh.einkaufsplaner_desktop.service.ShoppingListService;
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
import java.io.File;
import java.io.IOException;
import static com.brh.einkaufsplaner_desktop.helper.DialogHelper.*;
import static com.brh.einkaufsplaner_desktop.helper.ValidationHelper.*;
import static com.brh.einkaufsplaner_desktop.service.ShoppingListService.saveArticles;

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

    // Zum Aktualisieren der Einkaufsliste bei Änderungen (quasi unser Listener f
    private final ObservableList<Article> shoppingList = FXCollections.observableArrayList();

    /**
     * Öffnet die Ansicht für die Rezeptverwaltung
     */
    @FXML
    private void goToRecipeManagement() throws IOException {

        // Lade die FXML-Datei für die Rezeptverwaltung
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

        // Liste aus Datei laden
        shoppingList.setAll(ShoppingListService.loadArticles());

        // TableView mit Liste verbinden
        shoppingListTV.setItems(shoppingList);

        // Spalten mit den Daten verbinden
        articleItemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        articleAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        articleUnitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

        // Checkbox-Spalte für "Gekauft" erstellen
        articleBoughtCol.setCellValueFactory(new PropertyValueFactory<>("bought"));
        articleBoughtCol.setCellFactory(CheckBoxTableCell.forTableColumn(articleBoughtCol));
    }

    /**
     * Fügt einen Artikel zur Einkaufsliste hinzu.
     */
    @FXML
    private void onAddArticle() {

        // Eingabefelder validieren
        if (!ValidationHelper.validateName(articleNameTF)) return;
        if (!ValidationHelper.validateAmount(articleAmountTF)) return;
        if (!ValidationHelper.validateUnit(articleUnitTF)) return;

        // Werte extrahieren
        String name = articleNameTF.getText().trim();
        double amount = Double.parseDouble(articleAmountTF.getText().trim().replace(",", "."));
        String unit = articleUnitTF.getText().trim();

        // Artikel erstellen und zur Liste hinzufügen
        Article article = new Article(false, name, amount, unit);
        shoppingList.add(article);

        // Eingabefelder leeren
        articleNameTF.clear();
        articleAmountTF.clear();
        articleUnitTF.clear();
        articleNameTF.requestFocus();

        // Speicherung der Einkaufsliste
        saveShoppingList();
    }



    @FXML
    private void onDeleteArticle() {
        Article selectedArticle = shoppingListTV.getSelectionModel().getSelectedItem();

        if (selectedArticle == null) {
            warningDialog("Kein Artikel ausgewählt",
                    "Bitte wähle einen Artikel aus der Liste aus, um ihn zu löschen.");
            return;
        }

        boolean confirmed = confirmDeleteDialog(selectedArticle.getName());

        if (confirmed) {
            shoppingListTV.getItems().remove(selectedArticle);
            saveShoppingList();
        }
    }


    @FXML
    private void onDeleteShoppingList() {
        boolean confirmed = confirmDialog(
                "Einkaufsliste löschen",
                "Möchtest du wirklich die gesamte Einkaufsliste löschen?");

        if (confirmed) {
            shoppingList.clear();
            shoppingListTV.getItems().clear();// View aktualisieren
            saveShoppingList();
            infoDialog("Liste gelöscht", "Die Einkaufsliste wurde erfolgreich gelöscht.");
        }
    }

    /**
     * Verschiebt den ausgewählten Artikel in der Einkaufsliste nach oben.
     */
    @FXML
    private void onMoveUp() {

        // gibt den Index des aktuell ausgewählten Artikels zurück
        int selectedIndex = shoppingListTV.getSelectionModel().getSelectedIndex();

        // Wenn der Index größer als 0 ist, dann kann der Artikel nach oben verschoben werden
        if (selectedIndex > 0) {
            Article selectedArticle = shoppingList.get(selectedIndex);
            shoppingList.remove(selectedIndex);
            shoppingList.add(selectedIndex - 1, selectedArticle);
            shoppingListTV.getSelectionModel().select(selectedIndex - 1);
            saveShoppingList();
        }
    }

    /**
     * Verschiebt den ausgewählten Artikel in der Einkaufsliste nach unten.
     */
    @FXML
    private void onMoveDown() {

        // gibt den Index des aktuell ausgewählten Artikels zurück
        int selectedIndex = shoppingListTV.getSelectionModel().getSelectedIndex();

        // Ist der Index kleiner als die Größe der Liste - 1,
        // dann kann der Artikel nach unten verschoben werden
        if (selectedIndex >= 0 && selectedIndex < shoppingList.size() - 1) {
            Article selectedArticle = shoppingList.get(selectedIndex);
            shoppingList.remove(selectedIndex);
            shoppingList.add(selectedIndex + 1, selectedArticle);
            shoppingListTV.getSelectionModel().select(selectedIndex + 1);
            saveShoppingList();
        }
    }


    @FXML
    private void onAddRecipe(){
        //Todo: Hinzufügen eines Rezepts zur Einkaufsliste
    }

    @FXML
    private void onSelectRecipe(){
        //Todo: Auswählen eines Rezepts aus der Rezeptsammlung
    }

    private void saveShoppingList(){
        File file = new File("data/shopping_list.csv");
        saveArticles(shoppingList, file);
    }


}
