package com.anz.fx;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class FXCalculatorAppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FXCalculatorAppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FXCalculatorAppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testFXCalculatorApp()
    {
        assertTrue( true );
    }
}
