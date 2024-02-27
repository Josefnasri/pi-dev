package service;

import entities.event;
import entities.participant;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class participantService implements IService<participant>{
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public participantService() {
        conn= DataSource.getInstance().getCnx();
    }
    //methode Statement:
    public void addPst(participant p){
        String requete="INSERT INTO `participant`(`nom`, `prenom`, `age`, `address`, `niveauSportif`, `id_event`) VALUES (?,?,?,?,?,?)";
        try {

            pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getPrenom());
            pst.setInt(3, p.getAge());
            pst.setString(4, p.getAddress());
            pst.setString(5, p.getNiveauSportif());
            pst.setInt(6, p.getId_event());
           pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void delete(participant p) {
        String requete="DELETE FROM `participant` WHERE `id` = ?";
        try {
            pst=conn.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(participant p) {
        String requete="UPDATE `participant` SET `nom`=?,`prenom`=?,`age`=?,`address`=?,`niveauSportif`=?,`id_event`=? WHERE `id`=?";
        try {

            pst=conn.prepareStatement(requete);
            pst.setString(1,p.getNom());
            pst.setString(2,p.getPrenom());
            pst.setInt(3,p.getAge());
            pst.setString(4,p.getAddress());
            pst.setString(5,p.getNiveauSportif());
            pst.setInt(6,p.getId_event());
            pst.setInt(7,p.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<participant> readAll() {
        String requete = "SELECT * FROM participant";
        List<participant> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                participant p = new participant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getString("niveauSportif"),
                        rs.getInt("id_event")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public participant readByid(int id) {
        return null;
    }
}
