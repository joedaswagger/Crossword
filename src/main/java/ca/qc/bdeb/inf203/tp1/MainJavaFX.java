package ca.qc.bdeb.inf203.tp1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.util.Arrays;


public class MainJavaFX extends Application {
    String texto; //Paramètres pour éviter l'errerur de "effectively final"
    int position;
    String duplicate;
    MotsCroisees motsCroiseesHelp;
    MotsCroisees motsCroisees;
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception {

        var root = new BorderPane();
        var scene = new Scene(root, 1000, 1000);

        stage.getIcons().add(new Image("icon.png"));

        var vboxMere = new VBox(); //initialisation des boîtes
        var hboxTitre = new HBox();
        var hboxChoice = new HBox();
        var vboxChoice = new VBox();
        var hboxText = new HBox();

        hboxTitre.setAlignment(Pos.CENTER); //Alignement des boîtes
        hboxChoice.setAlignment(Pos.CENTER);
        hboxText.setAlignment(Pos.CENTER);
        vboxMere.setSpacing(30);

        var img = new Image("mots.png"); //Initialisation image
        var imageView = new ImageView(img);
        imageView.setFitWidth(125);
        imageView.setPreserveRatio(true);

        var titre = new Text("Super Mots-Croisés Master 3000"); //initialisation titre
        titre.setFont(Font.font("monospace", 20));
        var changer = new Text("Changer de grille");

        String[] optionsMotCroises = {"mots-croises1.txt", "mots-croises2.txt", "mots-croises3.txt", //initialisation ComboBox
                "invalide1.txt", "invalide2.txt", "invalide3.txt"};
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(optionsMotCroises));

        FileChooser select = new FileChooser(); //Initialisation FileChooser (Source: https://jenkov.com/tutorials/javafx/filechooser.html)
        Button load = new Button("Ouvrir un fichier...");


        GridPane grid = new GridPane(); //initialisation GridPane
        grid.setAlignment(Pos.BOTTOM_CENTER);

        String nomFichier = optionsMotCroises[0]; //Initialisation "default" du mots-croisees1
        motsCroisees = new MotsCroisees(nomFichier);

        MotsCroisees motsCroisees1 = motsCroisees;

        String[][] tableau =  motsCroisees.tableau(); //initialisation des variables nécessaires
        char[][] grille = motsCroisees.grilleCachee();
        char[][] copieReponses = motsCroisees.grilleReponse();
        String[] description = motsCroisees.Descriptions();
        String[] orientation = motsCroisees.orientation();
        String[] mots = motsCroisees.tabMots();
        int[] numColonnes = motsCroisees.numColonne;
        int[] numLignes = motsCroisees.numLigne;

        dessiner(mots, grille, grid, copieReponses); //Initialisation du méthode dessiner

        var vboxDesc = new VBox();
        affichageReponseEtDesc(mots, hboxText, orientation, grille, numLignes, numColonnes, grid, copieReponses, description, vboxDesc);

        comboBox.setOnAction(event -> { // Changement de fichier à lire quand on clique sur un choix de ComboBox et réinitialisation/relecture
            motsCroiseesHelp = motsCroisees1;
            duplicate = (String) comboBox.getValue();
            motsCroiseesHelp = new MotsCroisees(duplicate);
            motsCroisees = motsCroiseesHelp;

            String[][] tableau2 =  motsCroisees.tableau();
            char[][] grille2 = motsCroisees.grilleCachee();
            char[][] copieReponses2 = motsCroisees.grilleReponse();
            String[] description2 = motsCroisees.Descriptions();
            String[] orientation2 = motsCroisees.orientation();
            String[] mots2 = motsCroisees.tabMots();
            int[] numColonnes2 = motsCroisees.numColonne;
            int[] numLignes2 = motsCroisees.numLigne;

            grid.getChildren().clear();
            vboxDesc.getChildren().clear();
            hboxText.getChildren().clear();

            dessiner(mots2,grille2,grid,copieReponses2);
            affichageReponseEtDesc(mots2, hboxText, orientation2, grille2, numLignes2, numColonnes2, grid, copieReponses2, description2, vboxDesc);

            boolean verif = MotsCroisees.validite(tableau2); //Vérification avec méthode validite
            if (!verif) {
                grid.getChildren().clear();
                vboxDesc.getChildren().clear();
                hboxText.getChildren().clear();
                var invalid = new Text("Fichier invalide");
                invalid.setFont(Font.font("monospace", 20));
                invalid.setFill(Color.RED);
                hboxText.getChildren().add(invalid);
            }
        });
        load.setOnAction(event -> { // Même chose, mais avec la lecture de fichier et un try/catch pour gérer les exceptions
            File selectedFile = select.showOpenDialog(stage);
            try {
                motsCroiseesHelp = motsCroisees1;
                duplicate = selectedFile.getName();
                motsCroiseesHelp = new MotsCroisees(duplicate);
                motsCroisees = motsCroiseesHelp;

                String[][] tableau2 =  motsCroisees.tableau();

                char[][] grille2 = motsCroisees.grilleCachee();
                char[][] copieReponses2 = motsCroisees.grilleReponse();
                String[] description2 = motsCroisees.Descriptions();
                String[] orientation2 = motsCroisees.orientation();
                String[] mots2 = motsCroisees.tabMots();
                int[] numColonnes2 = motsCroisees.numColonne;
                int[] numLignes2 = motsCroisees.numLigne;

                grid.getChildren().clear();
                vboxDesc.getChildren().clear();
                hboxText.getChildren().clear();

                dessiner(mots2,grille2,grid,copieReponses2);
                affichageReponseEtDesc(mots2, hboxText, orientation2, grille2, numLignes2, numColonnes2, grid, copieReponses2, description2, vboxDesc);
            } catch (Exception e) {
                var invalid = new Text("Fichier invalide");
                invalid.setFont(Font.font("monospace", 20));
                invalid.setFill(Color.RED);
                hboxText.getChildren().add(invalid);
            }

        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        }); //Code pour fermer le fichier avec Escape. Source: https://stackoverflow.com/questions/14357515/javafx-close-window-on-pressing-esc

