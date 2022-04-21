package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.Joueur;
import javafx.beans.property.ObjectProperty;

public interface IRoute {
    ObjectProperty<Joueur> proprietaireProperty();
    String getNom();
}