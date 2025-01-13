package edu.centralenantes;

import java.util.Scanner;

public class Joueur {
    private int couleur;

    public Joueur(int c){
        couleur = c;
    }

    public String jouer(String[] coupPossible){

    }

    public String[] demandeCoup(){
        String[] resultat = new String[2];
        Scanner sc = new Scanner(System.in);
        System.out.println("Indiquez la lettre de la colonne où vous souhaitez jouer : ");
        String col = sc.nextLine();
        System.out.println("Indiquez le numero de la ligne où vous souhaitez jouer : ");
        String lig = sc.nextLine();
        sc.close();
        resultat[0] = col;
        resultat[1] = lig;
        return resultat;
    }
}
