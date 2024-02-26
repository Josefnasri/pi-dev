package services;

import entities.Response;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceResponse implements Iservice<Response> {

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
    }

    @Override
    public void supprimer(Response response) throws SQLException {
        String req = "DELETE FROM reponse WHERE response_id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, response.getResponse_id());
        pre.executeUpdate();
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
}
