package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.preferences.ImmutableMap;

/**
*
* MetaheuristicParameters:
* Used to provide values to the Wizard pages, and to also store variables for the Metaheuristic Job. This parameter class hold what type of metaheuristic is going to be used.
* @author Christopher Williams
*
*/

public class MetaHeuristicTypeParameters {
	
	//fitness function attributes
	public static final Integer WHOLE = 0;
	public static final Integer REAL = 1;
	
	//default fitness function attribute values
	public static final Map<Integer,Number> defaultFitnessFunctionAttributeValues = new HashMap<Integer, Number>()
	{
		private static final long serialVersionUID = 1L;
	{
		put(WHOLE,10);
		put(REAL,0.0);
	}};
	
	//Metaheuristic fitness function attributes
	public static Map<String, Integer> fitnessFunctionAttributeTypesHashMap = new HashMap<String, Integer>()
	{
		//private static final long serialVersionUID = 1L;
	{
		put("Generation",MetaHeuristicTypeParameters.WHOLE);
		put("Alpha : Beta",MetaHeuristicTypeParameters.REAL);
		put("Mutation Probability",MetaHeuristicTypeParameters.REAL);
		put("Cross Over Probability",MetaHeuristicTypeParameters.REAL);
		put("Initial Candidate Population Size",MetaHeuristicTypeParameters.WHOLE);
		put("to be set - whole",MetaHeuristicTypeParameters.WHOLE);
		put("to be set - real",MetaHeuristicTypeParameters.REAL);
	}};

	
	//metaheuristic attribute indices, these are the selections from the above which define the fitness function attributes
	public static final int[] hillClimbingFitnessFunctionAttributeIndex = new int[] {0,1,2};
	
	public static final int[] geneticAlgorithmFitnessFunctionAttributeIndex = new int[] {0,1,2,3,4};
	
	public static final int[] particleSwarmOptimisationFitnessFunctionAttributeIndex = new int[] {1,5,6};
	
	//Array for Metaheuristic type
	public static final String[] metaHeuristicTypes = new String[] {"Hill Climbing","Genetic Algorithm","Particle Swarm"};
	
	//Initialise MetaHeuristicParameters (the individual metaheuristic configuration essentially...)
	public static MetaHeuristicParameters metaheuristic1 = new MetaHeuristicParameters(0, getAttributes(hillClimbingFitnessFunctionAttributeIndex));
	
	public static MetaHeuristicParameters metaheuristic2 = new MetaHeuristicParameters(1, getAttributes(hillClimbingFitnessFunctionAttributeIndex));
	
	//Array for network of metaheuristics
	public static final String[] metaHeuristicNetworkTypes = new String[] {"Single","Driven","Pipeline"};
	
	public static int METAHEURISTICNETWORK = 0; 
	
	public MetaHeuristicTypeParameters(){
	}
	
	/**
	 * 
	 * This function receives an attributeIndex (hillClimbingFitnessFunctionAttributeIndex for example), and returns the attributes that
	 * that metaheuristic requires
	 * 
	 * @param inputAttributeIndex, a int[] of indexes
	 * @return String[] of attributes
	 */
	public static String[] getAttributes(int[] inputAttributeIndex){
		String[] result = new String[inputAttributeIndex.length];
		
		for(int i = 0; i < inputAttributeIndex.length; i++){
			result[i] = (String) fitnessFunctionAttributeTypesHashMap.keySet().toArray()[inputAttributeIndex[i]];
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param inputValue, the user entered value
	 * @param type, the type to check against
	 * @return true if the value is of that type
	 */
	public static boolean testType(String s, Integer type){
		if(type == WHOLE){
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			}
			
			return true;
		}
		else if(type == REAL){
			try {
				Float.parseFloat(s);
			} catch (NumberFormatException e) {
				return false;
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param s 
	 * @param t
	 * @param type, what type of attribute is it? - assumes that testType has already happened
	 * @return boolean, true if s is less than t
	 */
	public static boolean testXLessThanY(String s, String t, Integer type){
		if(type == WHOLE){

			Integer i = Integer.parseInt(s);
			Integer j = Integer.parseInt(t);
				
			return i < j;
				
		}
		else if(type == REAL){
			Float i = Float.parseFloat(s);
			Float j = Float.parseFloat(t);
				
			return i < j;
		}
		else {
			return false;
		}		
	}
	
	
}