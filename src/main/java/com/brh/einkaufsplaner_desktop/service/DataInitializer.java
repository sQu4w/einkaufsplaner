package com.brh.einkaufsplaner_desktop.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Initialisieren der Dateien.
 * Wird beim Start der Anwendung aufgerufen.
 */
public class DataInitializer {

    // Datenordner und Dateinamen als Konstanten
    private static final String DATA_FOLDER = "data";
    private static final String SHOPPING_FILE = DATA_FOLDER + "/shopping_list.csv";
    private static final String RECIPE_FILE = DATA_FOLDER + "/recipes.json";

    /**
     * Wird beim Start aufgerufen. Prüft und erstellt (falls nötig) die Dateien.
     */
    public static void initializeFiles() {
        new File(DATA_FOLDER).mkdirs(); // Ordner erstellen, falls nicht vorhanden
        ensureFileExists(SHOPPING_FILE, "");
        ensureFileExists(RECIPE_FILE, "[]");
    }

    /**
     * Erstellt eine Datei mit Inhalt, falls sie nicht existiert.
     */
    private static void ensureFileExists(String filename, String content) {
        File file = new File(filename);

        // Nur wenn Datei noch nicht existiert
        if (file.exists()) return;

        try {
            if (file.createNewFile() && content != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(content);
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Datei '" + filename + "':");
            e.printStackTrace();
        }
    }
}
