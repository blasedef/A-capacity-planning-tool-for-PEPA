package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;

public class MetaHeuristicType extends ModelType{
	
	//metaheuristic types
	private static final String[] metaHeuristicTypeList = new String[] {HILLCLIMBING_S,GENETICALGORITHM_S,PARTICLESWARMOPTIMISATION_S};
	
	//metaheuristic attribute lists
	private static String[] hillClimbingFitnessFunctionAttributeList = new String[] {GENERATION_S,ALPHABETA_S,MUTATIONPROBABILITY_S};
	private static String[] geneticAlgorithmFitnessFunctionAttributeList = new String[] {GENERATION_S,ALPHABETA_S,MUTATIONPROBABILITY_S,CROSSOVERPROBABILITY_S,INITIALCANDIDATEPOPULATION_S};
	private static String[] particleSwarmOptimisationFitnessFunctionAttributeList = new String[] {GENERATION_S,TOBESET1_S,TOBESET2_S};
	
	//sub classes
	public static FitnessFunction fitnessFunction;
	public static Targets targets;
	public static PopulationRanges minPopulationRanges;
	public static PopulationRanges maxPopulationRanges;
	
	
	//option mapping for fitness options
	private static final Map<String,String[]> attributeListMap = new HashMap<String, String[]>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 9024689169600758025L;
	{
		put(HILLCLIMBING_S,hillClimbingFitnessFunctionAttributeList);
		put(GENETICALGORITHM_S,geneticAlgorithmFitnessFunctionAttributeList);
		put(PARTICLESWARMOPTIMISATION_S,particleSwarmOptimisationFitnessFunctionAttributeList);
		
	}};
	
	public MetaHeuristicType(String defaultType){
		super(defaultType, metaHeuristicTypeList, "Metaheurstic Type");
		MetaHeuristicType.fitnessFunction = new FitnessFunction(attributeListMap.get(defaultType));
		MetaHeuristicType.targets = new Targets();
		MetaHeuristicType.minPopulationRanges = new PopulationRanges();
		MetaHeuristicType.maxPopulationRanges = new PopulationRanges();
	}
	
	public void updateFitnessFunctionValues(){
		MetaHeuristicType.fitnessFunction.updateFitnessMap(attributeListMap.get(this.value));
	}
	
	
	public void resetFitnessFunctionValues(){
		MetaHeuristicType.fitnessFunction.updateFitnessMapToDefault(attributeListMap.get(this.value));
	}
	
	public void updateTargetValues(){
		MetaHeuristicType.targets.updateTargetMap();
	}
	
	public void updateMinPopulationRanges(){
		MetaHeuristicType.minPopulationRanges.updatePopulationMap();
	}
	
	public void updateMaxPopulationRanges(){
		MetaHeuristicType.maxPopulationRanges.updatePopulationMap();
	}

	@Override
	public void setOptions(String option) {
		super.options = MetaHeuristicType.attributeListMap.get(option);
		
	}
	
	@Override
	public void setValue(String type){
		this.value = type;
	}
	
	public Targets getTarget(){
		return null;
	}
	
}