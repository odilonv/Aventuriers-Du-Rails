package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends GridPane {
    private Label nomJoueur;
    private VBox cartes;
    private ImageView photoJoueur;

    public VueJoueurCourant(){
        nomJoueur= new Label();
        nomJoueur.setFont(Font.font("Courrier", FontWeight.NORMAL,30));
        nomJoueur.setStyle("-fx-text-fill: white");

        cartes = new VBox();
        photoJoueur = new ImageView();
        photoJoueur.setPreserveRatio(true);
        photoJoueur.setFitHeight(140);

        ColumnConstraints premiercol=new ColumnConstraints();
        premiercol.setPercentWidth(5);
        ColumnConstraints deuxiemecol=new ColumnConstraints();
        deuxiemecol.setPercentWidth(20);
        ColumnConstraints troisiemecol =new ColumnConstraints();
        troisiemecol.setPercentWidth(5);
        ColumnConstraints quatriemecol =new ColumnConstraints();
        quatriemecol.setPercentWidth(60);
        RowConstraints ligne1 = new RowConstraints();
        ligne1.setPercentHeight(40);
        RowConstraints ligne2 = new RowConstraints();
        ligne2.setPercentHeight(4);
        RowConstraints ligne3 = new RowConstraints();
        ligne3.setPercentHeight(2);

        /*RowConstraints ligne3 = new RowConstraints();
        ligne2.setPercentHeight(100);*/
        getColumnConstraints().addAll(premiercol,deuxiemecol,troisiemecol,quatriemecol);
        getRowConstraints().addAll(ligne1,ligne2,ligne3);
        add(nomJoueur,1,2);
        add(cartes,1,3);
        add(photoJoueur, 1,1);

    }

    public void creerBindings() {
        ChangeListener<IJoueur> changeListener = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    nomJoueur.setText(t1.getNom());
                    cartes.getChildren().clear();
                    Image i1 = new Image("images/personnages/avatar-"+ t1.getCouleur().name()+ ".png");
                    photoJoueur.setImage(i1);
                    for(int i=0; i<t1.getCartesWagon().size();i++){
                        Button button = new Button();
                        ImageView imageView = new ImageView("images/cartesWagons/carte-wagon-"+t1.getCartesWagon().get(i).name()+".png");
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(80);
                        button.setGraphic(imageView);
                        cartes.getChildren().add(button);

                    }

                });
            }
        };

        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(changeListener);

    }


}
