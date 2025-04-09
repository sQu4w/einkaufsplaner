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
    private String preparation;

    public Recipe(String name, int baseServings, List<Ingredient> ingredients, String preparation) {
        this.name = name;
        this.baseServings = baseServings;
        this.ingredients = ingredients;
        this.preparation = preparation;
    }

    // Getter & Setter für Name, Grundportion und Zutaten
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getBaseServings() {return baseServings;}
    public void setBaseServings(int baseServings) {this.baseServings = baseServings;}

    public List<Ingredient> getIngredients() {return ingredients;}
    public void setIngredients(List<Ingredient> ingredients) {this.ingredients = ingredients;}

    public String getPreparation() {return preparation;}
    public void setPreparation(String preparation) {this.preparation = preparation;}
    /**
     * Skaliert die Zutatenliste auf die gewünschte Portionen für die Einkaufsliste
     * @param servings Die gewünschte Portionen
     * @return Die skalierte Zutatenliste
     */
    public List<Ingredient> getScaledIngredients(int servings) {

        // Ermittelt den Faktor, um die Zutatenliste zu skalieren
        double factor = (double) servings / baseServings;
        List<Ingredient> scaledList = new ArrayList<>();

        // Skaliert jede Zutat in der Liste
        for (Ingredient ing : ingredients){

            // Skaliere und runde immer auf die nächste Zahl
            double scaledAmount = Math.ceil(ing.getAmount() * factor);

            // Erstelle eine neue Zutat mit dem skalierten Wert
            Ingredient scaled = new Ingredient(
                    ing.getName(),
                    scaledAmount,
                    ing.getUnit()
            );
            // Fügt die skalierte Zutat der Liste hinzu
            scaledList.add(scaled);
        }
        return scaledList;
    }

}

