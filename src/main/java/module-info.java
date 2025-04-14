module com.brh.einkaufsplaner_desktop {
    requires javafx.fxml;
    requires org.json;
    requires javafx.controls;
    requires org.controlsfx.controls;


    opens com.brh.einkaufsplaner_desktop to javafx.fxml;
    exports com.brh.einkaufsplaner_desktop;
    exports com.brh.einkaufsplaner_desktop.model;
    opens com.brh.einkaufsplaner_desktop.model to javafx.fxml;
    opens com.brh.einkaufsplaner_desktop.controller to javafx.fxml;
    exports com.brh.einkaufsplaner_desktop.controller;
}