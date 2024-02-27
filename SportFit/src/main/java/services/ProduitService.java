package services;

import entities.Produit;
import interfaces.IProduit;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IProduit<Produit> {
    private Connection conn;
    private Statement ste;  /*pour envoyer une requette*/
    private PreparedStatement pst;/*plus rapide*/

    public ProduitService() {
        conn= DataSource.getInstance().getCnx();
    }


    @Override
    public  void add(Produit p) {
        String requete="insert into produit (imageFile,prix,categorie,nom values ('"+p.getImageFile()+"','"+p.getPrix()+"','"+p.getCategorie()+"','"+p.getNom()+"') ";
        try {
            ste=conn.createStatement();
            ste.executeUpdate(requete);   /*pour envoyer la requette */
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPst(Produit p){/*Pripared statement Permet d'envoyer des requettes prescompilées c plus rapide*/
        String requete="insert into produit (nom,imageFile,prix,categorie) values (?,?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1,p.getNom());
            pst.setString(2,p.getImageFile());
            pst.setFloat(3,p.getPrix()); /*setString(1, String.valueOf(p.getPrix()));*/
            pst.setString(4,p.getCategorie());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Produit produit) {
        String requete = "DELETE FROM produit WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, produit.getId()); // Assuming you have a method getId() in the Produit class
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Produit produit) {
        String requete = "UPDATE produit SET nom=?, prix=? WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, produit.getNom());
            pst.setFloat(2, produit.getPrix());
            pst.setInt(3, produit.getId()); // Assuming you have a method getId() in the Personne class
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Produit> readAll() {
        String requete="select * from produit";
        List<Produit> list=new ArrayList<>();
        try {
            ste=conn.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){ /*tant que y en a des données*/
                list.add(new Produit(rs.getInt(1),rs.getString("nom"),rs.getString("imageFile"),rs.getFloat("prix"),rs.getString("categorie")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public Produit readById(int id) {
        String requete = "SELECT * FROM produit WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Produit(rs.getInt(1),rs.getString("nom"),rs.getString("imageFile"),rs.getFloat("prix"),rs.getString("categorie"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
