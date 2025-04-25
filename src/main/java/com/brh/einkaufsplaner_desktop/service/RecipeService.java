package com.brh.einkaufsplaner_desktop.service;
import com.brh.einkaufsplaner_desktop.helper.DialogHelper;
import com.brh.einkaufsplaner_desktop.model.Ingredient;
import com.brh.einkaufsplaner_desktop.model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    /**
     * Speichert die Rezepte in einer JSON-Datei.
     *
     * @param recipes Liste von Rezepten
     */
    public static void saveRecipes(List<Recipe> recipes) {

        // Erstelle ein JSON-Array für die Rezepte
        JSONArray recipeArray = new JSONArray();

        // Iteriere über die Rezepte und füge sie dem JSON-Array hinzu
        for (Recipe recipe : recipes) {
            JSONObject recipeObj = getJsonObject(recipe);
            recipeArray.put(recipeObj);
        }

        // Speichere das JSON-Array in der Datei
        try (FileWriter writer = new FileWriter("data/recipes.json")) {
            writer.write(recipeArray.toString(2));
        } catch (IOException e) {
            DialogHelper.errorDialog("Fehler beim Speichern der Rezepte",
                    "Die Rezeptdatei konnte nicht gespeichert werden.");
            e.printStackTrace();
        }
    }

    /**
     * Lädt Rezepte aus der JSON-Datei und gibt sie als Liste zurück.
     *
     * @return Liste von Rezepten
     */
    public static List<Recipe> loadRecipes() {

        // Erstelle eine leere Liste für die Rezepte
        List<Recipe> recipes = new ArrayList<>();

        try {
            // Lese den Inhalt der JSON-Datei und parse sie ans JSON-Array
            String content = Files.readString(Path.of("data/recipes.json"));
            JSONArray recipeArray = new JSONArray(content);

            // Iteriere über die Rezepte im JSON-Array
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipeObj = recipeArray.getJSONObject(i);

                // Erstelle ein Rezept-Objekt aus dem JSON-Objekt
                String name = recipeObj.getString("name");
                int baseServings = recipeObj.getInt("baseServings");
                String preparation = recipeObj.getString("preparation");

                // Erstelle eine Liste von Zutaten
                List<Ingredient> ingredients = new ArrayList<>();
                JSONArray ingredientsArray = recipeObj.getJSONArray("ingredients");

                // Iteriere über die Zutaten im JSON-Array
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ingObj = ingredientsArray.getJSONObject(j);
                    
                    // Erstelle ein Ingredient-Objekt aus dem JSON-Objekt
                    String ingName = ingObj.getString("name");
                    double amount = ingObj.getDouble("amount");
                    String unit = ingObj.getString("unit");

                    // Erstelle ein Ingredient-Objekt und füge es zur Zutatenliste hinzu
                    ingredients.add(new Ingredient(ingName, amount, unit));
                }

                // Füge das Rezept zur Liste hinzu
                recipes.add(new Recipe(name, baseServings, ingredients, preparation));
            }
        } catch (IOException e) {
            DialogHelper.errorDialog("Fehler beim Laden der Rezepte",
                    "Die Rezeptdatei konnte nicht geladen werden.");
            e.printStackTrace();
        }

        return recipes;
    }

    /**
     * Erstellt ein JSON-Objekt aus einem Rezept-Objekt.
     *
     * @param recipe Rezept-Objekt
     * @return JSON-Objekt
     */
    private static JSONObject getJsonObject(Recipe recipe) {

        JSONObject recipeObj = new JSONObject();
        recipeObj.put("name", recipe.getName());
        recipeObj.put("baseServings", recipe.getBaseServings());
        recipeObj.put("preparation", recipe.getPreparation());

        JSONArray ingredientsArray = new JSONArray();
        for (Ingredient ing : recipe.getIngredients()) {

            JSONObject ingObj = new JSONObject();
            ingObj.put("name", ing.getName());
            ingObj.put("amount", ing.getAmount());
            ingObj.put("unit", ing.getUnit());

            ingredientsArray.put(ingObj);
        }

        recipeObj.put("ingredients", ingredientsArray);
        return recipeObj;
    }
}


