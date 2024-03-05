package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherUsers {
    @FXML
    private ListView<String> adresseCol;

    @FXML
    private ListView<String> emailCol;

    @FXML
    private ListView<String> mdpCol;

    @FXML
    private ListView<String> nomCol;

    @FXML
    private ListView<String> prenomCol;

    @FXML
    private ListView<String> idCol;

    @FXML
    private TextField searchField; // Add the search text field

    private ObservableList<User> usersList; // Maintain the original list of users

    @FXML
    void PageSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SupprimerUser.fxml"));
        try {
            prenomCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void PageModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ModifierUser.fxml"));
        try {
            prenomCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ReturnToAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AjouterUser.fxml"));
        try {
            prenomCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        UserService userService;
        try {
            userService = new UserService();
            List<User> users = userService.recupperer();
            usersList = FXCollections.observableArrayList(users);
            displayUsers(usersList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void OnRecherche(ActionEvent actionEvent) {
        String searchText = searchField.getText().toLowerCase();
        if (!searchText.isEmpty()) {
            ObservableList<User> filteredList = FXCollections.observableArrayList();
            for (User user : usersList) {
                if (userContainsSearchText(user, searchText)) {
                    filteredList.add(user);
                }
            }
            displayUsers(filteredList);
        } else {
            displayUsers(usersList);
        }
    }

    private boolean userContainsSearchText(User user, String searchText) {
        return user.getNom().toLowerCase().contains(searchText)
                || user.getPrenom().toLowerCase().contains(searchText)
                || user.getEmail().toLowerCase().contains(searchText);
    }

    private void displayUsers(ObservableList<User> users) {
        ObservableList<String> idList = FXCollections.observableArrayList();
        ObservableList<String> emailList = FXCollections.observableArrayList();
        ObservableList<String> mdpList = FXCollections.observableArrayList();
        ObservableList<String> nomList = FXCollections.observableArrayList();
        ObservableList<String> prenomList = FXCollections.observableArrayList();
        ObservableList<String> adresseList = FXCollections.observableArrayList();

        for (User user : users) {
            idList.add(String.valueOf(user.getId()));
            emailList.add(user.getEmail());
            mdpList.add(user.getMdp());
            nomList.add(user.getNom());
            prenomList.add(user.getPrenom());
            adresseList.add(user.getAdresse());
        }

        idCol.setItems(idList);
        emailCol.setItems(emailList);
        mdpCol.setItems(mdpList);
        nomCol.setItems(nomList);
        prenomCol.setItems(prenomList);
        adresseCol.setItems(adresseList);
    }
}
