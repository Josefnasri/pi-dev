package interfaces;

import entities.EntreePanier;
import entities.User;

import java.util.List;
import java.util.Map;

public interface IPanier {

    public void addProduct(String productName, User user);

    public void removeProduct(String prodcutName);

    public int getQuantity(String productName);

    public float calculTotale();

    public List<EntreePanier> getEntries(User user);

    public void updateEntety(EntreePanier entry);

    public void deleteEntety(EntreePanier entry);

    public void passerCommande(User user, float totale, java.sql.Date sqlDate);

    public Map<String, List<EntreePanier>> getAllEntries();

    public float calculTotaleByEntries(List<EntreePanier> panierEntries);

    public void validerCommande(User user);

    Map<String, EntreePanier> getCommandeEntries(User user);

    public float calculTotaleByCategory(String category);


}
