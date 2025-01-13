package edu.centralenantes;

/**
 *
 * @author titou
 */
public class Position {
    private char x;
    private int y;
    private String alphabet = "abcdefgh";

    /**
     *
     */
    public Position(){
        x = 'z';
        y = 0;
    }

    /**
     *
     * @param col
     * @param lig
     */
    public Position(char col, int lig){
        x = col;
        y = lig;
    }

    /**
     *
     * @param col
     * @param lig
     */
    public Position(int col, int lig){
        x = alphabet.charAt(col-1);
        y = lig;
    }

    /**
     *
     * @return
     */
    public char getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(char x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return
     */
    public String getAlphabet() {
        return alphabet;
    }

    /**
     *
     * @param alphabet
     */
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    /**
     *
     * @return
     */
    public int xToInt(){
        int resultat = alphabet.indexOf(this.x) + 1;
        return resultat;
    }
}
