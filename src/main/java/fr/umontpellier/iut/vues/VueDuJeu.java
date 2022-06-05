package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
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

import java.util.Objects;

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
    private HBox cartesVisibles;
    private Button passer;
    private VueJoueurCourant joueurCourant;
    private VueAutresJoueurs autresJoueurs;
    private Button pioche;
    private Button piocheDest;
    private int cpt=0;

    private Label instructions;


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
        destinations.setSpacing(10);
        destinations.setAlignment(Pos.CENTER);
        passer = new Button("PASSER\nSON\nTOUR");
        joueurCourant = new VueJoueurCourant();
        autresJoueurs = new VueAutresJoueurs();

        cartesVisibles = new HBox();
        cartesVisibles.setPrefSize(200,100);
        cartesVisibles.setAlignment(Pos.TOP_RIGHT);

        VBox pioches = new VBox();
        pioche = new Button();
        ImageView imageView = new ImageView("images/wagons.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(80);
        pioche.setGraphic(imageView);
        pioche.setStyle("-fx-background-color:transparent");
        pioche.setOnMouseEntered(mouseEvent -> imageView.setFitHeight(82));
        pioche.setOnMouseExited(mouseEvent -> imageView.setFitHeight(80));

        piocheDest = new Button();
        ImageView img = new ImageView("images/destinations.png");
        img.setPreserveRatio(true);
        img.setFitHeight(80);
        piocheDest.setGraphic(img);
        piocheDest.setStyle("-fx-background-color:transparent");
        piocheDest.setOnMouseEntered(mouseEvent -> img.setFitHeight(82));
        piocheDest.setOnMouseExited(mouseEvent -> img.setFitHeight(80));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.web("#F7E6BC"));
        passer.setAlignment(Pos.CENTER);
        passer.setPrefSize(120,100);
        passer.setMaxSize(125,105);
        passer.setFont(Font.font("Georgia", 20));
        passer.setStyle("-fx-border-color: yellow");
        passer.setStyle("-fx-background-color: #F7E6BC");
        passer.setEffect(dropShadow);
        passer.setOnMouseEntered(mouseEvent -> {passer.setPrefSize(125,105);passer.setStyle("-fx-background-color: #F5E9D7");});
        passer.setOnMouseExited(mouseEvent -> {passer.setPrefSize(120,100);passer.setStyle("-fx-background-color: #F7E6BC");});
        pioche.setEffect(dropShadow);
        piocheDest.setEffect(dropShadow);

        HBox hBox= new HBox();
        instructions = new Label(jeu.instructionProperty().getValue().toUpperCase() + " :");
        instructions.setAlignment(Pos.BOTTOM_LEFT);
        /*instructions.setOnMouseEntered(mouseEvent -> instructions.setPrefSize(200,500));
        instructions.setOnMouseExited(mouseEvent -> instructions.setPrefSize('àà',100));*/
        instructions.setStyle("-fx-background-color: black ; -fx-text-fill: white");
        instructions.setFont(Font.font("Georgia", 20));
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setPadding(new Insets(20));
        hBox.getChildren().add(instructions);


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
        //setGridLinesVisible(true);
        //add(passer,2,1);
        add(joueurCourant,0,1);
        add(autresJoueurs, 0,2);
        add(plateau,1,1);
        add(pioches, 2,1);
        add(cartesVisibles,1,2);
        add(hBox, 1,1);
        add(destinations,1,2);


    }

    public HBox getCartesVisibles() {
        return cartesVisibles;
    }

    public IJeu getJeu() {
        return jeu;
    }

    public HBox getDestinations() {
        return destinations;
    }

    public Label getInstructions() {
        return instructions;
    }

    public void creerBindings() {
        //ObservableList<Destination> affichageDestinations = destinationsInitialesProperty();
        ListChangeListener<IDestination> affichageDest= new ListChangeListener<IDestination>() {
            @Override
            public void onChanged(Change<? extends IDestination> change) {
                Platform.runLater(() -> {
                    if(change.next()) {
                        cartesVisibles.toFront();
                        cartesVisibles.setOpacity(100);
                        if (change.wasAdded()) {
                            for (IDestination d : change.getAddedSubList()) {
                                //System.out.println(d.getNom() + " a ete ajoute");
                                VueDestination vueDestination = new VueDestination(d,true);
                                vueDestination.setAlignment(Pos.TOP_RIGHT);
                                vueDestination.getDe().setOnMouseEntered(mouseEvent -> vueDestination.getDe().setStyle("-fx-background-color: white"));
                                vueDestination.getDe().setOnMouseExited(mouseEvent -> vueDestination.getDe().setStyle("-fx-background-color: #F7E6BC"));
                                vueDestination.getDe().setOnMouseClicked(destinations -> jeu.destinationsInitialesProperty().remove(trouveLabelDestination(d)));
                                destinations.getChildren().add(vueDestination);
                            }
                        } else if (change.wasRemoved()) {
                            for (IDestination d : change.getRemoved()) {
                                //System.out.println(d.getNom() + " a ete supprime");
                                jeu.uneDestinationAEteChoisie(d.getNom());
                                destinations.getChildren().remove(trouveLabelDestination(d));
                            }
                        }
                        cartesVisibles.toBack();
                        cartesVisibles.setOpacity(0);
                    }
                });
            }
        };

        ListChangeListener<ICouleurWagon> affichageCVisibles = new ListChangeListener<ICouleurWagon>() {
            @Override
            public void onChanged(Change<? extends ICouleurWagon> change) {
                Platform.runLater(() -> {
                    if(change.next()) {
                        cartesVisibles.toFront();
                        cartesVisibles.setOpacity(100);
                        if (change.wasAdded()) {
                            for (ICouleurWagon d : change.getAddedSubList()) {
                                VueCarteWagon vueCarteWagon = new VueCarteWagon(d);
                                vueCarteWagon.getImageView().setFitHeight(70);
                                vueCarteWagon.getButton().setOnMouseEntered(mouseEvent -> vueCarteWagon.getImageView().setFitHeight(90));
                                vueCarteWagon.getButton().setOnMouseExited(mouseEvent -> vueCarteWagon.getImageView().setFitHeight(70));
                                vueCarteWagon.getButton().setOnMouseClicked(cartesVisibles -> jeu.uneCarteWagonAEteChoisie(d));
                                vueCarteWagon.setId(d+"");
                                vueCarteWagon.setAlignment(Pos.TOP_RIGHT);
                                cartesVisibles.getChildren().add(vueCarteWagon);
                            }
                        }
                        else if (change.wasRemoved()) {
                            for (ICouleurWagon d : change.getRemoved()) {
                                if(change.getRemovedSize()>1){
                                    cartesVisibles.getChildren().clear();
                                }
                                else {
                                    cartesVisibles.getChildren().remove(trouveLabelcouleurWagon(d + ""));
                                }
                            }
                        }

                    }
                });
                }
            };


        jeu.cartesWagonVisiblesProperty().addListener(affichageCVisibles);
        jeu.destinationsInitialesProperty().addListener(affichageDest);




        passer.setOnMouseClicked(passer -> {jeu.passerAEteChoisi();cartesVisibles.setOpacity(100);cartesVisibles.toFront();});
        pioche.setOnMouseClicked(pioche -> {jeu.uneCarteWagonAEtePiochee();cartesVisibles.setOpacity(100);cartesVisibles.toFront();});
        piocheDest.setOnMouseClicked(piocheDest -> {jeu.uneDestinationAEtePiochee();cartesVisibles.setOpacity(0);cartesVisibles.toBack();});

        joueurCourant.creerBindings();
        autresJoueurs.creerBindings();
        plateau.creerBindings();


    }


    /*public Button trouveLabelDestination(IDestination dest){
        Button solution = null;
        for(Node label : destinations.getChildren()){
            Button l = (Button) label;
            if(l.getText().equals(dest.getNom())){
                solution=l;
            }
        }
        return solution;
    }*/


    public int trouveLabelDestination(IDestination destination){
        int solution = 0;
        for(int i=0; i<jeu.destinationsInitialesProperty().size();i++){
            if(Objects.equals(destination.toString(), jeu.destinationsInitialesProperty().get(i).toString())){
                solution=i;
            }
        }
        return solution;
    }

    public VueCarteWagon trouveLabelcouleurWagon(String couleurWagon){
        for(int i=0; i<cartesVisibles.getChildren().size();i++){
            VueCarteWagon sl= (VueCarteWagon) cartesVisibles.getChildren().get(i);
            if(sl.getId().equals(couleurWagon)){
                return sl;
            }
        }
        return null;
    }

}