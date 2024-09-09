package ca.qc.bdeb.inf203.tp1;

import java.util.Arrays;
import java.util.Scanner;

public class MainCmd  {

    public static void main(String[] args) {

        MotsCroisees motsCroisees = new MotsCroisees("mots-croises1.txt");// initialisation des paramètres

        String[][] what = motsCroisees.tableau();
        char[][] grille = motsCroisees.grilleCachee();
        char[][] copieReponses = motsCroisees.grilleReponse();
        String[] description = motsCroisees.Descriptions();
        String[] orientation = motsCroisees.orientation();
        String[] mots = motsCroisees.tabMots();
        int[] numColonnes = motsCroisees.numColonne;
        int[] numLignes = motsCroisees.numLigne;


        Scanner clavier = new Scanner(System.in);

        loop: while(!Arrays.deepEquals(grille, copieReponses)) { // Tant que la grille n'est pas complété, la boucle continue. deepEquals source: https://stackoverflow.com/questions/2721033/java-arrays-equals-returns-false-for-two-dimensional-arrays
            for(int j = 0; j < grille.length; j++) {   // Afficher la grille
                for(int k = 0; k< grille[0].length; k++) {
                    System.out.print(grille[j][k]);
                    System.out.print("    ");
                }
                System.out.println("\n");
            }
            for(int i = 0; i < description.length; i++) { //Afficher la description
                System.out.println((i+1) + ". " + description[i]);
            }
            System.out.println("Quel mot voulez-vous deviner?");
            System.out.println("q pour quitter et s pour la solution");
            String choix = clavier.next();

            switch(choix) {  //Un switch qui garde en compte ton choix
                case "q": //Si q, la boucle arrête
                    break loop;
                case "s": //Si s, le programme affiche la solution, puis arrête la boucle
                    for(int j = 0; j < copieReponses.length; j++) {
                        for (int k = 0; k < copieReponses[0].length; k++) {
                            System.out.print(copieReponses[j][k]);
                            System.out.print("    ");
                        }
                        System.out.println("\n");
                    }
                    break loop;
                case "1": //si c'est parmi 1-9, la boucle continue
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    int numChoix = Integer.parseInt(choix) - 1; //choix qui est transformé en int pour être servi comme indice pour trouver le mot
                    char[] mot = mots[numChoix].toCharArray(); // mot trouvé avec l'indice transformé en tableau de char
                    System.out.println("Tentative: ");
                    char[] reponse = clavier.next().toCharArray(); // réponse transformé en tableau de char
                    if(Arrays.equals(reponse, mot)) { //si le tableau réponse et le tableau mot sont égaux, le programme continue
                        for (int i = 0; i < mot.length; i++) { //remplace les cases avec les lettres du mot réponse grâce à l'indice
                            if (orientation[numChoix].equals("H")) {
                                grille[numLignes[numChoix]][numColonnes[numChoix] + i] = mot[i];
                            }
                            else if (orientation[numChoix].equals("V")) {
                                grille[numLignes[numChoix] + i][numColonnes[numChoix]] = mot[i];
                            }
                        }
                        System.out.println("Bonne réponse!");
                    } else {
                        System.out.println("Mauvaise réponse!");
                    }
                    break;
                default:
                    System.out.println("Veuillez entrer un choix valide.");
            }
        }


    }
}
