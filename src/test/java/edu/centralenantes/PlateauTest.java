package edu.centralenantes;

import edu.centralenantes.Position;
import edu.centralenantes.Joueur;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlateauTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PlateauTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PlateauTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testInitialisation()
    {
        Plateau monPlateau = new Plateau();
        monPlateau.initialiserPartie();
        Position milieu = new Position(e, 4);
        xmilieu = milieu.xToInt() - 1; //Le plateau va de 0 a 7 alors que les positions vont de a a h ou de 1 a 8 donc on retire 1.
        ymilieu = milieu.getY() - 1;
        assertTrue( monPlateau.positions[xmilieu][ymilieu] == 1 ); //On verrifie que e4 est bien initialise en noir.
    }

    public void testRechercheVoisinsOpp()
    {
        Plateau monPlateau = new Plateau();
        monPlateau.initialiserPartie();
        Position milieu = new Position(e, 4);
        int couleurJoue = 1;
        ArrayList<Position> mesVoisinsOpp = monPlateau.voisinsOpposes(milieu, couleurJoue);
        Position e5 = new Position(e, 5);
        Position d4 = new Position(d, 4);
        assertTrue( mesVoisinsOpp.size() == 2 ); //On verrifie que e4 a bien deux voisins opposes.
        assertTrue( ((mesVoisinsOpp[0].equals(d4))||(mesVoisinsOpp[0].equals(e5))) && ((mesVoisinsOpp[1].equals(d4))||(mesVoisinsOpp[1].equals(e5)))); //On verrifie que e4 a e5 et d4 en voisins opposes.
    }

    public void testCapture()
    {
        Plateau monPlateau = new Plateau();
        monPlateau.initialiserPartie();
        int couleurJoue = 1;
        Position e5 = new Position(e, 5);
        Position e6 = new Position(e, 6);
        assertTrue( monPlateau.capture(e6, e5, couleurJoue) );//On verifie que e6 pour les noirs capture bien e5.
    }

    public void testCoupAutorise()
    {
        Plateau monPlateau = new Plateau();
        monPlateau.initialiserPartie();
        int couleurJoue = 1;
        Joueur j1 = new Joueur(couleurJoue);
        ArrayList<Position> mesCoups = monPlateau.coupAutorise(j1);
        Position e6 = new Position(e, 6);
        assertTrue( mesCoups.size() == 4 );//On verifie qu'on a bien 4 coups autorise
        assertTrue( (mesCoups[0].equals(e6))||(mesCoups[1].equals(e6))||(mesCoups[2].equals(e6))||(mesCoups[3].equals(e6)) );
    }       
}