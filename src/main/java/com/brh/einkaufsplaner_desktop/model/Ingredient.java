package com.brh.einkaufsplaner_desktop.model;

import javafx.beans.property.*;

public class Ingredient {

    private final StringProperty name;
    private final DoubleProperty amount;
    private final StringProperty unit;

    public Ingredient(String name, double amount, String unit) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
        this.unit = new SimpleStringProperty(unit);
    }

    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public DoubleProperty amountProperty() { return amount; }
    public double getAmount() { return amount.get(); }
    public void setAmount(double amount) { this.amount.set(amount); }

    public StringProperty unitProperty() { return unit; }
    public String getUnit() { return unit.get(); }
    public void setUnit(String unit) { this.unit.set(unit); }
}
