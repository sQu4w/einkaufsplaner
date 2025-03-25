module com.brh.einkaufsplaner_desktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.brh.einkaufsplaner_desktop to javafx.fxml;
    exports com.brh.einkaufsplaner_desktop;
}