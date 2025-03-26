module com.brh.einkaufsplaner_desktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.brh.einkaufsplaner_desktop to javafx.fxml;
    exports com.brh.einkaufsplaner_desktop;
    exports com.brh.einkaufsplaner_desktop.model;
    opens com.brh.einkaufsplaner_desktop.model to javafx.fxml;
    opens com.brh.einkaufsplaner_desktop.controller to javafx.fxml;
    exports com.brh.einkaufsplaner_desktop.controller;
}