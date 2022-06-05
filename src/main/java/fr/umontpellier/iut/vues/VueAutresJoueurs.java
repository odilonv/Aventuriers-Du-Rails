package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends HBox {

    List<Joueur> listJoueurs;
    List<Joueur> listbis;

    public VueAutresJoueurs(){
        listJoueurs = new ArrayList<>();
        listbis = new ArrayList<>();
    }


    public void creerBindings(){
        ChangeListener<IJoueur> changeInstruction = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur s, IJoueur t1) {
                Platform.runLater(() -> {

                        if(listJoueurs.isEmpty()) {
                            listJoueurs.addAll(((VueDuJeu) getScene().getRoot()).getJeu().getJoueurs());
                            listbis.addAll(listJoueurs);
                            for (int i = 0; i < listbis.size(); i++) {
                                Button button = new Button(listbis.get(i).getNom());
                                //if (!Objects.equals(t1.getNom(), listbis.get(i).getNom())) {
                                    getChildren().add(button);
                                //}
                            }
                        }
                        //getChildren().get(0).setOpacity(0);
                        getChildren().add(getChildren().remove(0));
                        getChildren().get(getChildren().size()-1).setOpacity(0);
                        getChildren().get(getChildren().size()-2).setOpacity(100);


                    // JOUEUR COURANT t1

                });
            }
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(changeInstruction);
    }





}
