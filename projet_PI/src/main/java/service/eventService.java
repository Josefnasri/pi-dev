package service;

import entities.event;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class eventService implements IService<event>{
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public eventService() {
        conn= DataSource.getInstance().getCnx();
    }
    //methode Statement:
    public void addPst(event e){
        String requete="insert into event (date,adresse,nom) values (?,?,?)";
        try {

            pst=conn.prepareStatement(requete);
            pst.setDate(1, new java.sql.Date(e.getDate().getTime()));
            pst.setString(2,e.getAdresse());
            pst.setString(3,e.getNom());
           pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void delete(event event) {
        String requete="DELETE FROM `event` WHERE `ID` = ?";
        try {
            pst=conn.prepareStatement(requete);
            pst.setInt(1, event.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(event event) {
        String requete="UPDATE `event` SET `DATE`= ?,`ADRESSE`= ?,`NOM`= ? WHERE `ID`= ?";
        try {

            pst=conn.prepareStatement(requete);
            pst.setDate(1,new java.sql.Date(event.getDate().getTime()));
            pst.setString(2,event.getAdresse());
            pst.setString(3,event.getNom());
            pst.setInt(4,event.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<event> readAll() {
        String requete="select * from event";
        List<event> list=new ArrayList<>();
        try {
            ste=conn.createStatement();
            ResultSet rs=ste.executeQuery(requete);
           while(rs.next()) {
           list.add(
                   new event(rs.getInt(1),
                           rs.getDate(2),
                           rs.getString(3),
                           rs.getString(4))
           );
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public event readByid(int id) {
        return null;
    }

    public event searchById(int id) {
        String requete = "SELECT * FROM event WHERE ID = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new event(
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public event searchByName(String name) {
        String requete = "SELECT * FROM event WHERE NOM LIKE ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new event(
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
