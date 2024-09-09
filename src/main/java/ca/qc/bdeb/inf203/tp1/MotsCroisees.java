package ca.qc.bdeb.inf203.tp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MotsCroisees {
    protected static String nomFichier;
    protected static int nbLignes;
    protected static int nbColonnes;
    protected static String[][] motsCroises;
    protected static int[] numColonne;
    protected static int[] numLigne;

    public MotsCroisees(String nomFichier) { // constructeur pour pouvoir choisir le fichier dans JavaFX
        this.nomFichier = "src/main/resources/" + nomFichier;
    }

    public static String[][] tableau() { //convertit le .txt en tableau 2D et crée les tableaux des colonnes et lignes selon l'indice du mot

        BufferedReader fichier1;
        BufferedReader fichier2;
        {
            try {
                fichier1 = new BufferedReader(new FileReader(nomFichier));

                String line = fichier1.readLine();
                nbLignes = 0;
                nbColonnes = 0;
                while(line != null) {
                    if (line.startsWith("#")){
                        line = fichier1.readLine();
                    }
                    else {
                        String[] test = line.split(":");
                        nbColonnes = test.length;
                        line = fichier1.readLine();
                        nbLignes++;
                    }
                }

                motsCroises = new String[nbColonnes][nbLignes];
                numColonne = new int[nbLignes];
                numLigne = new int[nbLignes];

                fichier2 = new BufferedReader(new FileReader(nomFichier));
                String line2 =fichier2.readLine();
                int compteur2 = 0;
                while(line2 != null) {
                    if (line2.startsWith("#")){
                        line2 = fichier2.readLine();
                    }
                    else {
                        String[] test = line2.split(":");
                        for(int i = 0; i < test.length; i++) {
                            motsCroises[i][compteur2] = test[i];
                        }
                        numColonne[compteur2] = Integer.parseInt(test[1]);
                        numLigne[compteur2] = Integer.parseInt(test[2]);
                        line2 = fichier2.readLine();
                        compteur2++;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return motsCroises;
    }
    public static String[] tabMots() { //Crée un tableau des mots
        String[] tabMots = new String[motsCroises[0].length];
        for (int i = 0; i < motsCroises[0].length; i++) {
            tabMots[i] = motsCroises[0][i];
        }
        return tabMots;
    }
    public static String[] orientation() { //Crée un tableau d'orientation
        String[] orientation = new String[motsCroises[0].length];
        for (int i = 0; i < orientation.length; i++) {
            orientation[i] = motsCroises[3][i];
        }
        return orientation;
    }

    public static int Length() { //Détermine la longueur de la grille
        String[] ori = orientation();
        String[] placeholder = tabMots();
        int length = placeholder[0].length();
        for(int i = 1; i < placeholder.length; i++) {
            if(placeholder[i].length() + numColonne[i] > length && ori[i].equals("H")) {
                length = placeholder[i].length() + numColonne[i];
            }
        }
        return length;
    }
    public static int Height() { //Détermine l'hauteur de la grille
        String[] ori = orientation();
        String[] placeholder = tabMots();
        int length = placeholder[0].length();
        for(int i = 1; i < placeholder.length; i++) {
            if(placeholder[i].length() + numLigne[i] > length && ori[i].equals("V")) {
                length = numLigne[i] + placeholder[i].length();
            }
        }
        return length;
    }
    public static String[] Descriptions() { //Crée un tableau de descriptions
        String[] descriptions = new String[tabMots().length];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = motsCroises[4][i];
        }
        return descriptions;
    }
    public static char[][] grille() { //Dessine une grille rudimentaire qui se fait utiliser par grilleCachee et grilleReponse
        int length = MotsCroisees.Length();
        int height = MotsCroisees.Height();
        char[][] grille = new char[height][length];
        String[] mots = tabMots();
        String[] ori = orientation();
        int[] numColonnes = MotsCroisees.numColonne;
        int[] numLignes = MotsCroisees.numLigne;

        for(int i = 0; i < mots.length; i++) {
            char[] mot2enChar = mots[i].toCharArray();
            if (ori[i].equals("H")) {
                for(int j = 0; j < mot2enChar.length; j++) {
                    grille[numLignes[i]][numColonnes[i] + j] = mot2enChar[j];
                }
            } else if (ori[i].equals("V")) {
                for(int j = 0; j < mot2enChar.length; j++) {
                    grille[numLignes[i] + j][numColonnes[i]] = mot2enChar[j];
                }
            }

        }
        return grille;
    }

    public static char[][] grilleCachee() { //Crée la grille cachée avec les "?"
        char[][] grille = grille();
        String[] mots = tabMots();
        int[] numColonnes = MotsCroisees.numColonne;
        int[] numLignes = MotsCroisees.numLigne;
        for(int i = 0; i < mots.length; i++) {
            char[] motEnChar = mots[i].toCharArray();
            char test = (char) (49 + i);
            for(int j = 0; j < grille.length; j++) {
                for(int k = 0; k < grille[0].length; k++) {
                    if(grille[j][k] == 0) {
                        grille[j][k] = '*';
                    } else if (grille[j][k] == motEnChar[0]) {
                        grille[numLignes[i]][numColonnes[i]] = test;
                    }
                }
            }
        }
        int[] tableauAscii = {49,50,51,52,53,54,55,56,57}; //Valeurs ASCII de 1 à 9
        for(int i = 0; i < grille.length; i++) {
            for(int j = 0; j < grille[0].length; j++) {
                for(int k = 0; k < tableauAscii.length; k++) {
                    if(grille[i][j] != '*' && grille[i][j] != '1' && grille[i][j] != '2' && grille[i][j] != '3' && grille[i][j] != '4' && grille[i][j] != '5' && grille[i][j] != '6' && grille[i][j] != '7' && grille[i][j] != '8' && grille[i][j] != '9') {
                        grille[i][j] = '?';
                    }
                }
            }
        }
        return grille;
    }
    public static char[][] grilleReponse() { //Crée la grille complète avec tous les réponses, mais avec les "*"
        char[][] grilleReponse = grille();
        for (int i = 0; i < grilleReponse.length; i++) {
            for (int j = 0; j < grilleReponse[0].length; j++) {
                if(grilleReponse[i][j] == 0) {
                    grilleReponse[i][j] = '*';
                }
            }
        }
        return grilleReponse;
    }
    public static boolean analyse(String mot, String reponse) { //Méthode pour analyser si le mot réponse est bonne
        if (mot.equals(reponse)) {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean validite(String[][] tableau) { //Méthode pour vérifier si un fichier est valide
        boolean test = true;
        for (int i = 0; i < tableau.length; i++) {
            for (int j = 0; j < tableau[0].length; j++) {
                if(tableau[i][j] == null) {
                    test = false;
                    break;
                }
            }
        }
        return test;
    }
}
