module com.example.sdaproj {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.sdaproj to javafx.fxml;
    exports com.example.sdaproj;
}
