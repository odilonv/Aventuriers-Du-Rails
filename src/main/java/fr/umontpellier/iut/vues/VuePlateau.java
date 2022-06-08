package fr.umontpellier.iut.vues;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Joueur;
import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.Ville;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    private IJeu jeu;

    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void choixRouteOuVille(Event e) {
        Node node = (Node) e.getSource();
        jeu.uneVilleOuUneRouteAEteChoisie(node.getId());
    }

    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    public void creerBindings() {
        jeu = ((VueDuJeu) getScene().getRoot()).getJeu();
        //bindRedimensionPlateau();
        setProprietaire();
    }

    public void setProprietaire() {

        for (Object o : jeu.getRoutes()) {
            Route r = (Route) o;
            r.proprietaireProperty().addListener(new ChangeListener<Joueur>() {
                @Override
                public void changed(ObservableValue<? extends Joueur> arg0, Joueur arg1, Joueur arg2) {
                    setWagon(r, arg2);
                }
            });
        }
        for (Object o : jeu.getVilles()) {
            Ville v = (Ville) o;
            v.proprietaireProperty().addListener(new ChangeListener<Joueur>() {
                @Override
                public void changed(ObservableValue<? extends Joueur> arg0, Joueur arg1, Joueur arg2) {
                    setGare(v, arg2);
                }
            });
        }
    }

    public void setWagon(Route r, Joueur j) {

        for (Node p : routes.getChildren()) {
            Group gRoute = (Group) p;
            if (p.getId().equals(r.getNom())) {
                for (Node nRect : gRoute.getChildren()) {
                    Rectangle rect = (Rectangle) nRect;
                    Platform.runLater(() -> {
                        Image image = new Image("images/wagons/image-wagon-" + j.getCouleur() + ".png");
                        rect.setFill(new ImagePattern(image, rect.getX(), rect.getY(),rect.getWidth(), rect.getHeight(), false));
                        getChildren().add(rect);
                    });
                }
                break;
            }
        }
    }

    public void setGare(Ville v, Joueur j) {

        for (Node p : villes.getChildren()) {
            if (p.getId().equals(v.getNom())) {
                Circle c = (Circle) p;
                Platform.runLater(() -> {
                    ImageView image = new ImageView("images/gares/gare-" + j.getCouleur() + ".png");
                    image.setPreserveRatio(true);
                    image.setFitHeight(27);
                    image.setEffect(new DropShadow(20, Color.BLACK));
                    image.layoutXProperty().bind(c.layoutXProperty().divide(1.02));
                    image.layoutYProperty().bind(c.layoutYProperty().divide(1.06));
                    image.xProperty().bind(c.centerXProperty());
                    image.yProperty().bind(c.centerYProperty());
                    image.translateXProperty().bind(c.translateXProperty().divide(1.06));
                    image.translateYProperty().bind(c.translateYProperty().divide(1.06));
                    getChildren().add(image);
                });
                break;
            }
        }
    }


    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();
//        Les dimensions de l'image varient avec celle de la scène

        image.fitWidthProperty().bind(getScene().widthProperty().multiply(0.7));
        image.fitHeightProperty().bind(getScene().heightProperty().multiply(0.7));
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {

        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });

        // EPAISSEUR ET LONGUEUR
        rect.widthProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.largeurRectangle * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;

            }
        });
        rect.heightProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurRectangle * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;

            }
        });

        //X ET Y
        rect.xProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.xInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.yProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.yInitial * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {

        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }
}

