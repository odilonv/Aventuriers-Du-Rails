package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    private VueJoueurCourant joueurCourant;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        destinations = new VBox();
        passer = new Button("Passer");
        joueurCourant = new VueJoueurCourant();
        getChildren().addAll(destinations, passer, joueurCourant);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        //ObservableList<Destination> affichageDestinations = destinationsInitialesProperty();
        ListChangeListener<IDestination> affichageDest= new ListChangeListener<IDestination>() {
            @Override
            public void onChanged(Change<? extends IDestination> change) {
                Platform.runLater(() -> {
                    while(change.next()) {
                        if (change.wasAdded()) {
                            for (IDestination d : change.getAddedSubList()) {
                                System.out.println(d.getNom() + " a ete ajoute");
                                destinations.getChildren().add(new Label(d.getNom()));
                            }
                        } else if (change.wasRemoved()) {
                            for (IDestination d : change.getRemoved()) {
                                System.out.println(d.getNom() + " a ete supprime");
                                destinations.getChildren().remove(trouveLabelDestination(d));
                            }
                        }
                    }
                });
            }
        };
        jeu.destinationsInitialesProperty().addListener(affichageDest);
        passer.setOnAction(passer -> jeu.passerAEteChoisi());
        joueurCourant.creerBindings();
    }

    public Label trouveLabelDestination(IDestination dest){
        Label solution = null;
        for(Node label : destinations.getChildren()){
            Label l = (Label) label;
            if(l.getText().equals(dest.getNom())){
                solution=l;
            }
        }
        return solution;
    }

}