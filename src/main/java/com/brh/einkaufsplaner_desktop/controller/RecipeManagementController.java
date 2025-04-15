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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecipeManagementController {

    // Buttons
    @FXML private Button openShoppingListBtn;
    @FXML private Button addIngredientBtn;
    @FXML private Button deleteIngredientBtn;
    @FXML private Button saveRecipeBtn;
    @FXML private Button servingsInfoBtn;

    // Rezeptfelder
    @FXML private TextField recipeNameTF;
    @FXML private TextField servingsTF;
    @FXML private TextArea preparationTA;

    // Zutatenfelder
    @FXML private TextField ingredientNameTF;
    @FXML private TextField ingredientAmountTF;
    @FXML private TextField ingredientUnitTF;

    // Tabelle für Zutaten
    @FXML private TableView<Ingredient> ingredientListTV;
    @FXML private TableColumn<Ingredient, String> ingredientItemCol;
    @FXML private TableColumn<Ingredient, Double> ingredientAmountCol;
    @FXML private TableColumn<Ingredient, String> ingredientUnitCol;

    // Rezeptliste
    @FXML private ListView<String> recipeLV;

    // Intern genutzte Daten um die Rezepte und Zutaten zu speichern
    private final ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
    private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();

    // Rezept, das bearbeitet wird (null, wenn kein Rezept bearbeitet wird)
    private Recipe recipeInEdit = null;


    /**
     * Öffnet die Ansicht für die Einkaufsliste
     */
    @FXML
    private void goToShoppingList() throws IOException {

        if (!confirmCancelRecipe()) return;

        // Lade die FXML-Datei für die Einkaufsliste
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/brh/einkaufsplaner_desktop/shoppinglist.fxml"));
        Parent view = loader.load();

        // Erzeugt das Fenster und setzt die Szene auf die geladene FXML-Datei
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

        // Rezepte laden
        List<Recipe> loadedRecipes = RecipeService.loadRecipes();
        recipes.addAll(loadedRecipes);

        // Rezepte in ListView anzeigen
        for (Recipe r : loadedRecipes){
            recipeLV.getItems().add(r.getName());
        }

        // Felder zu Beginn deaktivieren
        setInputFieldsDisabled(true);

        // Wenn der Benutzer doppelt auf ein Rezept in der Liste klickt,
        // wird die Methode onDoubleClickRecipe aufgerufen, um die Rezeptdetails anzuzeigen.
        recipeLV.setOnMouseClicked(this::onDoubleClickRecipe);
    }

    /**
     * Erstellt ein leeres Rezept (Felder leeren).
     */
    @FXML
    private void onAddRecipe() {

        // Felder zurücksetzen
        resetRecipeForm();

        // Aktivieren der Eingabefelder
        setInputFieldsDisabled(false);
    }

    /**
     * Lädt ein Rezept in die Eingabefelder zur Bearbeitung.
     */
    @FXML
    private void onEditRecipe() {
        if (!confirmCancelRecipe()) return;
        String selectedName = recipeLV.getSelectionModel().getSelectedItem();

        if (selectedName == null) {
            DialogHelper.warningDialog("Keine Auswahl",
                    "Bitte wähle ein Rezept aus der Liste aus, um es zu bearbeiten.");
            return;
        }

        // Suche das Rezept mit diesem Namen
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(selectedName)) {

                // Felder mit den Daten des Rezepts befüllen
                recipeNameTF.setText(recipe.getName());
                servingsTF.setText(String.valueOf(recipe.getBaseServings()));
                preparationTA.setText(recipe.getPreparation());

                // Zutatenliste setzen (kopiert)
                ingredientList.setAll(recipe.getIngredients());

                // Bearbeitetes Rezept merken
                recipeInEdit = recipe;

                // aktivieren der Eingabefelder
                setInputFieldsDisabled(false);
                return;
            }
        }

        // Wenn das Rezept nicht gefunden wurde
        DialogHelper.errorDialog("Fehler",
                "Das Rezept konnte nicht geladen werden.");
    }


    @FXML
    private void onDeleteRecipe() {
        if (!confirmCancelRecipe()) return;
        String selectedName = recipeLV.getSelectionModel().getSelectedItem();

        if (selectedName == null) {
            DialogHelper.warningDialog("Keine Auswahl",
                    "Bitte wähle ein Rezept aus der Liste aus, um es zu löschen.");
            return;
        }

        boolean confirmed = DialogHelper.confirmDeleteDialog(selectedName);

        if (!confirmed) return;

        // Rezept aus der internen Liste entfernen
        Recipe toRemove = null;
        for (Recipe r : recipes) {
            if (r.getName().equals(selectedName)) {
                toRemove = r;
                break;
            }
        }

        if (toRemove != null) {
            recipes.remove(toRemove);
            recipeLV.getItems().remove(selectedName);

            // Datei aktualisieren
            RecipeService.saveRecipes(recipes);
        } else {
            DialogHelper.errorDialog("Fehler",
                    "Das Rezept konnte nicht gefunden werden.");
        }

        // Felder zurücksetzen, falls das gelöschte Rezept gerade bearbeitet wurde
        onAddRecipe();

        // Felder deaktivieren
        setInputFieldsDisabled(true);

        // Rezept, das bearbeitet wurde, zurücksetzen
        recipeInEdit = null;
    }


    /**
     * Speichert das Rezept in der Liste und aktualisiert die Datei.
     */
    @FXML
    private void onSaveRecipe() {
        String name = recipeNameTF.getText().trim();
        String preparation = preparationTA.getText().trim();

        // Eingabefelder validieren
        if (!ValidationHelper.validateName(recipeNameTF, "Rezeptname")) return;
        if (!ValidationHelper.validateServings(servingsTF)) return;

        if (ingredientList.isEmpty()) {
            DialogHelper.warningDialog("Keine Zutaten",
                    "Bitte füge mindestens eine Zutat hinzu.");
            return;
        }

        // Werte konvertieren und extrahieren
        int baseServings = (int) Double.parseDouble(
                servingsTF.getText().trim().replace(",", "."));

        // Zutatenliste kopieren
        List<Ingredient> ingredientsCopy = new ArrayList<>(ingredientList);

        // Falls ein bestehendes Rezept bearbeitet wird
        if (recipeInEdit != null) {

            // Rezeptname aktualisieren
            String oldName = recipeInEdit.getName();

            // Bestehendes Rezept aktualisieren
            recipeInEdit.setName(name);
            recipeInEdit.setBaseServings(baseServings);
            recipeInEdit.setPreparation(preparation);
            recipeInEdit.setIngredients(ingredientsCopy);

            // Namen in der ListView aktualisieren
            int index = recipeLV.getItems().indexOf(oldName);
            if (index >= 0) {
                recipeLV.getItems().set(index, name);
            }

            DialogHelper.infoDialog("Rezept aktualisiert",
                    "Das Rezept wurde erfolgreich aktualisiert.");
        } else {

            // Neues Rezept erstellen
            Recipe recipe = new Recipe(name, baseServings, ingredientsCopy, preparation);
            recipes.add(recipe);
            recipeLV.getItems().add(recipe.getName());

            DialogHelper.infoDialog("Rezept gespeichert",
                    "Das Rezept wurde erfolgreich gespeichert.");
        }

        // Rezepte nach dem Speichern aktualisieren
        RecipeService.saveRecipes(recipes);

        // Eingabefelder zurücksetzen
        resetRecipeForm();

        // Felder zurücksetzen und deaktivieren
        setInputFieldsDisabled(true);

        // Rezept, das bearbeitet wurde, zurücksetzen
        recipeInEdit = null;
    }

    /**
     * Zeigt eine Info-Box mit Informationen zu den Portionen an.
     */
    @FXML
    private void onServingsInfo(){
        DialogHelper.infoDialog("Portionen",
                "Hier können die Basisportionen für das Rezept festlegen werden.");
    }

    /**
     * Fügt eine Zutat zur Zutatenliste hinzu.
     */
    @FXML
    private void onAddIngredient(){

        // Eingabefelder validieren
        if (!ValidationHelper.validateName(ingredientNameTF, "Zutat")) return;
        if (!ValidationHelper.validateAmount(ingredientAmountTF)) return;
        if (!ValidationHelper.validateUnit(ingredientUnitTF)) return;

        // Werte extrahieren
        String name = ingredientNameTF.getText().trim();
        double amount = Double.parseDouble(
                ingredientAmountTF.getText().trim().replace(",", "."));
        String unit = ingredientUnitTF.getText().trim();

        // Zutat erstellen und zur Liste hinzufügen
        Ingredient ingredient = new Ingredient(name, amount, unit);
        ingredientList.add(ingredient);

        // Eingabefelder leeren
        ingredientNameTF.clear();
        ingredientAmountTF.clear();
        ingredientUnitTF.clear();
        ingredientNameTF.requestFocus();
    }

    /**
     * Löscht die ausgewählte Zutat aus der Liste.
     */
    @FXML
    private void onDeleteIngredient() {
        Ingredient selectedIngredient = ingredientListTV.getSelectionModel().getSelectedItem();

        if (selectedIngredient == null) {
            DialogHelper.warningDialog("Keine Auswahl",
                    "Bitte wähle eine Zutat aus der Liste aus, um sie zu löschen.");
            return;
        }

        boolean confirmed = DialogHelper.confirmDeleteDialog(selectedIngredient.getName());

        if (confirmed) {
            ingredientListTV.getItems().remove(selectedIngredient);
        }
    }

    /**
     * Zeigt die Rezeptdetails in einem Dialog an, wenn der Benutzer doppelt auf ein Rezept klickt.
     *
     * @param event Das MouseEvent-Objekt
     */
    @FXML
    private void onDoubleClickRecipe(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedName = recipeLV.getSelectionModel().getSelectedItem();

            if (selectedName == null) return;

            for (Recipe recipe : recipes) {
                if (recipe.getName().equals(selectedName)) {

                    // Rezeptdetails zusammenbauen
                    StringBuilder details = new StringBuilder();
                    details.append("Name: ").append(recipe.getName()).append("\n");
                    details.append("Portionen: ").append(recipe.getBaseServings()).append("\n\n");
                    details.append("Zutaten:\n");

                    for (Ingredient ing : recipe.getIngredients()) {
                        details.append(String.format("► %s %.2f %s\n",
                                ing.getName(),
                                ing.getAmount(),
                                ing.getUnit()));
                    }

                    details.append("\nZubereitung:\n").append(recipe.getPreparation());

                    // Dialog anzeigen
                    DialogHelper.recipeDetailsDialog("Rezeptdetails", details.toString());
                    break;
                }
            }
        }
    }

    /*
     * Bestätigt, ob der Benutzer das Rezept wirklich verwerfen möchte.
     * Wenn ja, werden die Eingabefelder zurückgesetzt und deaktiviert.
     *
     * @return true, wenn das Rezept verworfen werden soll, sonst false
     */
    private boolean confirmCancelRecipe() {
        if (recipeInEdit == null && recipeNameTF.getText().trim().isEmpty()) {
            return true;
        }

        boolean confirmed = DialogHelper.confirmDialog(
                "Rezept verwerfen?",
                "Willst du das angefangene Rezept wirklich verwerfen?");

        if (confirmed) {
            resetRecipeForm(); // Felder leeren & deaktivieren
            return true;
        }

        return false;
    }


    /**
     * Setzt die Eingabefelder für Rezepte und Zutaten zurück.
     */
    private void resetRecipeForm() {

        // Eingabefelder zurücksetzen
        recipeNameTF.clear();
        servingsTF.clear();
        preparationTA.clear();
        ingredientList.clear();
        recipeLV.getSelectionModel().clearSelection();

        // Rezept, das bearbeitet wurde, zurücksetzen
        recipeInEdit = null;

        // Deaktivieren der Eingabefelder
        setInputFieldsDisabled(true);
    }

    /*
     * Diese Methode wird aufgerufen, wenn der Benutzer die Rezeptliste ändert.
     * Sie aktiviert oder deaktiviert die Eingabefelder für Rezepte und Zutaten,
     * je nachdem, ob ein Rezept hinzugefügt oder bearbeitet wird.
     *
     * @param disabled true, um die Eingabefelder zu deaktivieren, false, um sie zu aktivieren
     */
    private void setInputFieldsDisabled(boolean disabled) {

        // Rezeptfelder aktivieren/deaktivieren
        recipeNameTF.setDisable(disabled);
        servingsTF.setDisable(disabled);
        preparationTA.setDisable(disabled);

        // Zutatenfelder aktivieren/deaktivieren
        ingredientNameTF.setDisable(disabled);
        ingredientAmountTF.setDisable(disabled);
        ingredientUnitTF.setDisable(disabled);
        ingredientListTV.setDisable(disabled);

        // Buttons aktivieren/deaktivieren
        addIngredientBtn.setDisable(disabled);
        deleteIngredientBtn.setDisable(disabled);
        saveRecipeBtn.setDisable(disabled);
        servingsInfoBtn.setDisable(disabled);
    }
}
