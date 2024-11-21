module com.example.sdaproj {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires com.jfoenix;

    // Make the controllers package accessible to FXML
    opens com.example.controllers to javafx.fxml;

    // Export the controllers package (optional, if needed outside the module)
    exports com.example.controllers;

    // Retain existing settings for the sdaproj package
    opens com.example.sdaproj to javafx.fxml;
    exports com.example.sdaproj;
    exports com.example.oopfiles;
    opens com.example.oopfiles to javafx.fxml;
}
