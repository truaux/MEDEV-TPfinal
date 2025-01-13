package edu.centralenantes;

public class Position {
    private char x;
    private int y;
    private String alphabet = "abcdefgh";

    public Position(){
        x = 'd';
        y = 4;
    }

    public Position(char col, int lig){
        x = col;
        y = lig;
    }

    public Position(int col, int lig){
        x = alphabet.charAt(col);
        y = lig;
    }

    public char getX() {
        return x;
    }

    public void setX(char x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }
}
