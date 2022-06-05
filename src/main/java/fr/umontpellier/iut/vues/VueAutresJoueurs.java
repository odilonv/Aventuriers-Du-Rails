package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends HBox {
    /*
    List<Joueur> listJoueurs;

    public VueAutresJoueurs(){
        //listJoueurs.addAll(((VueDuJeu) getScene().getRoot()).getJeu().getJoueurs());

    }

    public void creerBindings(){
        ChangeListener<IJoueur> changeInstruction = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur s, IJoueur t1) {
                Platform.runLater(() -> {
                    // JOUEUR COURANT t1
                    for(int i=0;i<listJoueurs.size();i++){
                        Button button = new Button(listJoueurs.get(i).toString());
                        getChildren().add(button);
                    }

                });
            }
        };
        //((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(changeInstruction);
    }

     */



}
