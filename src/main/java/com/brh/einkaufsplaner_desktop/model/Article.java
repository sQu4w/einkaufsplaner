package com.brh.einkaufsplaner_desktop.model;

/**
 * Klasse für Artikel in der Einkaufsliste.
 */
public class Article {
    private boolean bought;
    private String name;
    private double amount;
    private String unit;

    /**
     * Konstruktor für einen Artikel.
     * @param bought Gibt an, ob der Artikel bereits gekauft wurde.
     * @param name Name des Artikels.
     * @param amount Menge des Artikels.
     * @param unit Einheit des Artikels.
     */
    public Article(boolean bought, String name, double amount, String unit) {
        this.bought = bought;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    // Getter & Setter für Status, Name, Menge und Einheit
    public boolean isBought() { return bought; }
    public void setBought(boolean bought) { this.bought = bought; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}



