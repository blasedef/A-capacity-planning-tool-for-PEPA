package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

/*
 * Could not think of a better name for this class!
 */
public abstract class Config {
	
	/*
	 * Default strings
	 */
	
	//Input Types
	public static final String NATURAL = "NATURAL";
	public static final String INTEGER = "INTEGER";
	public static final String PERCENT = "PERCENT";
	public static final String DOUBLE = "DOUBLE";
	public static final String EVEN = "EVEN";
	
	//evaluator types
	public static final String EVALUATORTHROUGHPUT_S = "Throughput";
	public static final String EVALUATORAVERAGERESPONSETIME_S = "Average response time";
	public static final String EVALUATORTYPE_S = "Evaluator Type";
	
	//metaheuristic types
	public static final String METAHEURISTICTYPEHILLCLIMBING_S = "Hill climbing";
	public static final String METAHEURISTICTYPEGENETICALGORITHM_S = "Genetic Algorithm";
	public static final String METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S = "Particle Swarm Optimisation";
	public static final String METAHEURISTICTYPE_S = "Metaheuristic Type";
	
	//MetaHeuristic Configuration Parameters
	public static String EXPERIMENTS_S = "Experiments";
	public static String GENERATION_S = "Generation";
	public static String GENERATIONHC_S = "HillClimbing generation";
	public static String FITNESS_ALPHA_PERFORMANCE_S = "Performance";
	public static String FITNESS_BETA_POPULATION_S = "Population";
	public static String FITNESS_DELTA_PERFORMANCE_S = "Performance";
	public static String FITNESS_EPSILON_TIME_S = "Time";
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
	public static final String CHAINSINGLE_S = "Single";
	public static final String CHAINDRIVEN_S = "Driven";
	public static final String CHAINPIPELINE_S = "Pipe lined";
	public static final String CHAINTYPE_S = "Metaheuristic Chain Type";
	
	public static final String EVALUATORDESCRIPTION = " placeholder"; 
	public static final String METAHEURISTICDESCRIPTION = " placeholder";
	public static final String CHAINDESCRIPTION = " placeholder";
	public static final String ADDITIONALCOSTDESCRIPTION = " placeholder";
	
	//output to view
	public static final String VTOPFITNESS = "Top fitness:";
	public static final String VOVERALLFITTEST = "Overall fittest system equation candidate:";
	public static final String VOVERALLFITTESTL = "Overall fittest lab candidate:";
	public static final String VLABMEANFITNESS = "Lab mean fitness:";
	public static final String VSTANDARDDEV = "Standard deviation:";
	public static final String VLABMEANRESPONSETIME = "Mean lab response time:";
	public static final String VLABTOP10 = "0";
	public static final String VLABTOP9 = "1";
	public static final String VLABTOP8 = "2";
	public static final String VLABTOP7 = "3";
	public static final String VLABTOP6 = "4";
	public static final String VLABTOP5 = "5";
	public static final String VLABTOP4 = "6";
	public static final String VLABTOP3 = "7";
	public static final String VLABTOP2 = "8";
	public static final String VLABTOP1 = "9";
	public static final String VLABBESTPERFORMANCE = "Best performance:";
	
}
