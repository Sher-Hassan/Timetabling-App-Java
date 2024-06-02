module com.example.timetablejava {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.timetablejava to javafx.fxml;
    exports com.example.timetablejava;
}