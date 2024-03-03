module pi.sport.youssef_pi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // Add this line

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires okhttp3;
    requires itextpdf;
    opens pi.sport.youssef_pi to javafx.fxml;
    exports pi.sport.youssef_pi;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.desktop;
    requires jakarta.mail;
    opens pi.sport.youssef_pi.entite to javafx.base;
    opens pi.sport.youssef_pi.controller to javafx.fxml;

    exports pi.sport.youssef_pi.controller;
}
