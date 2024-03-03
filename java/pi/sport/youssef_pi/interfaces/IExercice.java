package pi.sport.youssef_pi.interfaces;

import pi.sport.youssef_pi.entite.Categorie;
import pi.sport.youssef_pi.entite.Exercice;

import java.util.List;

public interface IExercice {

    void ajouterExercice(Exercice exercice);

    public Exercice recupererExerciceParIdCategorie(int idCategorie);

    // Supprimer une réponse de la base de données
    public boolean supprimerExercice( Exercice exercice);

    // Modifier une réponse dans la base de données
    public boolean modifierExercice(Exercice exercice);

    // Récupérer toutes les réponses de la base de données
    public List<Exercice> afficherTousExercices();
    public List<Categorie> Read();

}
