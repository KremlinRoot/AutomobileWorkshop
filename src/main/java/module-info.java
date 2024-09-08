module com.automobileapp.sotame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires com.h2database;

    opens com.automobileapp.sotame to javafx.fxml;
    opens com.automobileapp.sotame.models to javafx.base;
    exports com.automobileapp.sotame;
}