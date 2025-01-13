package edu.centralenantes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class PositionTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PositionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PositionTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testXToInt() {
        Position pos = new Position('a', 1);
        int posInt = pos.xToInt();
        assertEquals(1, posInt);
        assertEquals(1, pos.getY());

        pos = new Position('h', 8);
        posInt = pos.xToInt();
        assertEquals(8, posInt);
        assertEquals(8, pos.getY());
    }

    /**
     *
     */
    public void testConstructor() {
        Position pos = new Position(1, 1);
        assertEquals('a', pos.getX());

        pos = new Position(8, 1);
        assertEquals('h', pos.getX());
    }
}
