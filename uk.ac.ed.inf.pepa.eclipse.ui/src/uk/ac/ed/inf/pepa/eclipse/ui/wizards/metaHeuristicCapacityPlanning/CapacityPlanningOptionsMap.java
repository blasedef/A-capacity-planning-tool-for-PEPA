package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import java.util.HashMap;
import java.util.Map;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.configuration.MetaHeuristicConfiguration;


/**
*
* MetaheuristicParameters:
* Used to provide values to the Wizard pages, and to also store variables for the Metaheuristic Job. This parameter class hold what type of metaheuristic is going to be used.
* @author Christopher Williams
*
*/

public abstract class CapacityPlanningOptionsMap {
	
	//attribute types
	public static final Integer INTEGER = 0;
	public static final Integer DOUBLE = 1;
	
	//evaluator types
	public static final Integer THROUGHPUT = 0;
	public static final String THROUGHPUT_S = "Throughput";
	
	public static final Integer AVERAGERESPONSETIME = 1;
	public static final String AVERAGERESPONSETIME_S = "Average response time";
	
	public static Integer EVALUATORTYPE = 0;
	public static String EVALUATORTYPE_S = "Evaluator Type";
	
	//metaheuristic types
	public static final Integer HILLCLIMBING = 0;
	public static final String HILLCLIMBING_S = "Hill climbing";
	
	public static final Integer GENETICALGORITHM = 1;
	public static final String GENETICALGORITHM_S = "Genetic Algorithm";
	
	public static final Integer PARTICLESWARMOPTIMISATION = 2;
	public static final String PARTICLESWARMOPTIMISATION_S = "Particle Swarm Optimisation";
	
	public static final Integer METAHEURISTICTYPE1 = 0;
	public static String METAHEURISTICTYPE1_S = "Metaheuristic Type";
	
	public static final Integer METAHEURISTICTYPE2 = 0;
	public static final String METAHEURISTICTYPE2_S = "Driving/First Metaheuristic Type";
	
	//metaheuristic network
	public static final Integer METAHEURISTICSINGLE = 0;
	public static final String METAHEURISTICSINGLE_S = "Single";
	
	public static final Integer METAHEURISTICDRIVEN = 1;
	public static final String METAHEURISTICDRIVEN_S = "Driven";
	
	public static final Integer METAHEURISTICPIPELINE = 2;
	public static final String METAHEURISTICPIPELINE_S = "Pipe Lined";

	public static final Integer METAHEURISTICNETWORKTYPE = 0;
	public static String METAHEURISTICNETWORKTYPE_S = "Metaheuristic Network Type";
	
	//Additional Pages
	public static final Integer ADDITIONALCOSTSNO = 0;
	public static final String ADDITIONALCOSTSNO_S = "No";
	
	public static final Integer ADDITIONALCOSTSYES = 1;
	public static final String ADDITIONALCOSTSYES_S = "Yes";
	
	public static final Integer ADDITIONALCOSTS = 0;
	public static String ADDITIONALCOSTS_S = "Additional Costs";
	
	//Fitness parameters
	public static Integer GENERATION = 1000;
	public static String GENERATION_S = "Generation";
	
	public static Double ALPHABETA = 0.5;
	public static String ALPHABETA_S = "Alpha : Beta";
	
	public static Double MUTATIONPROBABILITY = 0.5; 
	public static String MUTATIONPROBABILITY_S = "Mutation Probability";
	
	public static Double CROSSOVERPROBABILITY = 0.2;
	public static String CROSSOVERPROBABILITY_S = "Crossover Probability";
	
	public static Integer INITIALCANDIDATEPOPULATION = 100;
	public static String INITIALCANDIDATEPOPULATION_S = "Initial Candidate Population";
	
	public static Integer TOBESET1 = 0;
	public static String TOBESET1_S = "tobeset1";
	
	public static Double TOBESET2 = 0.0;
	public static String TOBESET2_S = "tobeset2";
	
	//parameter lists, keep the names in order of the index below...
	public static final String[] evaluatorList = new String[] {THROUGHPUT_S,AVERAGERESPONSETIME_S};
	public static final String[] metaHeuristicTypeList = new String[] {HILLCLIMBING_S,GENETICALGORITHM_S,PARTICLESWARMOPTIMISATION_S};
	public static final String[] metaHeuristicNetworkList = new String[] {METAHEURISTICSINGLE_S, METAHEURISTICDRIVEN_S, METAHEURISTICPIPELINE_S};
	public static final String[] additionalCostsList = new String[] {ADDITIONALCOSTSNO_S, ADDITIONALCOSTSYES_S};
	
	
	//metaheuristic attribute lists
	public static final String[] hillClimbingFitnessFunctionAttributeIndex = new String[] {GENERATION_S,ALPHABETA_S,MUTATIONPROBABILITY_S};
	public static final String[] geneticAlgorithmFitnessFunctionAttributeIndex = new String[] {GENERATION_S,ALPHABETA_S,MUTATIONPROBABILITY_S,CROSSOVERPROBABILITY_S,INITIALCANDIDATEPOPULATION_S};
	public static final String[] particleSwarmOptimisationFitnessFunctionAttributeIndex = new String[] {GENERATION_S,TOBESET1_S,TOBESET2_S};
	
