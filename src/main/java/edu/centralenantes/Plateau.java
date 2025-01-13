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
        for (int i = -1; i<2; i++){
            for (int j = -1; j<2; j++){
                if (dansPlateau(posxInt+i, posyInt+j)){
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
        if (xJoue == xVoisin){
            int rapport = yVoisin - yJoue;
            int yExplore = yVoisin + rapport;
            while ((dansPlateau(xJoue, yExplore)) && (this.positions[xJoue][yExplore] == couleurOppose)){
                yExplore += rapport;
            }
            if ((dansPlateau(xJoue, yExplore)) && (this.positions[xJoue][yExplore] == couleurJoue)){
                voisinCapture = true;
            }
        } elif (yJoue == yVoisin){
            int rapport = xVoisin - xJoue;
            int xExplore = xVoisin + rapport;
            while ((dansPlateau(xExplore, yJoue)) && (this.positions[xExplore][yJoue] == couleurOppose)){
                xExplore += rapport;
            }
            if ((dansPlateau(xExplore, yJoue)) && (this.positions[xExplore][yJoue] == couleurJoue)){
                voisinCapture = true;
            }
        } else {
            int xrapport = xVoisin - xJoue;
            int xExplore = xVoisin + xrapport;
            int yrapport = yVoisin - yJoue;
            int yExplore = yVoisin + yrapport;
            while ((dansPlateau(xExplore, yExplore)) && (this.positions[xExplore][yExplore] == couleurOppose)){
                xExplore += xrapport;
                yExplore += yrapport;
            }
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
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (this.positions[i][j] == -1){
                    Position maPos = new Position(i+1, j+1);
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
                        coupJouable.add(maPos);
                    }
                }
            }
        }
        return coupJouable;
    }
}