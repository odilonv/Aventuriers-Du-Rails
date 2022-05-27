package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends VBox {

    private ICouleurWagon couleurWagon;
    private VBox vueCartes;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
        Button button = new Button();
        ImageView imageView = new ImageView("images/cartesWagons/carte-wagon-"+couleurWagon.toString().toUpperCase()+".png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color:transparent");
        button.setAlignment(Pos.CENTER);

        button.setOnMouseEntered(mouseEvent -> button.setStyle("-fx-background-color:"+couleurEnglish(couleurWagon.toString())));
        button.setOnMouseExited(mouseEvent -> button.setStyle("-fx-background-color:transparent"));
        getChildren().add(button);

    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

    /**public void creerBindings() {
        ListChangeListener<ICouleurWagon> listChangeListener = new ListChangeListener<ICouleurWagon>() {
            @Override
            public void onChanged(Change<? extends ICouleurWagon> change) {
                Platform.runLater(() -> {
                            while(change.next()) {
                                if (change.wasAdded()) {
                                    for (ICouleurWagon d : change.getAddedSubList()) {
                                        System.out.println(d.toString().toUpperCase() + " LALALALLALALALALA");
                                        Button button = new Button();
                                        ImageView imageView = new ImageView("images/cartesWagons/carte-wagon-" + change.toString().toUpperCase() + ".png");
                                        imageView.setPreserveRatio(true);
                                        imageView.setFitHeight(80);
                                        button.setGraphic(imageView);
                                        vueCartes.getChildren().add(button);
                                    }
                                }
                            }

                });
            }
        };
        //((VueDuJeu) getScene().getRoot()).getJeu().().addListener(changeListener);
    }*/

    public String couleurEnglish(String c) {
        switch (c){
            case "Rouge":
                return "red";
            case "Bleu":
                return "blue";
            case "Jaune":
                return "yellow";
            case "Rose":
                return "pink";
            case "Vert":
                return "green";
            case "Blanc":
                return "white";
            case "Locomotive":
                return "grey";
            case "Noir":
                return "black";
            case "Orange":
                return "orange";
        }
        return "";
    }



}