	//Metaheuristic fitness function attributes
	public static final Map<String, Integer> fitnessFunctionAttributeTypesHashMap = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID = 1L;
	{
		put(GENERATION_S,INTEGER);
		put(ALPHABETA_S,DOUBLE);
		put(MUTATIONPROBABILITY_S,DOUBLE);
		put(CROSSOVERPROBABILITY_S,DOUBLE);
		put(INITIALCANDIDATEPOPULATION_S,INTEGER);
		put(TOBESET1_S,INTEGER);
		put(TOBESET2_S,DOUBLE);
	}};
	
	//default attribute values
	public static final Map<Integer,Number> defaultAttributeValues = new HashMap<Integer, Number>()
	{
		private static final long serialVersionUID = 1L;
	{
		put(INTEGER,10);
		put(DOUBLE,0.0);
	}};
	
	//option mapping
	public static final Map<String,Number> optionMap = new HashMap<String, Number>()
	{
		private static final long serialVersionUID = 1L;
	{
		put(THROUGHPUT_S,THROUGHPUT);
		put(AVERAGERESPONSETIME_S,AVERAGERESPONSETIME);
		put(EVALUATORTYPE_S,EVALUATORTYPE);
		put(HILLCLIMBING_S,HILLCLIMBING);
		put(GENETICALGORITHM_S,GENETICALGORITHM);
		put(PARTICLESWARMOPTIMISATION_S,PARTICLESWARMOPTIMISATION);
		put(METAHEURISTICTYPE1_S,METAHEURISTICTYPE1);
		put(METAHEURISTICTYPE2_S,METAHEURISTICTYPE2);
		put(METAHEURISTICSINGLE_S,METAHEURISTICSINGLE);
		put(METAHEURISTICDRIVEN_S,METAHEURISTICDRIVEN);
		put(METAHEURISTICPIPELINE_S,METAHEURISTICPIPELINE);
		put(METAHEURISTICNETWORKTYPE_S,METAHEURISTICNETWORKTYPE);
		put(ADDITIONALCOSTSNO_S,ADDITIONALCOSTSNO);
		put(ADDITIONALCOSTSYES_S,ADDITIONALCOSTSYES);
		put(ADDITIONALCOSTS_S,ADDITIONALCOSTS);
		put(GENERATION_S,GENERATION);
		put(ALPHABETA_S,ALPHABETA);
		put(MUTATIONPROBABILITY_S,MUTATIONPROBABILITY);
		put(CROSSOVERPROBABILITY_S,CROSSOVERPROBABILITY);
		put(INITIALCANDIDATEPOPULATION_S,INITIALCANDIDATEPOPULATION);
		put(TOBESET1_S,TOBESET1);
		put(TOBESET2_S,TOBESET2);
	}};
	
	//option mapping for strings
	public static final Map<String,String> optionMap_S = new HashMap<String, String>()
	{
		private static final long serialVersionUID = 1L;
	{
		put(EVALUATORTYPE_S,THROUGHPUT_S);
		put(METAHEURISTICTYPE1_S,HILLCLIMBING_S);
		put(METAHEURISTICTYPE2_S,HILLCLIMBING_S);
		put(METAHEURISTICNETWORKTYPE_S,METAHEURISTICSINGLE_S);
		put(ADDITIONALCOSTS_S,ADDITIONALCOSTSNO_S);
	}};
	
	
	//option mapping for fitness options
	public static final Map<String,String[]> optionMap_fitnessOptions = new HashMap<String, String[]>()
	{
		private static final long serialVersionUID = 1L;
	{
		put(HILLCLIMBING_S,hillClimbingFitnessFunctionAttributeIndex);
		put(GENETICALGORITHM_S,geneticAlgorithmFitnessFunctionAttributeIndex);
		put(PARTICLESWARMOPTIMISATION_S,particleSwarmOptimisationFitnessFunctionAttributeIndex);
		
	}};
	
	
	
	//metaheuristicConfigurations
	public static final MetaHeuristicConfiguration metaHeuristicConfiguration1 = new MetaHeuristicConfiguration(hillClimbingFitnessFunctionAttributeIndex);
	public static final MetaHeuristicConfiguration metaHeuristicConfiguration2 = new MetaHeuristicConfiguration(hillClimbingFitnessFunctionAttributeIndex);
	
	public CapacityPlanningOptionsMap(){
	}
	
	
	/**
	 * 
	 * @param inputValue, the user entered value
	 * @param type, the type to check against
	 * @return true if the value is of that type
	 */
	public static boolean testType(String s, Integer type){
		if(type == INTEGER){
			try {
				if(Integer.parseInt(s) != (int)Integer.parseInt(s)){
					return false;
				}
				
			} catch (NumberFormatException e) {
				return false;
			}
			
			return true;
		}
		else if(type == DOUBLE){
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
	public static boolean testXLessThanOrEqualToY(String s, String t, Number type){
		if(type == INTEGER){

			Integer i = Integer.parseInt(s);
			Integer j = Integer.parseInt(t);
				
			return i <= j;
				
		}
		else if(type == DOUBLE){
			Double i = Double.parseDouble(s);
			Double j = Double.parseDouble(t);
				
			return i <= j;
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
	public static boolean testXLessThanY(String s, String t, Number type){
		if(type == INTEGER){

			Integer i = Integer.parseInt(s);
			Integer j = Integer.parseInt(t);
				
			return i < j;
				
		}
		else if(type == DOUBLE){
			Double i = Double.parseDouble(s);
			Double j = Double.parseDouble(t);
				
			return i < j;
		}
		else {
			return false;
		}		
	}
	
	public static void setAttribute(Integer index, String[] values, String attribute){
		attribute = values[index];
	}
	
	
	
	
}