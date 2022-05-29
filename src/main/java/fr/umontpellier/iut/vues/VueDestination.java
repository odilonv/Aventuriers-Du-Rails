package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends HBox {

    private IDestination destination;
    private Button de;

    public VueDestination(IDestination destination, boolean b) {
        this.destination = destination;
        de = new Button(destination.getNom());
        de.setStyle("-fx-background-color: #F7E6BC");
        de.setFont(Font.font("Georgia", 15));
        if(!b) {
            //de.setAlignment(Pos.CENTER);
            de.setPrefSize(150, 15);
            de.setMaxSize(150, 20);
            de.setOnMouseEntered(mouseEvent -> de.setPrefSize(150, 20));
            de.setOnMouseExited(mouseEvent -> de.setPrefSize(150, 15));
            de.setFont(Font.font("Georgia", 7));
        }
        else {
            de.setPrefSize(300,10);
            de.setMaxSize(300,20);
            de.setFont(Font.font("Georgia", 14));
            de.setAlignment(Pos.CENTER);
            //de.setOnMouseEntered(mouseEvent -> de.setStyle("-fx-background-color: white"));
            //de.setOnMouseExited(mouseEvent -> de.setStyle("-fx-background-color: #F7E6BC"));
            //de.setOnMouseClicked(destinations -> ((VueDuJeu) getScene().getRoot()).getJeu().destinationsInitialesProperty().remove(trouveLabelDestination(getDestination())));
        }
        getChildren().add(de);

    }

    public int trouveLabelDestination(IDestination destination){
        int solution = 0;
        for(int i=0; i<((VueDuJeu) getScene().getRoot()).getJeu().destinationsInitialesProperty().size();i++){
            if(Objects.equals(destination.toString(), ((VueDuJeu) getScene().getRoot()).getJeu().destinationsInitialesProperty().get(i).toString())){
                solution=i;
            }
        }
        return solution;
    }

    public Button getDe() {
        return de;
    }

    public IDestination getDestination() {
        return destination;
    }

}
