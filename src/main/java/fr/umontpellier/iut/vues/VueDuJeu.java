package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends VBox {

    private IJeu jeu;
    private VuePlateau plateau;
    private VBox destinations;
    private Button passer;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        destinations = new VBox();
        passer = new Button("Passer");

        getChildren().addAll(destinations, passer);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        //ObservableList<Destination> affichageDestinations = destinationsInitialesProperty();

        ListChangeListener<IDestination> affichageDest= new ListChangeListener<IDestination>() {
            @Override
            public void onChanged(Change<? extends IDestination> change) {
                change.next();
                if(change.wasAdded()){
                    for(IDestination d : change.getAddedSubList()) {
                        System.out.println(d.getNom() + " a ete ajoute");
                        destinations.getChildren().add(new Label(d.getNom()));
                    }
                }
                else if(change.wasRemoved()){
                    for(IDestination d : change.getAddedSubList()) {
                        System.out.println(d.getNom() + " a ete supprime");
                    }
                }
            }
        };
        jeu.destinationsInitialesProperty().addListener(affichageDest);

        passer.setOnAction(passer -> jeu.passerAEteChoisi());







        /*Button b1 = new Button("Passer");

        getChildren().add(b1);*/
    }

}