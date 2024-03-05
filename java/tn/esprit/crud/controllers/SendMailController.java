package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.IServices;
import tn.esprit.crud.services.SendingMail;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.utils.OTPUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import static tn.esprit.crud.controllers.login.codem;
import static tn.esprit.crud.controllers.login.isValidEmailAddress;

public class SendMailController {

    @FXML
    private TextField EmailUser;

    @FXML
    private Label mailLabel;
    static String mail;


    @FXML
    void SendMail(ActionEvent event) throws Exception{
            IServices Iu = new UserService();
            Random r = new Random ();
         codem = r.nextInt(9999 - 1000 + 1);
        OTPUtil.setOtp(codem);
            System.out.println(codem);
            if(isValidEmailAddress(EmailUser.getText())&&UserExist(EmailUser.getText())==true)
            {
                SendingMail.send(EmailUser.getText(),codem);
                FXMLLoader loader = new FXMLLoader();
                mailLabel.getScene().getWindow().hide();
                Stage prStage = new Stage();
                loader.setLocation(getClass().getResource("/tn/esprit/crud/ResetPassword.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                prStage.setScene(scene);
                prStage.setResizable(false);
                prStage.show();
            }
            else if(isValidEmailAddress(EmailUser.getText())==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte");
                alert.setHeaderText(null);
                alert.setContentText("Email address not valid");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte");
                alert.setHeaderText(null);
                alert.setContentText("Not a user go ahead and create an account");
                alert.showAndWait();

            }

        }


    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        mailLabel.getScene().getWindow().hide();
        Stage prStage =new Stage();
        loader.setLocation(getClass().getResource("/tn/esprit/crud/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        prStage.setScene(scene);
        prStage.setResizable(false);
        prStage.show();
    }
    public boolean UserExist (String email) throws SQLException {

        IServices<User> Iu = new UserService();
        if (Iu.getIdbyMail(EmailUser.getText())!=0){
            mail=EmailUser.getText();
            return true;
        }
        return false;
    }
}
