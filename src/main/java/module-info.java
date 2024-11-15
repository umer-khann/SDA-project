module com.example.sdaproj {
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.jfoenix;
    requires javafx.controls;
    requires java.desktop;

    opens com.example.sdaproj to javafx.fxml;
    exports com.example.sdaproj;
}
