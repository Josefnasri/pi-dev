module tn.esprit.football.football {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires java.datatransfer;
    requires itextpdf;
    requires java.sql;
    requires java.desktop;
    requires java.mail;
    requires javafx.web;
    requires jfxtras.agenda;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires stripe.java;
    requires org.apache.pdfbox;

    exports tn.esprit.crud.test;
    opens tn.esprit.crud.test;
    exports tn.esprit.crud.controllers;
    exports tn.esprit.crud.models;
    exports tn.esprit.crud.services;
    exports tn.esprit.crud.utils;
    opens tn.esprit.crud.services;
    opens tn.esprit.crud.utils;
    opens tn.esprit.crud.controllers;
}