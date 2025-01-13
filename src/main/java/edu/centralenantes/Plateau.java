package edu.centralenantes;

import edu.centralenantes.Position;

import java.util.ArrayList;

import edu.centralenantes.Joueur;

public class Plateau {
    private int[][] positions;

    public Plateau(){
        this.positions = new int[8][8];
    }

    public Plateau(int[][] pos){
        this.positions = pos;
    }

    public void initialiserPartie(){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                this.positions[i][j] = -1;
            }
        }
        //Toutes les cases sont maintenant considerees comme vide (-1 -> vide)
        //On place maintenant les quatre jetons du debut (0 -> blanc, 1 -> noir)
        this.positions[3][3] = 0;
        this.positions[3][4] = 1;
        this.positions[4][3] = 1;
        this.positions[4][4] = 0;
    }

    public boolean dansPlateau(int x, int y){
        return ((x >= 0) && (x < 8) && (y >= 0) && (y < 0));
    }

    public ArrayList<Position> voisinsOpposes(Position pos, int couleurJoue){
        ArrayList<Position> mesVoisinsOpp = new ArrayList<Position>();
        int couleurOppose = 1 - couleurJoue;
        int posxInt = pos.xToInt() - 1;
        int posyInt = pos.getY() - 1;
        //On explore tous les voisins de notre positions
        for (int i = -1; i<2; i++){
            for (int j = -1; j<2; j++){
                //On verifie que la position regardee est dans le plateau
                if (dansPlateau(posxInt+i, posyInt+j)){
                    //Puis on regarde sa couleur et si cette derniere est la bonne, on ajoute la position a notre liste
                    if (this.positions[posxInt+i][posyInt+j] == couleurOppose){
                        Position voisinOpp = new Position(posxInt+i+1, posyInt+j+1);
                        mesVoisinsOpp.add(voisinOpp);
                    }
                }
            }
        }
        return mesVoisinsOpp;
    }

    public boolean capture(Position joue, Position voisin, int couleurJoue){
        int couleurOppose = 1 - couleurJoue;
        int xJoue = joue.xToInt() - 1;
        int yJoue = joue.getY() - 1;
        int xVoisin = voisin.xToInt() - 1;
        int yVoisin = voisin.getY() - 1;
        boolean voisinCapture = false;
        //Premiere possibilite : meme ligne
        if (xJoue == xVoisin){
            //le rapport nous donne la direction a explorer
            int rapport = yVoisin - yJoue;
            int yExplore = yVoisin + rapport;
            //on arrete l'exploration lorsqu'on atteint autre chose qu'un pion de la meme couleur que celui potentiellement capture.
            while ((dansPlateau(xJoue, yExplore)) && (this.positions[xJoue][yExplore] == couleurOppose)){
                yExplore += rapport;
            }
            //si ce qui nous a stoppe est un pion de l'autre couleur, c'est une capture
            if ((dansPlateau(xJoue, yExplore)) && (this.positions[xJoue][yExplore] == couleurJoue)){
                voisinCapture = true;
            }
        //Deuxieme possibilite : meme colonne
        } elif (yJoue == yVoisin){
            //le rapport nous donne la direction a explorer
            int rapport = xVoisin - xJoue;
            int xExplore = xVoisin + rapport;
            //on arrete l'exploration lorsqu'on atteint autre chose qu'un pion de la meme couleur que celui potentiellement capture.
            while ((dansPlateau(xExplore, yJoue)) && (this.positions[xExplore][yJoue] == couleurOppose)){
                xExplore += rapport;
            }
            //si ce qui nous a stoppe est un pion de l'autre couleur, c'est une capture
            if ((dansPlateau(xExplore, yJoue)) && (this.positions[xExplore][yJoue] == couleurJoue)){
                voisinCapture = true;
            }
        //Troisieme possibilite : meme diagonale
        } else {
            //les rapports nous donnent la direction a explorer
            int xrapport = xVoisin - xJoue;
            int xExplore = xVoisin + xrapport;
            int yrapport = yVoisin - yJoue;
            int yExplore = yVoisin + yrapport;
            //on arrete l'exploration lorsqu'on atteint autre chose qu'un pion de la meme couleur que celui potentiellement capture.
            while ((dansPlateau(xExplore, yExplore)) && (this.positions[xExplore][yExplore] == couleurOppose)){
                xExplore += xrapport;
                yExplore += yrapport;
            }
            //si ce qui nous a stoppe est un pion de l'autre couleur, c'est une capture
            if ((dansPlateau(xExplore, yExplore)) && (this.positions[xExplore][yExplore] == couleurJoue)){
                voisinCapture = true;
            }
        }
        return voisinCapture;
    }

    public ArrayList<Position> coupAutorise(Joueur j1){
        ArrayList<Position> coupJouable = new ArrayList<Position>();
        int couleurJoue = j1.getCouleur();
        int couleurOppose = 1 - couleurJoue;
        //On explore tout le plateau
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                //Lorsqu'on trouve une case vide on observe ses voisins
                if (this.positions[i][j] == -1){
                    Position maPos = new Position(i+1, j+1);
                    //Puis on regarde plus precisemment les voisins de la couleur oppose au joueur.
                    ArrayList<Position> mesVoisinsOpp = voisinsOpposes(maPos, couleurJoue);
                    int verif = 0;
                    int indic = 0;
                    while ((verif == 0) && (indic<mesVoisinsOpp.size())){
                        if (capture(maPos, mesVoisinsOpp[indic])){
                            verif = 1;
                        }
                        indic++;
                    }
                    if (verif == 1){
                        //si le coup entraine une capture, on l'ajoute a la liste des coups jouables
                        coupJouable.add(maPos);
                    }
                }
            }
        }
        return coupJouable;
    }

    public int couleurGagnante(){
        int nb0 = 0;
        int nb1 = 0;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (positions[i][j] == 0){
                    nb0 += 1;
                } else if (positions[i][j] == 1){
                    nb1 += 1;
                }
            }
        }

        if (nb0 < nb1){
            return 1;
        } else if (nb0 > nb1){
            return 0;
        } else {
            // Cas d'egalite
            return -1;
        }
    }

    public boolean finDePartie(Joueur j1, Joueur j2){
        //Lorsqu'aucun joueur ne peut jouer, la partie prend fin
        return ((coupAutorise(j1).size() == 0) && (coupAutorise(j2).size() == 0));
    }

    public jeu(Joueur j1, Joueur j2){
        this.initialiserPartie();
    }
}