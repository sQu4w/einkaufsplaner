package com.brh.einkaufsplaner_desktop.service;

import com.brh.einkaufsplaner_desktop.model.Ingredient;
import com.brh.einkaufsplaner_desktop.model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RecipeService {

    public static void saveRecipes(List<Recipe> recipes) {
        JSONArray recipeArray = new JSONArray();

        for (Recipe recipe : recipes) {
            JSONObject recipeObj = getJsonObject(recipe);
            recipeArray.put(recipeObj);
        }

        try (FileWriter writer = new FileWriter("data/recipes.json")) {
            writer.write(recipeArray.toString(2)); // schön formatiert mit 2er Einrückung
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Rezepte:");
            e.printStackTrace();
        }
    }

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
