module com.monitorium.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.monitorium.app to javafx.fxml;
    exports com.monitorium.app;
}