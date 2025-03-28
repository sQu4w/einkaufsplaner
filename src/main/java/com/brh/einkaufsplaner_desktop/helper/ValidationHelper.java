package com.brh.einkaufsplaner_desktop.helper;

import javafx.scene.control.TextField;

/**
 * Hilfsklasse zur Validierung von Benutzereingaben.
 */
public class ValidationHelper {

    /**
     * Prüft, ob ein Textfeld leer oder nur aus Leerzeichen besteht.
     *
     * @param tf Textfeld, das geprüft werden soll
     * @param fieldName Der angezeigte Feldname für den Dialog
     * @return false, wenn das Feld leer ist, sonst true
     */
    public static boolean validateTextField(TextField tf, String fieldName) {
        if (tf.getText() == null || tf.getText().trim().isEmpty()) {
            DialogHelper.warningDialog("Eingabefehler",
                    fieldName + " darf nicht leer sein.");
            return false;
        }
        return true;
    }

    /**
     * Validiert die Eingabe von Zahlen.
     *
     * @param tf Das Textfeld mit der Eingabe
     * @param fieldName Der Feldname für Fehlermeldungen
     * @return Der geparste Double-Wert oder null bei Fehler
     */
    public static Double validateNumber(TextField tf, String fieldName) {
        try {
            double value = Double.parseDouble(tf.getText());
            if (value <= 0) {
                DialogHelper.warningDialog("Ungültige Eingabe",
                        fieldName + " darf nicht 0 sein.");
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            DialogHelper.errorDialog("Ungültige Eingabe",
                    fieldName + " muss eine gültige Zahl sein.");
            return null;
        }
    }
}
