package com.brh.einkaufsplaner_desktop.model;

import java.util.List;

/**
 * Klasse für Zutaten in der Rezeptverwaltung
 */
public class Ingredient {
    private String name;
    private double amount;
    private String unit;

    /**
     * Konstruktor für eine Zutat
     * @param name Name der Zutat
     * @param amount Menge der Zutat
     * @param unit Einheit der Zutat
     */
    public Ingredient(String name, double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    // Getter & Setter für Name, Menge und Einheit
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getUnit() {return unit;}
    public void setUnit(String unit) {this.unit = unit;}
}
