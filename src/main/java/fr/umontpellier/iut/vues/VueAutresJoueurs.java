package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private HBox elements;



    public VueAutresJoueurs(){
        listJoueurs = new ArrayList<>();
        listbis = new ArrayList<>();
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(5);


    }


    public void creerBindings(){
        ChangeListener<IJoueur> changeInstruction = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur agr1, IJoueur agr2) {
                Platform.runLater(() -> {
                        if(getChildren().isEmpty()) {
                            listJoueurs.addAll(((VueDuJeu) getScene().getRoot()).getJeu().getJoueurs());
                            listJoueurs.remove(agr2);

                            for (int i = 0; i < listJoueurs.size(); i++) {
                                panneauJoueur(listJoueurs.get(i));
                                getChildren().add(panneauJoueur(listJoueurs.get(i)));
                            }
                        }
                        else{

                            getChildren().remove(0);
                            getChildren().add(panneauJoueur(agr1));
                        }






                        /*
                        getChildren().add(getChildren().remove(0));
                        getChildren().get(getChildren().size()-1).setOpacity(0);
                        getChildren().get(getChildren().size()-2).setOpacity(100);*/


                });
            }
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(changeInstruction);
    }

    public VBox panneauJoueur(IJoueur j1){
        VBox perso = new VBox();
        perso.setPrefSize(100,80);
        perso.setMaxSize(100,80);
        Button button = new Button(j1.getNom());
        button.setPrefSize(100,20);
        button.setStyle("-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, "+couleurEnglish(j1.getCouleur().name())+", rgba(244,244,244,0)) ; -fx-text-fill: white");
        elements = new HBox();
        ImageView photoJoueur = new ImageView();
        photoJoueur.setPreserveRatio(true);
        photoJoueur.setFitHeight(80);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.web(couleurEnglish(j1.getCouleur().name())));



        String n = ""+j1.getNbWagons();
        Button nb= new Button(n);
        nb.setFont(Font.font("Georgia", 10));
        nb.setStyle("-fx-background-color: transparent ; -fx-text-fill: "+couleurEnglish(j1.getCouleur().name())+";");
        ImageView img = new ImageView("images/wagons/image-wagon-" + j1.getCouleur().name() + ".png");
        img.setFitHeight(30);
        img.setPreserveRatio(true);
        Button bimg = new Button();
        bimg.setGraphic(img);
        bimg.setStyle("-fx-background-color: transparent");
        bimg.setOnMouseEntered(affichage -> bimg.setGraphic(nb));
        bimg.setOnMouseExited(affichage -> bimg.setGraphic(img));
        nb.setEffect(dropShadow);
        bimg.setEffect(dropShadow);

        String ns = "Score : "+j1.getScore();
        Button nss= new Button(ns);
        nss.setFont(Font.font("Georgia", 10));
        nss.setStyle("-fx-background-color: transparent ; -fx-text-fill: "+couleurEnglish(j1.getCouleur().name())+";");
        nss.setEffect(dropShadow);

        String ng = ""+j1.getNbGares();
        Button ngg= new Button(ng);
        ngg.setFont(Font.font("Georgia", 10));
        ngg.setStyle("-fx-background-color: transparent ; -fx-text-fill: "+couleurEnglish(j1.getCouleur().name())+";");
        ImageView imgg = new ImageView("images/gares/gare-" + j1.getCouleur().name() + ".png");
        imgg.setFitHeight(30);
        imgg.setPreserveRatio(true);
        Button bimgg = new Button();
        bimgg.setGraphic(imgg);
        bimgg.setStyle("-fx-background-color: transparent");
        bimgg.setOnMouseEntered(affichage -> bimgg.setGraphic(ngg));
        bimgg.setOnMouseExited(affichage -> bimgg.setGraphic(imgg));
        ngg.setEffect(dropShadow);
        bimgg.setEffect(dropShadow);



        Image i1 = new Image("images/personnages/avatar-" + j1.getCouleur().name() + ".png");

        photoJoueur.setImage(i1);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(bimg,bimgg);
        elements.getChildren().addAll(photoJoueur,vBox);

        perso.getChildren().addAll(elements,button,nss);
        perso.setSpacing(10);
        return perso;
    }



    public String couleurEnglish(String c) {
        return switch (c) {
            case "ROUGE" -> "red";
            case "BLEU" -> "blue";
            case "JAUNE" -> "yellow";
            case "ROSE" -> "pink";
            case "VERT" -> "green";
            default -> "";
        };
    }

}
