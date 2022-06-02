package fr.umontpellier.iut.vues;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;
    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        Button retirer = new Button();
        nomsJoueurs = FXCollections.observableArrayList();
        Button passer = new Button("Ajouter un joueur");

        Button start = new Button("JOUER");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.web("#F7E6BC"));
        passer.setAlignment(Pos.CENTER);
        passer.setFont(Font.font("Georgia", 20));
        passer.setStyle("-fx-border-color: yellow");
        passer.setStyle("-fx-background-color: #F7E6BC");
        passer.setEffect(dropShadow);
        passer.setPrefSize(400,80);
        passer.setOnMouseEntered(mouseEvent -> {passer.setPrefSize(440,100);passer.setStyle("-fx-background-color: #F5E9D7");});
        passer.setOnMouseExited(mouseEvent -> {passer.setPrefSize(400,80);passer.setStyle("-fx-background-color: #F7E6BC");});

        start.setAlignment(Pos.CENTER);
        start.setFont(Font.font("Georgia", 20));
        start.setStyle("-fx-border-color: green");
        start.setStyle("-fx-background-color: #0AD200");
        DropShadow dp = new DropShadow();
        dp.setRadius(20.0);
        dp.setOffsetX(0);
        dp.setOffsetY(1.0);
        dp.setColor(Color.RED);
        start.setEffect(dropShadow);
        start.setPrefSize(300,80);
        start.setOnMouseEntered(mouseEvent -> {start.setPrefSize(360,120);start.setStyle("-fx-background-color: #2EE800");});
        start.setOnMouseExited(mouseEvent -> {start.setPrefSize(300,80);start.setStyle("-fx-background-color: #2EE800");});

        retirer.setAlignment(Pos.CENTER_RIGHT);
        retirer.setStyle("-fx-border-color: red");
        retirer.setStyle("-fx-background-color: red");
        retirer.setEffect(dp);
        retirer.setPrefSize(80,70);
        retirer.setOnMouseEntered(mouseEvent -> {retirer.setPrefSize(90,80);retirer.setStyle("-fx-background-color: red");});
        retirer.setOnMouseExited(mouseEvent -> {retirer.setPrefSize(80,70);retirer.setStyle("-fx-background-color: red");});

        GridPane gridPane = new GridPane();
        Image bg = new Image("images/backgrounds/LUI.jpg");
        BackgroundImage bImg = new BackgroundImage(bg,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        gridPane.setBackground(bGround);
        ColumnConstraints premiercol=new ColumnConstraints();
        ColumnConstraints deuxiemecol=new ColumnConstraints();
        ColumnConstraints troisiemecol=new ColumnConstraints();
        premiercol.setPercentWidth(33);
        deuxiemecol.setPercentWidth(33);
        troisiemecol.setPercentWidth(33);
        RowConstraints premierl=new RowConstraints();
        RowConstraints deuxiemel=new RowConstraints();
        RowConstraints troisiemel=new RowConstraints();
        premierl.setPercentHeight(30);
        deuxiemel.setPercentHeight(75);
        troisiemel.setPercentHeight(20);


        gridPane.getColumnConstraints().addAll(premiercol, deuxiemecol, troisiemecol);
        gridPane.getRowConstraints().addAll(premierl,deuxiemel,troisiemel);
        gridPane.add(vbox, 1, 1);



        setMaximized(true);
        Scene s1 = new Scene(gridPane);
        vbox.setAlignment(Pos.TOP_CENTER);

        ArrayList<String> liste = new ArrayList<>();
        TextField j1 = new TextField();
        TextField j2 = new TextField();
        TextField j3 = new TextField();
        TextField j4 = new TextField();
        TextField j5 = new TextField();

        TextField nbJoueurs = new TextField();

        j1.setPrefSize(200,20);
        j2.setPrefSize(20,20);
        j3.setPrefSize(20,20);
        j4.setPrefSize(20,20);
        j5.setPrefSize(20,20);

        j1.setFont(Font.font("Georgia", 30));
        j2.setFont(Font.font("Georgia", 30));
        j3.setFont(Font.font("Georgia", 30));
        j4.setFont(Font.font("Georgia", 30));
        j5.setFont(Font.font("Georgia", 30));

        Button espace = new Button("espace");
        espace.setPrefSize(100,30);
        espace.setOpacity(0);

        hbox.setAlignment(Pos.CENTER);

        hbox.setSpacing(25);


        vbox.getChildren().addAll(j1,espace,hbox);

        HBox hstart = new HBox();

        hstart.setAlignment(Pos.CENTER);

        gridPane.add(hstart,1,2);




        hbox.getChildren().add(passer);
        vbox.setSpacing(15);
        hstart.setAlignment(Pos.TOP_CENTER);



        passer.setOnMouseClicked(event -> {
            if(!vbox.getChildren().contains(j2)) {
                retirer.setOpacity(100);
                vbox.getChildren().removeAll(espace,hbox);
                if(!hbox.getChildren().contains(retirer)) {
                    hbox.getChildren().add(retirer);
                }
                vbox.getChildren().addAll(j2,espace,hbox);
                if(!hstart.getChildren().contains(start)){
                    hstart.getChildren().add(start);
                }

            }
            else if(!vbox.getChildren().contains(j3)) {
                vbox.getChildren().removeAll(espace,hbox);
                vbox.getChildren().addAll(j3,espace,hbox);
            }
            else if(!vbox.getChildren().contains(j4)){
                vbox.getChildren().removeAll(espace,hbox);
                vbox.getChildren().addAll(j4,espace,hbox);
            }
            else if(!vbox.getChildren().contains(j5)){

                vbox.getChildren().removeAll(espace,hbox);
                passer.setOpacity(0);
                vbox.getChildren().addAll(j5,espace,hbox);
                hbox.getChildren().remove(passer);
            }
        });
            retirer.setOnMouseClicked(eve -> {
                if(vbox.getChildren().contains(j5)){
                    hbox.getChildren().remove(retirer);
                    if(!hbox.getChildren().contains(passer)) {
                        hbox.getChildren().add(passer);
                    }
                    hbox.getChildren().add(retirer);
                    passer.setOpacity(100);
                    vbox.getChildren().remove(j5);
                }
                else if(vbox.getChildren().contains(j4)){
                    vbox.getChildren().remove(j4);
                }
                else if(vbox.getChildren().contains(j3)){
                    vbox.getChildren().remove(j3);
                }
                else if(vbox.getChildren().contains(j2)){
                    vbox.getChildren().remove(j2);
                    hbox.getChildren().remove(retirer);
                    retirer.setOpacity(0);
                    hstart.getChildren().remove(start);

                }


                });
        ArrayList<TextField> listefield = new ArrayList<>();
        listefield.add(j1);
        listefield.add(j2);
        if(vbox.getChildren().contains(j3)){
            listefield.add(j3);
        }
        if(vbox.getChildren().contains(j4)){
            listefield.add(j4);
        }
        if(vbox.getChildren().contains(j5)){
            listefield.add(j5);
        }
        start.setOnMouseClicked(event -> {
            for(TextField txt : listefield){
                nomsJoueurs.add(txt.getText());
            }
            hide();
        });



        setScene(s1);

    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {}

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return nomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return nomsJoueurs.get(playerNumber);
    }

}
