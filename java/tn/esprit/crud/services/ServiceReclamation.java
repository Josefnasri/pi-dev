package tn.esprit.crud.services;

import entities.Reclamation;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements services.Iservice<Reclamation> {
    private Connection connection;

    public ServiceReclamation() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reclamation reclamation) throws SQLException {
        String req = "INSERT INTO reclamation (request_date, customer_id, description, status) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setDate(1, reclamation.getRequest_date());
        pre.setInt(2, reclamation.getCustomer_id());
        pre.setString(3, reclamation.getDescription());
        pre.setString(4, reclamation.getStatus());
        pre.executeUpdate();
    }

    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET request_date = ?, customer_id = ?, description = ?, status = ? " +
                "WHERE request_id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setDate(1, reclamation.getRequest_date());
        pre.setInt(2, reclamation.getCustomer_id());
        pre.setString(3, reclamation.getDescription());
        pre.setString(4, reclamation.getStatus());
        pre.setInt(5, reclamation.getRequest_id());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(Reclamation reclamation) throws SQLException {
        String req = "DELETE FROM reclamation WHERE request_id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, reclamation.getRequest_id());
        pre.executeUpdate();
    }

    @Override
    public List<Reclamation> afficher() throws SQLException {
        String req = "SELECT * FROM reclamation";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<Reclamation> list = new ArrayList<>();
        while (res.next()) {
            Reclamation r = new Reclamation();
            r.setRequest_id(res.getInt(1));
            r.setRequest_date(res.getDate(2));
            r.setCustomer_id(res.getInt(3));
            r.setDescription(res.getString(4));
            r.setStatus(res.getString(5));
            list.add(r);
        }
        return list;
    }
    public Reclamation getReclamationById(int id) throws SQLException {
        String req = "SELECT * FROM reclamation WHERE request_id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setRequest_id(res.getInt(1));
            reclamation.setRequest_date(res.getDate(2));
            reclamation.setCustomer_id(res.getInt(3));
            reclamation.setDescription(res.getString(4));
            reclamation.setStatus(res.getString(5));
            return reclamation;
        }
        return null;
    }
}
