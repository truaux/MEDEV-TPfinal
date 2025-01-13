package edu.centralenantes;

import java.util.ArrayList;

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

    public void affichage(){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                System.out.print(this.positions[i][j] + "\t");
            }
            System.out.println();
        }
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

        public boolean coupCorrect(Position positionAJouer, ArrayList<Position> coupJouable){
        boolean isIn = false;
        for (int i = 0; i<coupJouable.size(); i++){
            if (positionAJouer.equals(coupJouable[i])){
                isIn = true;
            }
        }
        return isIn;
    }
    
    public void capturePions(Position positionAJouer, Position pionCapture, int couleurJoue){
        int couleurOppose = 1 - couleurJoue;
        int xJoue = positionAJouer.xToInt() - 1;
        int yJoue = positionAJouer.getY() - 1;
        int xCapture = pionCapture.xToInt() - 1;
        int yCapture = pionCapture.getY() - 1;
        //Premiere possibilite : meme ligne
        if (xJoue == xCapture){
            //le rapport nous donne la direction a explorer
            int rapport = yCapture - yJoue;
            int yExplore = yCapture + rapport;
            //on arrete l'exploration lorsqu'on atteint autre chose qu'un pion de la meme couleur que celui potentiellement capture.
            while ((dansPlateau(xJoue, yExplore)) && (this.positions[xJoue][yExplore] == couleurOppose)){
                yExplore += rapport;
                this.positions[xJoue][yExplore] = couleurJoue;
            }
        //Deuxieme possibilite : meme colonne
        } elif (yJoue == yCapture){
            //le rapport nous donne la direction a explorer
            int rapport = xCapture - xJoue;
            int xExplore = xCapture + rapport;
            //on arrete l'exploration lorsqu'on atteint autre chose qu'un pion de la meme couleur que celui potentiellement capture.
            while ((dansPlateau(xExplore, yJoue)) && (this.positions[xExplore][yJoue] == couleurOppose)){
                xExplore += rapport;
                this.positions[xExplore][yJoue] = couleurJoue;
            }
        //Troisieme possibilite : meme diagonale
        } else {
            //les rapports nous donnent la direction a explorer
            int xrapport = xCapture - xJoue;
            int xExplore = xCapture + xrapport;
            int yrapport = yCapture - yJoue;
            int yExplore = yCapture + yrapport;
            //on arrete l'exploration lorsqu'on atteint autre chose qu'un pion de la meme couleur que celui potentiellement capture.
            while ((dansPlateau(xExplore, yExplore)) && (this.positions[xExplore][yExplore] == couleurOppose)){
                xExplore += xrapport;
                yExplore += yrapport;
                this.positions[xExplore][yExplore] = couleurJoue;
            }
        }
    }


    public void jeu(){
        //On initialise les joueurs
        Joueur j1 = new Joueur(1);
        Joueur j2 = new Joueur(0);
        //On initialise le plateau
        this.initialiserPartie();
        Joueur jActif = j1;
        //Tant qu'un des joueurs peut jouer, on continue
        while (!finDePartie(j1, j2)){
            //Pour chaque tour de jeu, on recherche les coup possible
            ArrayList<Position> coupJouable = this.coupAutorise(jActif);
            if (coupJouable.size() > 0){
                int couleurJoue = jActif.getCouleur();
                int numeroJoueur = 2 - couleurJoue;
                System.out.println("Au tour du joueur "+numeroJoueur);
                //On affiche le monde
                this.affichage();
                //On demande au joueur de choisir son coup
                Position positionAJouer = jActif.demandeCoup();
                while (!this.coupCorrect(positionAJouer, coupJouable)){
                    //si ce coup n'est pas possible on lui redemande de choisir
                    positionAJouer = jActif.demandeCoup();
                }
                ArrayList<Position> capturesPossibles = this.voisinsOpposes(positionAJouer, couleurJoue);
                for (int i = 0; i<capturesPossibles.size(); i++){
                    if (capture(positionAJouer, capturesPossibles[i], couleurJoue)){
                        this.capturePions(positionAJouer, capturesPossibles[i], couleurJoue);
                    }
                }
                int xJoue = positionAJouer.xToInt() - 1;
                int yJoue = positionAJouer.getY() - 1;
                this.positions[xJoue][yJoue] = couleurJoue;
            }
            //quand le tour est fini, on change de joueur
            if (jActif.equals(j1)){
                jActif = j2;
            } else {
                jActif = j1;
            }
        }
        int gagnant = this.couleurGagnante();
        if (gagnant == -1){
            System.out.println("Fin de Partie : egalite !");
        } else {
            numeroJoueurGagnant = 2 - gagnant;
            System.out.println("Fin de Partie : le joueur "+numeroJoueurGagnant+" a gagne !");
        }
    }
}