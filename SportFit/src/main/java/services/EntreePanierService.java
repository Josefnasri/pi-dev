package services;

import entities.EntreePanier;
import entities.Produit;
import interfaces.IEntreePanier;
import interfaces.IPanier;

public class EntreePanierService implements IEntreePanier {

    EntreePanier panierEntry;

    public EntreePanierService(Produit product, int quantity) {
        this.panierEntry = new EntreePanier(product, quantity);
    }


    @Override
    public void increaseQuantity() {
        panierEntry.increaseQuantity();
    }

    @Override
    public void decreaseQuantity() {
        panierEntry.decreaseQuantity();
    }
}
