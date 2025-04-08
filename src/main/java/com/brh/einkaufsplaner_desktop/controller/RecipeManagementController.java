package com.brh.einkaufsplaner_desktop.controller;

import com.brh.einkaufsplaner_desktop.helper.DialogHelper;
import com.brh.einkaufsplaner_desktop.helper.ValidationHelper;
import com.brh.einkaufsplaner_desktop.model.Ingredient;
import com.brh.einkaufsplaner_desktop.model.Recipe;
import com.brh.einkaufsplaner_desktop.service.RecipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecipeManagementController {

    @FXML private Button openShoppingListBtn;

    // Rezeptfelder
    @FXML private TextField recipeNameTF;
    @FXML private TextField servingsTF;
    @FXML private TextArea preparationTA;

    // Zutatenfelder
    @FXML private TextField ingredientNameTF;
    @FXML private TextField ingredientAmountTF;
    @FXML private TextField ingredientUnitTF;

    // TableView für Zutaten
    @FXML private TableView<Ingredient> ingredientListTV;
    @FXML private TableColumn<Ingredient, String> ingredientItemCol;
    @FXML private TableColumn<Ingredient, Double> ingredientAmountCol;
    @FXML private TableColumn<Ingredient, String> ingredientUnitCol;

    // Rezeptliste
    @FXML private ListView<String> recipeLV;

    // Intern genutzte Daten
    private final ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
    private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();

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

    /**
     * Initialisiert die Tabelle und lädt vorhandene Rezepte.
     */
    @FXML
    private void initialize() {
        ingredientListTV.setItems(ingredientList);

        ingredientItemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ingredientUnitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

        // TODO: JSON-Datei laden und Rezepte in ListView anzeigen
    }

    /**
     * Erstellt ein leeres Rezept (Felder leeren).
     */
    @FXML
    private void onAddRecipe() {
        recipeNameTF.clear();
        servingsTF.clear();
        preparationTA.clear();
        ingredientList.clear();
        recipeLV.getSelectionModel().clearSelection();
    }

    @FXML
    private void onEditRecipe(){
        //Todo: Bearbeiten eines Rezeptes
    }

    @FXML
    private void onDeleteRecipe(){
        //Todo: Löschen eines Rezeptes
    }

    @FXML
    private void onSaveRecipe() {

        // Name des Rezepts und Zubereitungstext holen
        String name = recipeNameTF.getText().trim();
        String preparation = preparationTA.getText().trim();

        // Validierung der Eingabefelder
        if (!ValidationHelper.validateName(recipeNameTF)) return;
        if (!ValidationHelper.validateAmount(servingsTF)) return;

        int baseServings = (int) Double.parseDouble(servingsTF.getText().trim().replace(",", "."));

        if (ingredientList.isEmpty()) {
            DialogHelper.warningDialog("Keine Zutaten", "Bitte füge mindestens eine Zutat hinzu.");
            return;
        }

        // Die aktuelle Zutatenliste wird kopiert
        // Warum? Weil ingredientList die Arbeitsliste im Formular ist
        // Wenn man danach ein neues Rezept erstellt, wird diese Liste wieder geleert
        // Damit das gerade gespeicherte Rezept seine Zutaten trotzdem behält,
        // machen wir hier eine Kopie der Liste
        // So bleibt das Rezept, wie es jetzt ist – unabhängig von späteren Änderungen
        List<Ingredient> ingredientsCopy = new ArrayList<>(ingredientList);

        // Neues Rezept mit Name, Portionen, Zutaten und Zubereitungstext erstellen
        Recipe recipe = new Recipe(name, baseServings, ingredientsCopy, preparation);

        // Rezept zur internen Liste und zur ListView hinzufügen
        recipes.add(recipe);
        recipeLV.getItems().add(recipe.getName());

        // Rezept in die JSON-Datei speichern
        RecipeService.saveRecipes(recipes);

        // Eingabefelder und Tabelle zurücksetzen (für ein neues Rezept)
        onAddRecipe();

        DialogHelper.infoDialog("Rezept gespeichert", "Das Rezept wurde erfolgreich gespeichert.");
    }



    @FXML
    private void onServingsInfo(){
        DialogHelper.infoDialog("Portionen",
                "Hier können die Basisportionen für das Rezept festlegen werden.");
    }

    @FXML
    private void onAddIngredient(){

        if (!ValidationHelper.validateName(ingredientNameTF)) return;
        if (!ValidationHelper.validateAmount(ingredientAmountTF)) return;
        if (!ValidationHelper.validateUnit(ingredientUnitTF)) return;

        String name = ingredientNameTF.getText().trim();
        double amount = Double.parseDouble(ingredientAmountTF.getText().trim().replace(",", "."));
        String unit = ingredientUnitTF.getText().trim();

        Ingredient ingredient = new Ingredient(name, amount, unit);
        ingredientList.add(ingredient);

        // Eingabefelder leeren
        ingredientNameTF.clear();
        ingredientAmountTF.clear();
        ingredientUnitTF.clear();
        ingredientNameTF.requestFocus();
    }

    @FXML
    private void onDeleteIngredient() {
        Ingredient selectedIngredient = ingredientListTV.getSelectionModel().getSelectedItem();

        if (selectedIngredient == null) {
            DialogHelper.warningDialog("Keine Auswahl", "Bitte wähle eine Zutat aus der Liste aus, um sie zu löschen.");
            return;
        }

        boolean confirmed = DialogHelper.confirmDeleteDialog(selectedIngredient.getName());

        if (confirmed) {
            ingredientListTV.getItems().remove(selectedIngredient);
        }
    }

}
