package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private Button pioche;
    private Button piocheDest;


    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;

        Image bg = new Image("images/backgrounds/LUI.jpg");
        BackgroundImage bImg = new BackgroundImage(bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        setBackground(bGround);

        plateau = new VuePlateau();
        destinations= new HBox();
        passer = new Button("PASSER\nSON\nTOUR");
        joueurCourant = new VueJoueurCourant();

        VBox pioches = new VBox();
        pioche = new Button();
        ImageView imageView = new ImageView("images/wagons.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(80);
        pioche.setGraphic(imageView);
        pioche.setStyle("-fx-background-color:transparent");

        piocheDest = new Button();
        ImageView img = new ImageView("images/destinations.png");
        img.setPreserveRatio(true);
        img.setFitHeight(80);
        piocheDest.setGraphic(img);
        piocheDest.setStyle("-fx-background-color:transparent");


        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.web("#F7E6BC"));
        passer.setAlignment(Pos.CENTER);
        passer.setPrefSize(120,100);
        passer.setMaxSize(120,100);
        passer.setFont(Font.font("Georgia", 20));
        passer.setStyle("-fx-border-color: yellow");
        passer.setStyle("-fx-background-color: #F7E6BC");
        passer.setEffect(dropShadow);
        pioche.setEffect(dropShadow);
        piocheDest.setEffect(dropShadow);


        pioches.setSpacing(50);
        pioches.setAlignment(Pos.CENTER);
        pioches.getChildren().addAll(pioche, piocheDest,passer);

        //getChildren().addAll( destinations, passer, joueurCourant);
        ColumnConstraints premiercol=new ColumnConstraints();
        ColumnConstraints deuxiemecol=new ColumnConstraints();
        ColumnConstraints troisiemecol=new ColumnConstraints();
        premiercol.setPercentWidth(23);
        deuxiemecol.setPercentWidth(66);
        troisiemecol.setPercentWidth(14);

        RowConstraints premierl=new RowConstraints();
        RowConstraints deuxiemel=new RowConstraints();
        RowConstraints troisiemel=new RowConstraints();
        premierl.setPercentHeight(10);
        deuxiemel.setPercentHeight(80);
        troisiemel.setPercentHeight(15);

        getColumnConstraints().addAll(premiercol, deuxiemecol, troisiemecol);
        getRowConstraints().addAll(premierl,deuxiemel,troisiemel);
        setGridLinesVisible(true);
        add(destinations,1,2);
        //add(passer,2,1);
        add(joueurCourant,0,1);
        add(plateau,1,1);
        add(pioches, 2,1);

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
        pioche.setOnAction(pioche -> jeu.uneCarteWagonAEtePiochee());
        piocheDest.setOnAction(piocheDest -> jeu.uneDestinationAEtePiochee());
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