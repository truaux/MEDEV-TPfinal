package edu.centralenantes;

import java.nio.file.attribute.PosixFileAttributeView;
import java.util.Scanner;

import Position;

public class Joueur {
    private int couleur;

    public Joueur(int c){
        couleur = c;
    }

    public Position jouer(Position[] coupsPossibles){
        Position coup = demandeCoup();

        Boolean existe = false;
        for (int i=0; i < coupsPossibles.length; i++){
            if (coupsPossibles[i].equals(coup)){
                existe = true;    
            }
        }
        if (!existe){
            System.out.println("Votre coup n'est pas valide !");
            coup = jouer();
        }
        return coup;
    }

    public Position demandeCoup(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Indiquez la lettre de la colonne où vous souhaitez jouer : ");
        String saisieCol = sc.nextLine();
        System.out.println("Indiquez le numero de la ligne où vous souhaitez jouer : ");
        String saisieLig = sc.nextLine();
        sc.close();

        Position resultat = new Position();
        if (saisieCol.length() == 1 && saisieLig.length() == 1){
            char col = saisieCol.charAt(0);
            int lig;
            try{
                lig = Integer.valueOf(saisieLig);
            } catch (NumberFormatException error) {
                System.out.println("Vous avez saisie " + saisieLig + " pour la ligne : cette entree n'est pas un entier valide !");
                lig = -1;
            } finally {
                if ("abcdefgh".indexOf(col) < 0){
                    System.out.println("Vous avez saisie " + saisieCol + " pour la colonne : cette entree n'est pas une lettre valide !");
                    demandeCoup();
                } else if (lig < 1 || lig > 8){
                    System.out.println("Vous avez saisie " + saisieLig + " pour la ligne : cette entree n'est pas un entier valide !");
                    demandeCoup();
                } else {
                    resultat = new Position(col, lig);
                }
            }
        } else {
            System.out.println("Votre entree n'est pas valide !");
            resultat = demandeCoup();
        }
        return resultat;
    }
}
