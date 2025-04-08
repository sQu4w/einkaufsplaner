package com.brh.einkaufsplaner_desktop.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Hilfsklasse für die Anzeige von JavaFX-Dialogen.
 */
public class DialogHelper {

    /**
     * Zeigt einen Informationsdialog.
     * @param title Titel des Dialogs
     * @param message Nachricht im Dialog
     */
    public static void infoDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Zeigt einen Warn-Dialog.
     * @param title Titel des Dialogs
     * @param message Nachricht im Dialog
     */
    public static void warningDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Zeigt einen Fehlermeldungsdialog.
     * @param title Titel des Dialogs
     * @param message Nachricht im Dialog
     */
    public static void errorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Zeigt einen einfachen Bestätigungsdialog mit den Buttons "OK" und "Abbrechen".
     *
     * @param title   Der Titel des Dialogfensters.
     * @param message Was dem Nutzer mitgeteilt werden soll.
     * @return true, wenn der Benutzer "OK" auswählt,
     * ansonsten false (bei "Abbrechen" oder Dialog schließen).
     */
    public static boolean confirmDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Ziegt den Dialog an und wartet auf die Auswahl des Benutzers
        Optional<ButtonType> result = alert.showAndWait();

        // Gibt true zurück, wenn der Benutzer "OK" ausgewählt hat
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Zeigt einen Bestätigungsdialog für das Löschen eines Elements
     *
     * @param itemName Der Name des zu löschenden Elements
     * @return gibt true zurück, wenn der Benutzer das Löschen bestätigt
     */
    public static boolean deleteDialog(String itemName){
        return confirmDialog(
                "Löschen bestätigen",
                "Sind Sie sicher, dass Sie " + itemName + " löschen möchten?");
    }

    /**
     * Zeigt einen Dialog mit drei Optionen:
     * - "Speichern": Die Änderungen sollen gespeichert werden
     * - "Verwerfen": Die Änderungen sollen verworfen werden
     * - "Abbrechen": Die Aktion wird abgebrochen, der Nutzer bleibt im aktuellen Zustand
     *
     * Beispiel:
     * Wenn der Nutzer z.B. ein Rezept verändert und die Anwendung geschlossen werden soll,
     * kann man mit dieser Methode fragen, ob die Änderungen gespeichert werden sollen.
     *
     * @param title   Der Titel des Dialogs
     * @param message Die eigentliche Frage oder Information im Dialog
     * @return Ein String mit dem Namen der gewählten Option ("Speichern", "Verwerfen" oder "Abbrechen")
     */
    public static String saveChangesDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Erstellung von drei Buttons für die Auswahl
        ButtonType saveButton = new ButtonType("Speichern");
        ButtonType discardButton = new ButtonType("Verwerfen");
        ButtonType cancelButton = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Die Standard-Buttons ("OK" und "Cancel") durch eigene ersetzen
        alert.getButtonTypes().setAll(saveButton, discardButton, cancelButton);

        // Zeigt den Dialog an und wartet auf die Benutzerentscheidung
        Optional<ButtonType> result = alert.showAndWait();

        // Gibt den Namen der gewählten Option zurück
        if (result.isPresent()) {
            if (result.get() == saveButton) {
                return "Speichern";
            } else if (result.get() == discardButton) {
                return "Verwerfen";
            } else {
                return "Abbrechen";
            }
        }
        return "Abbrechen";
    }

    /**
     * Zeigt einen Bestätigungsdialog für das Löschen eines Elements
     * (z.B. Artikel oder Rezept)
     *
     * @param itemName Das Element, das gelöscht werden soll
     * @return true, wenn der Benutzer das Löschen bestätigt, sonst false
     */
    public static boolean confirmDeleteDialog(String itemName) {
        return confirmDialog(
                "Löschen bestätigen",
                "Möchten Sie \"" + itemName + "\" wirklich löschen?"
        );
    }

}
