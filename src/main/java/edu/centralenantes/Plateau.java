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
}