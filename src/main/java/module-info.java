module com.automobileapp.sotame {


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires jdk.compiler;
    requires com.h2database;
    requires java.sql;
    requires jfxtras.agenda;
    requires java.desktop;

    opens com.automobileapp.sotame.models to javafx.base;
    exports com.automobileapp.sotame;
}