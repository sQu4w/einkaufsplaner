package com.brh.einkaufsplaner_desktop.helper;
import javafx.scene.control.TextField;

/**
 * Hilfsklasse zur Validierung von Benutzereingaben.
 */
public class ValidationHelper {

    /**
     * Validiert den Artikelnamen: darf nicht leer sein, nur Buchstaben enthalten.
     */
    public static boolean validateName(TextField tf, String fieldName) {
        String text = tf.getText().trim();

        if (isNullOrBlank(text)) {
            DialogHelper.warningDialog("Eingabefehler", fieldName +
                    " darf nicht leer sein.");
            tf.requestFocus();
            return false;
        }

        if (!isLettersOnly(text)) {
            DialogHelper.warningDialog("Ungültige Eingabe",
                    fieldName + " darf nur Buchstaben enthalten.");
            tf.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Validiert die Menge: darf nicht leer sein, muss eine Zahl größer als 0 sein.
     *
     * @param tf das Textfeld, das die Menge enthält
     */
    public static boolean validateAmount(TextField tf) {

        // Lese den Text aus dem Textfeld und trimme Leerzeichen
        String text = tf.getText().trim();

        // Überprüfen, ob der Text null oder leer ist
        if (isNullOrBlank(text)) {
            DialogHelper.warningDialog("Eingabefehler",
                    "Menge darf nicht leer sein.");

            // Setze den Fokus zurück auf das Textfeld
            tf.requestFocus();
            return false;
        }

        try {
            // Ersetze Komma durch Punkt für die Umwandlung in eine Zahl
            text = text.replace(",", ".");
            double value = Double.parseDouble(text);

            if (value <= 0) {
                DialogHelper.warningDialog("Ungültige Eingabe",
                        "Menge muss größer als 0 sein.");
                tf.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {
            DialogHelper.errorDialog("Ungültige Eingabe",
                    "Menge muss eine gültige Zahl sein.");
            tf.requestFocus();
            return false;
        }
        // Wenn alle Überprüfungen bestanden sind, ist die Eingabe gültig
        return true;
    }

    /**
     * Validiert die Einheit: optionales Feld, aber falls ausgefüllt, nur Buchstaben erlaubt.
     */
    public static boolean validateUnit(TextField tf) {
        String text = tf.getText().trim();

        if (text.isEmpty()){
            DialogHelper.warningDialog("Eingabefehler",
                    "Einheit darf nicht leer sein.");
            tf.requestFocus();
            return false;
        }

        if (!isLettersOnly(text)) {
            DialogHelper.warningDialog("Ungültige Eingabe",
                    "Einheit darf nur Buchstaben enthalten.");
            tf.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Validiert eine positive ganze Zahl, z.B. für Portionen.
     */
    public static boolean validateServings(TextField tf) {
        String text = tf.getText().trim();

        if (isNullOrBlank(text)) {
            DialogHelper.warningDialog("Eingabefehler",
                    "Portionen dürfen nicht leer sein.");
            tf.requestFocus();
            return false;
        }

        try {
            int value = Integer.parseInt(text);
            if (value <= 0) {
                DialogHelper.warningDialog("Ungültige Eingabe",
                        "Portionen müssen größer als 0 sein.");
                tf.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {
            DialogHelper.errorDialog("Ungültige Eingabe",
                    "Portionen müssen eine ganze Zahl sein.");
            tf.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Überprüft, ob der Text null oder leer ist.
     *
     * @param text der zu überprüfende Text
     * @return true, wenn der Text null oder leer ist, andernfalls false
     */
    private static boolean isNullOrBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Überprüft, ob der Text nur Buchstaben (inkl. Umlaute) enthält.
     *
     * @param text der zu überprüfende Text
     * @return true, wenn der Text nur Buchstaben enthält, andernfalls false
     */
    private static boolean isLettersOnly(String text) {
        return text.matches("[a-zA-ZäöüÄÖÜß\\s]+");
    }
}
