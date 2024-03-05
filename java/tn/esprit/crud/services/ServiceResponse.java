package tn.esprit.crud.services;

import entities.Response;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class ServiceResponse implements services.Iservice<Response> {

    private Connection connection;

    public ServiceResponse() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Response response) throws SQLException {
        String req = "INSERT INTO reponse (request_id, response_date, response_text, response_status) VALUES (?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, response.getRequest_id());
        pre.setDate(2, response.getResponse_date());
        pre.setString(3, response.getResponse_text());
        pre.setString(4, response.getResponse_status());
        pre.executeUpdate();
        sendEmail("New Response Added", "A new response has been added.");
    }

    @Override
    public void modifier(Response response) throws SQLException {
        String req = "UPDATE reponse SET response_date = ?, response_text = ?, response_status = ? WHERE response_id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setDate(1, response.getResponse_date());
        pre.setString(2, response.getResponse_text());
        pre.setString(3, response.getResponse_status());
        pre.setInt(4, response.getResponse_id());
        pre.executeUpdate();
        sendEmail("Response Modified", "A response has been modified.");
    }

    @Override
    public void supprimer(Response response) throws SQLException {
        String req = "DELETE FROM reponse WHERE response_id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, response.getResponse_id());
        pre.executeUpdate();
        sendEmail("Response Deleted", "A response has been deleted.");

    }

    @Override
    public List<Response> afficher() throws SQLException {
        String req = "SELECT * FROM reponse";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<Response> list = new ArrayList<>();
        while (res.next()) {
            Response r = new Response();
            r.setResponse_id(res.getInt("response_id"));
            r.setRequest_id(res.getInt("request_id"));
            r.setResponse_date(res.getDate("response_date"));
            r.setResponse_text(res.getString("response_text"));
            r.setResponse_status(res.getString("response_status"));
            list.add(r);
        }
        return list;
    }

    public void sendEmail(String subject, String body) {
        String username = "nasri.youssef@esprit.tn"; //mail
        String password = "gfrq ypcd qlbv xjdn"; //mdp app
        String to = "belhaje.kamel@esprit.tn"; //mail recu
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //authentification
        props.put("mail.smtp.starttls.enable", "true"); //encrypt
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP :Simple mail tranfer protocol
        props.put("mail.smtp.port", "587"); //port SMTP

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
