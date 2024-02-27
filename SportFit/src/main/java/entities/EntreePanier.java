package entities;

import java.sql.Date;

public class EntreePanier {

    private Produit product;
    private int quantity;
    private Date dateCreation;

    public EntreePanier(Produit product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public EntreePanier(Produit product, int quantity, Date date) {
        this.product = product;
        this.quantity = quantity;
        this.dateCreation = date;
    }


    public Produit getProduct() {
        return product;
    }

    public void setProduct(Produit product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }


    /* manipulation de la quantité*/
    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }

    }
}
