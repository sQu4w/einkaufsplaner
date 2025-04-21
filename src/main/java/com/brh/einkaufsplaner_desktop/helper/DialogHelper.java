package com.brh.einkaufsplaner_desktop.helper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import java.util.Optional;

/**
 * Hilfsklasse für die Anzeige von JavaFX-Dialogen.
 */
public class DialogHelper {

    // Variable, um zu verfolgen, ob der Hinweis zur Zutatenangabe bereits angezeigt wurde
    private static boolean inputHint = false;

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

        // Zeigt den Dialog an und wartet auf die Auswahl des Benutzers
        Optional<ButtonType> result = alert.showAndWait();

        // Gibt true zurück, wenn der Benutzer "OK" ausgewählt hat
        return result.isPresent() && result.get() == ButtonType.OK;
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

    /**
     * Zeigt einen Dialog mit den Details eines Rezepts an.
     *
     * @param title   Titel des Dialogs
     * @param message Nachricht im Dialog
     */
    public static void recipeDetailsDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // TextArea für den Nachrichtentext
        TextArea textArea = new TextArea(message);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefRowCount(200); // Höhe steuern
        textArea.setPrefColumnCount(400); // Breite steuern
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        // Layout-Anpassung
        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 0);

        // Setzt den Inhalt des Dialogs
        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setPrefSize(600, 500); // Größe des Dialogs anpassen


        alert.showAndWait();
    }

    /**
     * Zeigt einen Hinweisdialog zur Zutatenangabe an.
     * Dieser Dialog wird nur einmal angezeigt.
     */
    public static void inputHintDialog() {
        if (!inputHint) {
            inputHint = true;
            infoDialog("Hinweis zur Zutatenangabe",
                    "Bitte achte darauf, die Einheiten und Namen deiner Zutaten möglichst einheitlich einzugeben.\n\n" +
                            "Nur so kann später bei der Umrechnung und beim Hinzufügen zur Einkaufsliste korrekt zusammengefasst werden.\n" +
                            "Beispiel: 'Tomate' mit Einheit 'Stück' und nicht einmal 'Stück', einmal 'Stk.' oder 'g'.");
        }
    }
}
