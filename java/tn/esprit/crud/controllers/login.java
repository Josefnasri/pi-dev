package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.crud.services.UserService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.sql.SQLException;


public class login {
    @FXML
    private Button insription;

    @FXML
    private Button login;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField name;



    @FXML

    public static String email;

    public static int codem;




    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    @FXML
    void ForgotpwdBtn(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //SignUpButton.getScene().getWindow().hide();
        Stage prStage = new Stage();
        loader.setLocation(getClass().getResource("/tn/esprit/crud/SendMail.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        prStage.setScene(scene);
        prStage.setResizable(false);
        prStage.show();

    }


    private final UserService userService = new UserService();

    public login() throws SQLException {
    }

    @FXML
    void connecter(ActionEvent event) throws IOException {
        String nom = name.getText();
        String mdp = pass.getText();
        if (userService.authenticateUser(nom, mdp)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/frant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) pass.getScene().getWindow();
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Login failed");
        }

    }
    @FXML
    void inscription(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/inscription.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = new Stage();
            stage.setTitle("Inscription Page");
            stage.setScene(scene);

            // Show the stage
            stage.show();

            // Close the current stage (login stage) if needed
            // ((Stage) insription.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

