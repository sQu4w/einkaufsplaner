package com.brh.einkaufsplaner_desktop.controller;
import com.brh.einkaufsplaner_desktop.helper.ValidationHelper;
import com.brh.einkaufsplaner_desktop.model.Article;
import com.brh.einkaufsplaner_desktop.model.Ingredient;
import com.brh.einkaufsplaner_desktop.model.Recipe;
import com.brh.einkaufsplaner_desktop.service.RecipeService;
import com.brh.einkaufsplaner_desktop.service.ShoppingListService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.brh.einkaufsplaner_desktop.helper.DialogHelper.*;

public class ShoppingListController {

    // Textfelder
    @FXML private TextField articleNameTF;
    @FXML private TextField articleAmountTF;
    @FXML private TextField articleUnitTF;
    @FXML private TextField selectServingsTF;

    // Tabelle für Artikel
    @FXML private TableView<Article> shoppingListTV;
    @FXML private TableColumn<Article, String> articleItemCol;
    @FXML private TableColumn<Article, Double> articleAmountCol;
    @FXML private TableColumn<Article, String> articleUnitCol;
    @FXML private TableColumn<Article, Boolean> articleBoughtCol;

    // Buttons
    @FXML private Button openRecipeManagementBtn;

    // Dropdowns
    @FXML private ComboBox<String> articleUnitCB;
    @FXML private ComboBox<String> selectRecipeCB;

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

        // Lädt die Rezepte in die ComboBox
        loadRecipesIntoComboBox();
    }

    /**
     * Fügt einen Artikel zur Einkaufsliste hinzu.
     */
    @FXML
    private void onAddArticle() {

        // Eingabefelder validieren
        if (!ValidationHelper.validateName(articleNameTF, "Artikelname")) return;
        if (!ValidationHelper.validateAmount(articleAmountTF)) return;
        if (!ValidationHelper.validateUnit(articleUnitTF)) return;

        // Werte extrahieren
        String name = articleNameTF.getText().trim();
        double amount = Double.parseDouble(articleAmountTF.getText().trim().replace(",", "."));
        String unit = articleUnitTF.getText().trim();

        // Artikel erstellen und zur Liste hinzufügen
        updateArticle(name, amount, unit);

        // Eingabefelder leeren
        articleNameTF.clear();
        articleAmountTF.clear();
        articleUnitTF.clear();
        articleNameTF.requestFocus();

        // Speicherung der Einkaufsliste
        saveShoppingList();
    }

    /**
     * Löscht einen ausgewählten Artikel aus der Einkaufsliste.
     */
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

    /**
     * Löscht alle Artikel aus der Einkaufsliste.
     */
    @FXML
    private void onDeleteShoppingList() {
        boolean confirmed = confirmDialog(
                "Einkaufsliste löschen",
                "Möchtest du wirklich die gesamte Einkaufsliste löschen?");

        if (confirmed) {
            shoppingList.clear();
            shoppingListTV.getItems().clear();// View aktualisieren
            saveShoppingList();
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

    /**
     * Fügt die Zutaten eines ausgewählten Rezepts zur Einkaufsliste hinzu.
     * Wenn der Benutzer eine Portion angegeben hat, wird diese verwendet,
     * andernfalls wird die Basisportion des Rezepts verwendet.
     */
    @FXML
    private void onAddRecipeToShoppingList() {
        String selectedRecipeName = selectRecipeCB.getValue();
        String servingsText = selectServingsTF.getText().trim();

        // Wenn kein Rezept ausgewählt ist → Dialog anzeigen und abbrechen
        if (selectedRecipeName == null || selectedRecipeName.isEmpty()) {
            warningDialog("Kein Rezept ausgewählt", "Bitte wähle ein Rezept aus der Liste.");
            return;
        }

        // Rezept aus der Liste laden
        List<Recipe> allRecipes = RecipeService.loadRecipes();
        Recipe selectedRecipe = null;

        for (Recipe r : allRecipes) {
            if (r.getName().equalsIgnoreCase(selectedRecipeName)) {
                selectedRecipe = r;
                break;
            }
        }

        if (selectedRecipe == null) {
            errorDialog("Rezept nicht gefunden", "Das Rezept konnte nicht geladen werden.");
            return;
        }

        // Basisportionen aus dem ausgewählten Rezept holen
        int servings = selectedRecipe.getBaseServings();
        if (!servingsText.isEmpty()) {
            try {
                servings = Integer.parseInt(servingsText);
                if (servings <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errorDialog("Ungültige Eingabe", "Bitte gib eine gültige Anzahl an Portionen ein oder lasse das Feld leer.");
                return;
            }
        }

        // Zutaten skalieren und zur Einkaufsliste hinzufügen
        List<Ingredient> ingredients = selectedRecipe.getScaledIngredients(servings);
        for (Ingredient ingredient : ingredients) {
            updateArticle(ingredient.getName(), ingredient.getAmount(), ingredient.getUnit());
        }

        // Einkaufsliste speichern
        saveShoppingList();
        infoDialog("Rezept übernommen", "Die Zutaten wurden zur Einkaufsliste hinzugefügt.");

        // Felder zurücksetzen
        selectServingsTF.clear();
        selectRecipeCB.getSelectionModel().clearSelection();
    }


    /**
     * Lädt die Rezepte in die ComboBox.
     */
    private void loadRecipesIntoComboBox() {
        // Rezepte aus der JSON-Datei laden
        List<Recipe> recipes = RecipeService.loadRecipes();

        // Erstelle eine Liste für die Rezeptnamen
        List<String> recipeNames = new ArrayList<>();

        // Iteriere über die Rezepte und füge die Namen zur Liste hinzu
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
        }

        // Aktualisiere der Rezepte in der ComboBox durch den Beobachter
        selectRecipeCB.setItems(FXCollections.observableArrayList(recipeNames));
    }

    /**
     * Speichert die Einkaufsliste in einer CSV-Datei.
     */
    private void saveShoppingList(){
        File file = new File("data/shopping_list.csv");
        ShoppingListService.saveArticles(shoppingList, file);
    }

    /**
     * Aktualisiert die Einkaufsliste: Wenn ein Artikel mit gleichem Namen und gleicher Einheit
     * bereits vorhanden ist, wird die Menge addiert. Ansonsten wird er neu hinzugefügt.
     *
     * @param name   Name des Artikels
     * @param amount Menge des Artikels
     * @param unit   Einheit des Artikels
     */
    private void updateArticle(String name, double amount, String unit) {
        for (Article article : shoppingList) {
            if (article.getName().equalsIgnoreCase(name) &&
                    article.getUnit().equalsIgnoreCase(unit)) {
                article.setAmount(article.getAmount() + amount);
                return;
            }
        }

        // Artikel existiert noch nicht → neu hinzufügen
        shoppingList.add(new Article(false, name, amount, unit));
    }
}
