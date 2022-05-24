package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
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
    private VBox cartes;

    public VueJoueurCourant(){
        nomJoueur= new Label();
        cartes = new VBox();
        getChildren().addAll(nomJoueur,cartes);

    }

    public void creerBindings() {
        ChangeListener<IJoueur> changeListener = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    nomJoueur.setText(t1.getNom());
                    cartes.getChildren().clear();
                    for(int i=0; i<cartes.getChildren().size();i++){
                        cartes.getChildren().add(new Label(t1.getCartesWagon().get(i).toString()));
                    }

                });
            }
        };
    }


}
