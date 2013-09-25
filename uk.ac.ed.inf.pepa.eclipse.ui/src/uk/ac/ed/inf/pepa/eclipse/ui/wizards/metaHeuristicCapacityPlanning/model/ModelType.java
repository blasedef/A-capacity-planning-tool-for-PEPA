package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public abstract class ModelType {
	
	/**
	 * Default strings
	 * 
	 */
	
	public static final String INTEGER = "INTEGER";
	public static final String PERCENT = "PERCENT";
	public static final String DOUBLE = "DOUBLE";
	
	//evaluator types
	public static final String THROUGHPUT_S = "Throughput";
	public static final String AVERAGERESPONSETIME_S = "Average response time";
	public static final String EVALUATORTYPE_S = "Evaluator Type";
	
	//metaheuristic types
	public static final String HILLCLIMBING_S = "Hill climbing";
	public static final String GENETICALGORITHM_S = "Genetic Algorithm";
	public static final String PARTICLESWARMOPTIMISATION_S = "Particle Swarm Optimisation";
	public static final String METAHEURISTICTYPE1_S = "Metaheuristic Type";
	public static final String METAHEURISTICTYPE2_S = "Driving/First Metaheuristic Type";
	
	//metaheuristic network
	public static final String METAHEURISTICSINGLE_S = "Single";
	public static final String METAHEURISTICDRIVEN_S = "Driven";
	public static final String METAHEURISTICPIPELINE_S = "Pipe Lined";
	public static final String METAHEURISTICNETWORKTYPE_S = "Metaheuristic Network Type";
	
	//Additional Pages
	public static final String ADDITIONALCOSTSNO_S = "No";
	public static final String ADDITIONALCOSTSYES_S = "Yes";
	public static final String ADDITIONALCOSTS_S = "Additional Costs";
	
	//Fitness parameters
	public static String GENERATION_S = "Generation";
	public static String ALPHABETA_S = "Alpha : Beta";
	public static String MUTATIONPROBABILITY_S = "Mutation Probability";
	public static String CROSSOVERPROBABILITY_S = "Crossover Probability";
	public static String INITIALCANDIDATEPOPULATION_S = "Initial Candidate Population";
	public static String TOBESET1_S = "tobeset1";
	public static String TOBESET2_S = "tobeset2";
	
	protected String value;
	protected String key;
	protected String[] options;
	
	public ModelType(String defaultType, String[] defaultOptions, String key){
		this.value = defaultType;
		this.options = defaultOptions;
		this.key = key;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setValue(String type){
		this.value = type;
	}
	
	public String[] getOptions(){
		return this.options;
	}
	
	public String getTitle(){
		return key;
	}
	
	public abstract void setOptions(String option);
	
}