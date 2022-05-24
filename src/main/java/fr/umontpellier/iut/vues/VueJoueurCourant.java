package fr.umontpellier.iut.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;

    public VueJoueurCourant(String nomJ){
        nomJoueur= new Label(nomJ);
        getChildren().add(nomJoueur);

    }

}
