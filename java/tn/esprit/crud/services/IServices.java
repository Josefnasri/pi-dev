package tn.esprit.crud.services;

import java.sql.SQLException;
import java.util.List;

public interface IServices<T> {
    void ajouter (T t) throws SQLException;

    void modifier (T t) throws SQLException;

    void supprimer (T t);

    void supprimer(int id) throws SQLException;


    List<T> recupperer() throws SQLException;

    int getIdbyMail(String EmailUser) throws SQLException;

    void UpdatePassword(String EmailUser, String PasswordUser) throws SQLException;
}
