/**
 * 
 */
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicTypeParameters;

/**
 * @author twig
 *
 */
public class MetaHeuristicTypeParametersTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicTypeParameters#MetaHeuristicTypeParameters()}.
	 */
	@Test
	public final void testMetaHeuristicTypeParameters() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicTypeParameters#getAttributes(int[])}.
	 */
	@Test
	public final void testGetAttributes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicTypeParameters#testType(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public final void testTestType() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicTypeParameters#testXLessThanY(java.lang.String, java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public final void testTestXLessThanY() {
		boolean test = true;
		boolean result = MetaHeuristicTypeParameters.testXLessThanY("1", "2", MetaHeuristicTypeParameters.WHOLE);
		Assert.assertTrue(test == result);
	}

}
