package com.brh.einkaufsplaner_desktop.service;

import com.brh.einkaufsplaner_desktop.helper.DialogHelper;
import com.brh.einkaufsplaner_desktop.model.Article;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse kümmert sich um das Laden und Speichern der Einkaufsliste (CSV).
 */
public class ShoppingListService {

    // Name der CSV-Datei in einer Konstante speichern
    private static final String FILE_NAME = "data/shopping_list.csv";

    /**
     * Lädt alle Artikel aus der CSV-Datei und gibt sie als Liste zurück.
     * @return Liste mit geladenen Artikeln
     */
    public static List<Article> loadArticles() {

        // Liste für Artikel erstellen
        List<Article> articles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            // Jede Zeile der CSV-Datei einlesen
            while ((line = reader.readLine()) != null) {

                // Zeile am Komma aufteilen
                String[] parts = line.split(";");

                // Nut gültige Zeilen mit genau 4 Inhalten verarbeiten
                if (parts.length == 4) {

                    // Teile der Zeile in die passenden Datentypen umwandeln und trimmen
                    boolean bought = Boolean.parseBoolean(parts[0].trim());
                    String name = parts[1].trim();
                    double amount = Double.parseDouble(parts[2].trim());
                    String unit = parts[3].trim();

                    // Neuen Artikel erstellen und zur Liste hinzufügen
                    articles.add(new Article(bought, name, amount, unit));
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Einkaufsliste:");
            e.printStackTrace();
        }

        return articles;
    }

    /**
     * Speichert die Artikel in die CSV-Datei.
     * @param articles Liste mit Artikeln
     * @param file Datei, in die gespeichert werden soll
     */
    public static void saveArticles(List<Article> articles, File file) {

        // Überprüfen, ob die Datei existiert, und sie gegebenenfalls erstellen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Article article : articles) {
                String line = article.isBought() + ";" +
                        article.getName() + ";" +
                        article.getAmount() + ";" +
                        article.getUnit();
                // Zeile in die CSV-Datei schreiben
                writer.write(line);
                // Zeilenumbruch hinzufügen
                writer.newLine();
            }
        } catch (IOException e) {
            DialogHelper.errorDialog("Fehler beim Speichern", "Die Einkaufsliste konnte nicht gespeichert werden.");
            e.printStackTrace();
        }
    }

}
