package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
* 
* Defaults, options and configuration
* @author Christopher Williams
*
*/

public abstract class ExperimentConfiguration {

	/*
	 * Default strings
	 */
	
	//Input Types
	public static final String INTEGER = "INTEGER";
	public static final String PERCENT = "PERCENT";
	public static final String DOUBLE = "DOUBLE";
	public static final String EVEN = "EVEN";
	
	//evaluator types
	public static final String THROUGHPUT_S = "Throughput";
	public static final String AVERAGERESPONSETIME_S = "Average response time";
	public static final String EVALUATORTYPE_S = "Evaluator Type";
	
	//metaheuristic types
	public static final String HILLCLIMBING_S = "Hill climbing";
	public static final String GENETICALGORITHM_S = "Genetic Algorithm";
	public static final String PARTICLESWARMOPTIMISATION_S = "Particle Swarm Optimisation";
	public static final String METAHEURISTICTYPE_S_S = "Metaheuristic Type";
	public static final String METAHEURISTICTYPE_D_S = "Driving Metaheuristic Type";
	public static final String METAHEURISTICTYPE_P_S = "Pipeline Metaheuristic Type";
	
	//MetaHeuristic Configuration Parameters
	public static String EXPERIMENTS_S = "Experiments";
	public static String GENERATION_S = "Generation";
	public static String ALPHABETA_S = "Performance vs Population";
	public static String DELTASIGMA_S = "Performance vs Time";
	public static String MUTATIONPROBABILITY_S = "Mutation Probability";
	public static String CROSSOVERPROBABILITY_S = "Crossover Probability";
	public static String INITIALCANDIDATEPOPULATION_S = "Initial Candidate Population";
	public static String PERSONALBEST = "Personal best";
	public static String GLOBALBEST = "Global best";
	public static String ORIGINALVELO = "Original velocity";
	
	//Additional Pages
	public static final String ADDITIONALCOSTS_S = "Additional Costs";
	public static final String ADDITIONALCOSTSNO_S = "No";
	public static final String ADDITIONALCOSTSYES_S = "Yes";
	
	//metaheuristic network
	public static final String NETWORKSINGLE_S = "Single";
	public static final String NETWORKDRIVEN_S = "Driven";
	public static final String NETWORKPIPELINE_S = "Pipe lined";
	public static final String NETWORKTYPE_S = "Metaheuristic Network Type";
	
	public static final String EVALUATORDESCRIPTION = " placeholder"; 
	public static final String METAHEURISTICDESCRIPTION = " placeholder";
	public static final String NETWORKDESCRIPTION = " placeholder";
	public static final String ADDITIONALCOSTDESCRIPTION = " placeholder";
	
	/*
	 * Value types
	 */
	public static Map<String,String> defaultOptionTypeMap = new HashMap<String, String>()
	{/**
		 * 
		 */
		private static final long serialVersionUID = -1942691622505736873L;

	{
		put(ExperimentConfiguration.EXPERIMENTS_S,ExperimentConfiguration.INTEGER);
		put(ExperimentConfiguration.GENERATION_S,ExperimentConfiguration.INTEGER);
		put(ExperimentConfiguration.ALPHABETA_S,ExperimentConfiguration.PERCENT);
		put(ExperimentConfiguration.MUTATIONPROBABILITY_S,ExperimentConfiguration.PERCENT);
		put(ExperimentConfiguration.CROSSOVERPROBABILITY_S,ExperimentConfiguration.PERCENT);
		put(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S,ExperimentConfiguration.EVEN);
		put(ExperimentConfiguration.PERSONALBEST,ExperimentConfiguration.DOUBLE);
		put(ExperimentConfiguration.GLOBALBEST,ExperimentConfiguration.DOUBLE);
		put(ExperimentConfiguration.DELTASIGMA_S,ExperimentConfiguration.PERCENT);
		put(ExperimentConfiguration.ORIGINALVELO,ExperimentConfiguration.DOUBLE);
	}};
	
	/*
	 * Options 
	 */
	private static final String[] evaluatorOptions = new String[] {ExperimentConfiguration.THROUGHPUT_S, ExperimentConfiguration.AVERAGERESPONSETIME_S};
	private static final String[] metaHeuristicOptions = new String[] {ExperimentConfiguration.HILLCLIMBING_S, ExperimentConfiguration.GENETICALGORITHM_S, ExperimentConfiguration.PARTICLESWARMOPTIMISATION_S};
	private static final String[] networkOptions = new String[] {ExperimentConfiguration.NETWORKSINGLE_S, ExperimentConfiguration.NETWORKDRIVEN_S, ExperimentConfiguration.NETWORKPIPELINE_S};
	private static final String[] additionalCostsOptions = new String[] {ExperimentConfiguration.ADDITIONALCOSTSNO_S, ExperimentConfiguration.ADDITIONALCOSTSYES_S};
	
	//What kind of performance evaluation are we doing?
	public static EvaluatorType evaluator = new EvaluatorType("Evaluator type", ExperimentConfiguration.THROUGHPUT_S, evaluatorOptions);
	
	//Single, or First (in the pipeline), or Driving metaheuristic
	public static MetaHeuristicType metaHeuristic = new MetaHeuristicType("Search for System equation using", ExperimentConfiguration.HILLCLIMBING_S, metaHeuristicOptions, "Primary");
	
	//what kind of network we have
	public static MetaHeuristicNetworkType networkType = new MetaHeuristicNetworkType("Metaheuristic Network type",ExperimentConfiguration.NETWORKSINGLE_S,networkOptions);
	
	//whether we want to deal with additional costs
	public static AdditionalCosts additionalCosts = new AdditionalCosts("Additional Costs",ExperimentConfiguration.ADDITIONALCOSTSNO_S,additionalCostsOptions);
	
	//ODE configuration values are saved here
	public static ODEConfig oDEConfig = new ODEConfig("ODE Config",null, null);
	
	//PEPA related values are saved here
	public static PEPAConfig pEPAConfig = new PEPAConfig("PEPA Config",null,null);
	
	public static ArrayList<Configuration> performanceEvaluatorAndMetaheuristic = new ArrayList<Configuration>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4028323174259971730L;

		{
			add((Configuration) ExperimentConfiguration.evaluator);
			add(ExperimentConfiguration.metaHeuristic);
			add(ExperimentConfiguration.networkType);
			add(ExperimentConfiguration.additionalCosts);
		}
	};

	public static void resetToDefaults() {
		evaluator = new EvaluatorType("Evaluator type", ExperimentConfiguration.THROUGHPUT_S, evaluatorOptions);
		metaHeuristic = new MetaHeuristicType("Search for System equation using", ExperimentConfiguration.HILLCLIMBING_S, metaHeuristicOptions, "Primary");
		networkType = new MetaHeuristicNetworkType("Metaheuristic Network type",ExperimentConfiguration.NETWORKSINGLE_S,networkOptions);
		additionalCosts = new AdditionalCosts("Additional Costs",ExperimentConfiguration.ADDITIONALCOSTSNO_S,additionalCostsOptions);
		oDEConfig = new ODEConfig("ODE Config",null, null);
		pEPAConfig = new PEPAConfig("PEPA Config",null,null);
		
		ExperimentConfiguration.performanceEvaluatorAndMetaheuristic = new ArrayList<Configuration>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4028323174259971730L;

			{
				add((Configuration) ExperimentConfiguration.evaluator);
				add(ExperimentConfiguration.metaHeuristic);
				add(ExperimentConfiguration.networkType);
				add(ExperimentConfiguration.additionalCosts);
			}
		};

	}
	

}