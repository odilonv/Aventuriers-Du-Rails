package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends GridPane {

    private IJeu jeu;
    private VuePlateau plateau;
    private HBox destinations;
    private Button passer;
    private VueJoueurCourant joueurCourant;


    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;

        Image bg = new Image("images/backgrounds/TRAIN.jpg");
        BackgroundImage bImg = new BackgroundImage(bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        setBackground(bGround);

        plateau = new VuePlateau();
        destinations= new HBox();
        passer = new Button("Passer");
        joueurCourant = new VueJoueurCourant();
        //getChildren().addAll( destinations, passer, joueurCourant);
        ColumnConstraints premiercol=new ColumnConstraints();
        premiercol.setPercentWidth(20);
        ColumnConstraints deuxiemecol=new ColumnConstraints();
        deuxiemecol.setPercentWidth(70);
        ColumnConstraints troisiemecol=new ColumnConstraints();
        troisiemecol.setPercentWidth(10);
        RowConstraints premierl=new RowConstraints();
        premierl.setPercentHeight(5);
        RowConstraints deuxiemel=new RowConstraints();
        deuxiemel.setPercentHeight(80);
        RowConstraints troisiemel=new RowConstraints();
        troisiemel.setPercentHeight(15);
        getColumnConstraints().addAll(premiercol, deuxiemecol, troisiemecol);
        getRowConstraints().addAll(premierl,deuxiemel,troisiemel);
        add(destinations,1,2);
        add(passer,2,2);
        add(joueurCourant,0,1);
        add(plateau,1,1);
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
                                destinations.getChildren().add(new Button(d.getNom()));
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

    public Button trouveLabelDestination(IDestination dest){
        Button solution = null;
        for(Node label : destinations.getChildren()){
            Button l = (Button) label;
            if(l.getText().equals(dest.getNom())){
                solution=l;
            }
        }
        return solution;
    }

}