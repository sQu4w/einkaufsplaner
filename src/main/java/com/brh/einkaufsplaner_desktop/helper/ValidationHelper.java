package com.brh.einkaufsplaner_desktop.helper;

import javafx.scene.control.TextField;

import static com.brh.einkaufsplaner_desktop.helper.DialogHelper.errorDialog;
import static com.brh.einkaufsplaner_desktop.helper.DialogHelper.warningDialog;

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
            warningDialog("Eingabefehler",
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
     * @return Den Wert, wenn die Eingabe gültig ist, sonst null
     */
    public static Double validateNumber(TextField tf, String fieldName) {

        // holt den Text aus dem Textfeld und entfernt führende und nachfolgende Leerzeichen
        String text = tf.getText().trim();

        if (text.isEmpty()) {
            warningDialog("Eingabefehler", fieldName + " darf nicht leer sein.");
            tf.requestFocus();
            return null;
        }

        try {
            // Ersetzt Kommas durch Punkte, um die Eingabe in eine Zahl umzuwandeln
            text = text.replace(",", ".");
            double value = Double.parseDouble(text);

            if (value <= 0) {
                warningDialog("Ungültige Eingabe", fieldName + " muss größer als 0 sein.");
                tf.requestFocus();
                return null;
            }

            return value;

        } catch (NumberFormatException e) {
            errorDialog("Ungültige Eingabe", fieldName + " muss eine gültige Zahl sein.");
            tf.requestFocus();
            return null;
        }
    }

}
