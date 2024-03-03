package pi.sport.youssef_pi.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
  import pi.sport.youssef_pi.entite.Categorie;
import pi.sport.youssef_pi.entite.Exercice;
import pi.sport.youssef_pi.interfaces.IExercice;
import pi.sport.youssef_pi.utils.Connexion_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
public class ServiceExercice implements IExercice {
    private Connection connection;
    public ServiceExercice() {
        try {
            this.connection = Connexion_database.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServiceExercice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void ajouterExercice(Exercice exercice) {
        System.out.println("test1  "+exercice.getNom());
        String insertQuery = "INSERT INTO exercice (description, categorie_id, nombre_de_fois,nom,duree,image) VALUES (?, ?, ?,?,?,?)";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, exercice.getDescription());
            preparedStatement.setInt(2, exercice.getCategorieId()); // Assuming idCategorie is the category ID
            preparedStatement.setInt(3, exercice.getNombreDeFois());
            preparedStatement.setString(4, exercice.getNom());
            preparedStatement.setString(5, exercice.getDuree());
            preparedStatement.setString(6, exercice.getImage());

            preparedStatement.executeUpdate();

            System.out.println(" ajoutÃ©e avec succÃ¨s !");
            System.out.println("test2  "+exercice.getNom());

            //String to = getUserEmail(reponse.getId_reclamation()); // Get user email from database
            String to ="youssef.benhiba12@gmail.com";
                String subject = "Nouveaux Exercice Ajouter ";
                String body = "Bonjour,\n\n Un Nouveaux Exercice Ajouter  Dans notre Salle "+" De Type :  "+exercice.getNom()+" .\n\nCordialement,\nL'Equipe de support";
                sendEmail(to, subject, body); // Call function to send email*/

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
 private void sendEmail(String to, String subject, String body) {
            String username = "youssef.benhiba12@gmail.com";
            String password = "jukujznecgbfprng";
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // Change this to your SMTP server host(yahoo...)
            props.put("mail.smtp.port", "587"); // Change this to your SMTP server port
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session;
            session = Session.getInstance(props,new Authenticator() {
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new jakarta.mail.PasswordAuthentication(username, password);
                }
            });


            try {
                // Create a MimeMessage object

                // Create a new message
                MimeMessage message = new MimeMessage(session);
                // Set the From, To, Subject, and Text fields of the message
                message.setFrom(new InternetAddress(username));
                message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body);

                // Send the message using Transport.send
                Transport.send(message);

                System.out.println("Email sent successfully");
            } catch (MessagingException ex) {
                System.err.println("Failed to send email: " + ex.getMessage());
            }

        }


    @Override
    public Exercice recupererExerciceParIdCategorie(int idCategorie) {
        String selectQuery = "SELECT * FROM exercice WHERE categorie_id = ?";

        try {
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery) ;

            preparedStatement.setInt(1, idCategorie);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a constructor in Exercice class to create an instance
                    return new Exercice(
                            resultSet.getString("description"),
                            resultSet.getInt("categorie_id"),
                            resultSet.getInt("nombre_de_fois"),
                            resultSet.getString("nom"),
                            resultSet.getString("duree"),
                            resultSet.getString("image")


                            );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

     return null;
    }

    @Override
    public boolean supprimerExercice(Exercice exercice) {
System.out.println("id exer"+exercice.getExerciceId());
        String deleteQuery = "DELETE FROM exercice WHERE exercice_id = ?";

        try {
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, exercice.getExerciceId());
            preparedStatement.executeUpdate();
            int deletedRows = preparedStatement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Hand
            return false;
// le the exception according to your application's needs
        }

    }

    @Override
    public boolean modifierExercice(Exercice exercice) {
        System.out.println("ok1");

        System.out.println("Description: " + exercice.getDescription());
        System.out.println("Nombre de Fois: " + exercice.getNombreDeFois());
        System.out.println("Nom: " + exercice.getNom());
        System.out.println("Duree: " + exercice.getDuree());
        System.out.println("Exercice ID: " + exercice.getExerciceId());

        String updateQuery = "UPDATE exercice SET description = ?, nombre_de_fois= ? ,nom=?,duree=? WHERE exercice_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, exercice.getDescription());
            preparedStatement.setInt(2, exercice.getNombreDeFois());
            preparedStatement.setString(3, exercice.getNom());
            preparedStatement.setString(4, exercice.getDuree());
            preparedStatement.setInt(5, exercice.getExerciceId());

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("ok2");

            // If rowsUpdated is greater than 0, the update was successful
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return false;
        }


    }



    @Override
    public ObservableList<Exercice> afficherTousExercices() {
        ObservableList<Exercice> exercices = FXCollections.observableArrayList();

        String selectQuery = "SELECT e.*, c.nom AS categorie_nom FROM exercice e " +
                "JOIN categorie c ON e.categorie_id = c.categorie_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Exercice exercice = new Exercice(
                        resultSet.getString("description"),
                        resultSet.getInt("categorie_id"),
                        resultSet.getInt("nombre_de_fois"),
                        resultSet.getString("nom"),
                        resultSet.getString("duree"),
                        resultSet.getString("image")
                );
                exercice.setExerciceId(resultSet.getInt("exercice_id"));
                exercice.setCategorie_nom(resultSet.getString("categorie_nom"));

                exercices.add(exercice);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return exercices;
    }

    public List<Categorie> Read() {
        List<Categorie> categories = new ArrayList<>();
        String selectQuery = "SELECT * FROM categorie";

        try  {
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setCategorieId(resultSet.getInt("categorie_id"));
                categorie.setNom(resultSet.getString("nom"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return categories;
    }
}


