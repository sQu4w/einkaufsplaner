package com.brh.einkaufsplaner_desktop.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Initialisieren der Dateien.
 * Wird beim Start der Anwendung aufgerufen.
 */
public class DataInitializer {

    // Hier werden die wichtigen Pfade und Dateinamen definiert
    private static final String DATA_FOLDER = "data";
    private static final String SHOPPING_FILE = DATA_FOLDER + "/shopping_list.csv";
    private static final String RECIPE_FILE = DATA_FOLDER + "/recipes.json";

    /**
     * Wird beim Start aufgerufen. Prüft und erstellt (falls nötig) die Dateien.
     */
    public static void initializeFiles() {
        // Datenordner erstellen, falls nicht vorhanden
        new File(DATA_FOLDER).mkdirs();

        // Dateien erstellen, falls nicht vorhanden
        ensureFileExists(SHOPPING_FILE, "");
        ensureFileExists(RECIPE_FILE, "[]");
    }

    /**
     * Erstellt eine Datei mit Standardinhalt,
     * aber nur wenn sie noch nicht existiert.
     *
     * @param filename Name der zu erstellenden Datei
     * @param content Standardinhalt für die neue Datei
     */
    private static void ensureFileExists(String filename, String content) {
        File file = new File(filename);

        // Wenn die Datei bereits existiert, nichts tun
        if (file.exists()) return;

        try {
            // Datei erstellen und mit Inhalt füllen
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