        root.setTop(hboxTitre);
        root.setCenter(vboxMere);

        vboxMere.getChildren().add(hboxTitre);
        hboxTitre.getChildren().add(imageView);
        hboxTitre.getChildren().add(titre);

        vboxMere.getChildren().add(hboxChoice);
        hboxChoice.getChildren().add(changer);
        hboxChoice.getChildren().add(vboxChoice);
        vboxChoice.getChildren().add(comboBox);
        vboxChoice.getChildren().add(load);

        vboxMere.getChildren().add(hboxText);

        vboxMere.getChildren().add(grid);
        vboxMere.getChildren().add(vboxDesc);

        stage.setTitle("Mots Croisés");
        stage.setScene(scene);
        stage.show();
    }
    public void dessiner(String[] mots, char[][] grille, GridPane grid, char[][] copieReponses) { //Méthode dessiner pour créer la grille
        for (int i = 0; i < mots.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                for (int k = 0; k < grille[0].length; k++) {
                    var cellule = new HBox();
                    cellule.setPadding(new Insets(3, 8, 3, 8));
                    cellule.setMaxSize(30, 30);
                    cellule.setMinSize(30, 30);

                    if(grille[j][k] == '*') {
                        cellule.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        Color color = Color.GRAY;
                        cellule.setBackground(new Background(new BackgroundFill(color, null, null)));
                    }
                    else {
                        cellule.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        Color color = Color.WHITE;
                        cellule.setBackground(new Background(new BackgroundFill(color, null, null)));

                        if(grille[j][k] != '*' && grille[j][k] == copieReponses[j][k]) {
                            String lettreMotString = String.valueOf(copieReponses[j][k]);
                            var lettre = new Text(lettreMotString);
                            lettre.setFont(Font.font("monospace", 20));
                            cellule.getChildren().add(lettre);
                            Color colorGood = Color.GREEN;
                            cellule.setBackground(new Background(new BackgroundFill(colorGood, null, null)));
                        }
                    }
                    int[] tableauAscii = {49,50,51,52,53,54,55,56,57};

                    for (int l = 0; l < tableauAscii.length; l++) {
                        char numEnChar = (char) tableauAscii[l];
                        if (grille[j][k] == numEnChar) {
                            char numChar = (char) tableauAscii[l];
                            String num = String.valueOf(numChar);
                            var numero = new Text(num);
                            numero.setFont(Font.font(10));
                            cellule.getChildren().add(numero);
                        }
                    }
                    grid.add(cellule, k, j);
                }
            }
        }
    }
    public void affichageReponseEtDesc(String[] mots, HBox hboxText, String[] orientation, char[][] grille, int[] numLignes, int[] numColonnes, GridPane grid, char[][] copieReponses, String[] description, VBox vboxDesc) {
        for (int i = 0; i < mots.length; i++) {
            String numMot = String.valueOf((i + 1));
            var hboxDesc = new HBox();
            var textNum = new Text(numMot + ". ");
            var textBox = new TextField();

            int iTemporaire = i;
            textBox.setOnAction(event -> { //Enregistre ce qui était écrit dans le textBox, le compare avec les mots possibles. S'ils sont la même, le programme place ses lettres dans la grille. Il affiche également un message de victoire, de bonne ou de mauvaise réponse.
                position = iTemporaire;
                texto = textBox.getText();
                if(MotsCroisees.analyse(mots[position], texto)) {
                    char[] textChar = texto.toCharArray();
                    hboxText.getChildren().clear();
                    for (int j = 0; j < textChar.length; j++) {
                        if (orientation[position].equals("H")) {
                            grille[numLignes[position]][numColonnes[position] + j] = textChar[j];
                        }
                        else if (orientation[position].equals("V")) {
                            grille[numLignes[position] + j][numColonnes[position]] = textChar[j];
                        }
                    }
                    var nice = new Text("Bonne réponse !");
                    nice.setFont(Font.font("monospace", 20));
                    nice.setFill(Color.GREEN);
                    hboxText.getChildren().add(nice);
                    textBox.setDisable(true);
                    dessiner(mots, grille, grid, copieReponses);
                } else {
                    hboxText.getChildren().clear();
                    var wrong = new Text("Mauvaise réponse!");
                    wrong.setFont(Font.font("monospace", 20));
                    wrong.setFill(Color.RED);
                    hboxText.getChildren().add(wrong);
                }
                if (Arrays.deepEquals(grille, copieReponses)){ //Arrays.deepEquals compare 2 tableaux 2D (Source: https://stackoverflow.com/questions/2721033/java-arrays-equals-returns-false-for-two-dimensional-arrays)
                    hboxText.getChildren().clear();
                    var win = new Text("TU AS GAGNÉ!!!!!!");
                    win.setFont(Font.font("monospace", 20));
                    win.setFill(Color.GREEN);
                    hboxText.getChildren().add(win);
                }
            });

            var desc = new Text(description[i]); //Emplacement de la description
            hboxDesc.getChildren().add(textNum);
            hboxDesc.getChildren().add(textBox);
            hboxDesc.getChildren().add(desc);
            vboxDesc.getChildren().add(hboxDesc);
        }
    }
}