package edu.centralenantes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Joueur.
 */
public class JoueurTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JoueurTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( JoueurTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testDemandeCoup() {
        // Simuler les entrées utilisateur
        String simulatedInput = "c\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Joueur j = new Joueur(0);

        Position expected = new Position('c', 4);
        Position actual = j.demandeCoup();

        assertEquals(expected.getX(), actual.getX());
        assertEquals(expected.getY(), actual.getY());
    }

    public void testJouer(){
        Position pos = new Position('e', 4);
        Joueur j = new Joueur(0);
        Position[] posPossibles = new Position[1];
        posPossibles[0] = pos;

        String simulatedInput = "e\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Position expected = pos;
        Position actual = j.jouer(posPossibles);

        assertNotNull(actual);
        assertEquals(expected.getX(), actual.getX());
        assertEquals(expected.getY(), actual.getY());
    }
}