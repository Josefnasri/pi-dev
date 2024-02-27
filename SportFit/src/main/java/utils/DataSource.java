package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private  Connection cnx;
    private  String url="jdbc:mysql://localhost:3307/sportfit";
    private  String login="root";
    private  String pwd="";
    private static DataSource instance; /**/
    public final static String VLIDATED_STATUS = "Valid";

    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url,login,pwd);
            System.out.println("succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getInstance(){
        if(instance==null)
            instance= new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
/*Singleton:- permet de garantir une unique instantiation de la classe DataSource
             - c'est un Design Patterne
              -pour l'appliquer sur une classe faut que le constructor soit private*/
/*1-Rendre le constructeur private
 * 2-methode get*/
}
