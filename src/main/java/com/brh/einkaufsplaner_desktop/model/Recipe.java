package com.brh.einkaufsplaner_desktop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse für Rezepte
 */
public class Recipe {
    private String name;
    private int baseServings;
    private List<Ingredient> ingredients;

    public Recipe(String name, int baseServings, List<Ingredient> ingredients) {
        this.name = name;
        this.baseServings = baseServings;
        this.ingredients = ingredients;
    }

    // Getter & Setter für Name, Grundportion und Zutaten
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getBaseServings() {return baseServings;}
    public void setBaseServings(int baseServings) {this.baseServings = baseServings;}

    public List<Ingredient> getIngredients() {return ingredients;}
    public void setIngredients(List<Ingredient> ingredients) {this.ingredients = ingredients;}

    /**
     * Skaliert die Zutatenliste auf die gewünschte Portionen für die Einkaufsliste
     * @param servings Die gewünschte Portionen
     * @return Die skalierte Zutatenliste
     */
    public List<Ingredient> getScaledIngredients(int servings) {
        double factor = (double) servings / baseServings;
        List<Ingredient> scaledList = new ArrayList<>();

        for (Ingredient ing : ingredients){
            Ingredient scaled = new Ingredient(
                    ing.getName(),
                    ing.getAmount() * factor,
                    ing.getUnit()
            );
            scaledList.add(scaled);
        }
        return scaledList;
    }

}

