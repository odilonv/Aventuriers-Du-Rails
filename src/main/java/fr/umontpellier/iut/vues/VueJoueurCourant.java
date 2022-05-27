package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends GridPane {
    private Label nomJoueur;
    private Label fondnomJ;
    private VBox cartesG;
    private VBox cartesD;
    private VBox cartesDD;

    private GridPane scrollPane;

    private ImageView photoJoueur;
    private ImageView fondphotoJ;

    public VueJoueurCourant() {

        fondnomJ = new Label();
        fondnomJ.setFont(Font.font("Georgia", 30));
        fondnomJ.setEffect(new GaussianBlur());

        nomJoueur = new Label();
        nomJoueur.setFont(Font.font("Georgia", 30));
        nomJoueur.setStyle("-fx-text-fill: white");

        //nomJoueur.setFont(Font.loadFont("file:resources/fonts/IMFellEnglishSC-Regular.ttf", 30));



        cartesG = new VBox();
        cartesG.setStyle("-fx-background-color: transparent");
        //cartes.setStyle("-fx-opacity: 50");
        cartesG.setPrefSize(440, 440);
        cartesG.setMaxSize(440, 440);
        //cartes.setAlignment(Pos.CENTER);

        cartesD = new VBox();
        cartesD.setStyle("-fx-background-color: transparent");
        //cartes.setStyle("-fx-opacity: 50");
        cartesD.setPrefSize(440, 440);
        cartesD.setMaxSize(440, 440);
        //cartes.setAlignment(Pos.CENTER);

        cartesDD = new VBox();
        cartesD.setStyle("-fx-background-color: transparent");
        //cartes.setStyle("-fx-opacity: 50");
        cartesD.setPrefSize(440, 440);
        cartesD.setMaxSize(440, 440);
        //cartes.setAlignment(Pos.CENTER);

        scrollPane = new GridPane();
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setPrefSize(440, 500);
        scrollPane.setMaxSize(440, 500);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        scrollPane.getColumnConstraints().addAll(col1,col2,col3);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.add(cartesG, 0,0);
        scrollPane.add(cartesD,1,0);
        scrollPane.add(cartesDD,2,0);

        fondphotoJ = new ImageView();
        fondphotoJ.setPreserveRatio(true);
        fondphotoJ.setFitHeight(140);
        fondphotoJ.setEffect(new GaussianBlur());
        photoJoueur = new ImageView();
        photoJoueur.setPreserveRatio(true);
        photoJoueur.setFitHeight(140);


        ColumnConstraints premiercol = new ColumnConstraints();
        premiercol.setPercentWidth(5);
        ColumnConstraints deuxiemecol = new ColumnConstraints();
        deuxiemecol.setPercentWidth(80);
        ColumnConstraints troisiemecol = new ColumnConstraints();
        troisiemecol.setPercentWidth(15);
        ColumnConstraints quatriemecol = new ColumnConstraints();
        quatriemecol.setPercentWidth(60);
        RowConstraints ligne1 = new RowConstraints();
        ligne1.setPercentHeight(26);
        RowConstraints ligne2 = new RowConstraints();
        ligne2.setPercentHeight(8);
        RowConstraints ligne3 = new RowConstraints();
        ligne3.setPercentHeight(4);

        /*RowConstraints ligne3 = new RowConstraints();
        ligne2.setPercentHeight(100);*/
        getColumnConstraints().addAll(premiercol, deuxiemecol, troisiemecol);
        getRowConstraints().addAll(ligne1, ligne2, ligne3);


        add(nomJoueur, 1, 2);
        add(fondnomJ, 1,2);
        add(scrollPane, 1, 3);
        add(fondphotoJ, 1, 1);
        add(photoJoueur, 1, 1);

    }

    public void creerBindings() {
        ChangeListener<IJoueur> changeListener = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setRadius(8.0);
                    dropShadow.setOffsetX(0);
                    dropShadow.setOffsetY(1.0);
                    dropShadow.setColor(Color.web(couleurEnglish(t1.getCouleur().name())));

                    scrollPane.setStyle("-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, "+couleurEnglish(t1.getCouleur().name())+", rgba(244,244,244,0))");

                    //scrollPane.setStyle("-fx-background-opacity: 0.5");
                    fondnomJ.setText(t1.getNom());
                    nomJoueur.setText(t1.getNom());
                    fondnomJ.setStyle("-fx-text-fill:" + couleurEnglish(t1.getCouleur().name()));
                    nomJoueur.setStyle("-fx-text-fill:" + couleurEnglish(t1.getCouleur().name()));

                    Image i1 = new Image("images/personnages/avatar-" + t1.getCouleur().name() + ".png");
                    fondphotoJ.setImage(i1);
                    photoJoueur.setImage(i1);
                    cartesG.getChildren().clear();
                    cartesD.getChildren().clear();
                    cartesDD.getChildren().clear();
                    for (int i = 0; i < t1.cartesWagonProperty().size(); i++) {
                        if(i<9) {
                            VueCarteWagon vue = new VueCarteWagon(t1.cartesWagonProperty().get(i));
                            cartesG.getChildren().add(vue);
                        }
                        else if(i>=9 && i<18){
                            VueCarteWagon vue = new VueCarteWagon(t1.cartesWagonProperty().get(i));
                            cartesD.getChildren().add(vue);
                        }
                        else if(i>=18){
                            VueCarteWagon vue = new VueCarteWagon(t1.cartesWagonProperty().get(i));
                            cartesDD.getChildren().add(vue);
                        }
                    }

                    ListChangeListener<ICouleurWagon> listChangeListener = new ListChangeListener<ICouleurWagon>() {
                        @Override
                        public void onChanged(Change<? extends ICouleurWagon> change) {
                            Platform.runLater(() -> {
                                while (change.next()) {
                                    if (change.wasAdded() && !change.getAddedSubList().isEmpty()) {
                                        System.out.println(change.getAddedSubList().get(0).toString());
                                        VueCarteWagon vue = new VueCarteWagon(change.getAddedSubList().get(0));
                                        if(t1.cartesWagonProperty().size()<9) {
                                            cartesG.getChildren().add(vue);
                                        }
                                        else if(t1.cartesWagonProperty().size()>=9 && t1.cartesWagonProperty().size()<18){
                                            cartesD.getChildren().add(vue);
                                        }
                                        else if(t1.cartesWagonProperty().size()>=18) {
                                            cartesDD.getChildren().add(vue);
                                        }
                                    }
                                }
                            });

                        }
                    };
                    ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().getValue().cartesWagonProperty().addListener(listChangeListener);
                });
            }
        };

        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(changeListener);
    }



    public String couleurEnglish(String c) {
        switch (c){
            case "ROUGE":
                return "red";
            case "BLEU":
                return "blue";
            case "JAUNE":
                return "yellow";
            case "ROSE":
                return "pink";
            case "VERT":
                return "green";
        }
        return "";
    }
}
