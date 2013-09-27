package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;

public class MetaHeuristicAttributes extends MetaHeuristicConfigurations {
	
	//metaheuristic attribute lists
	private String[] hillClimbingAttributeList = new String[] {ExperimentConfiguration.EXPERIMENTS_S, ExperimentConfiguration.GENERATION_S,ExperimentConfiguration.MUTATIONPROBABILITY_S};
	private String[] particleSwarmOptimisationAttributeList = new String[] {ExperimentConfiguration.EXPERIMENTS_S, ExperimentConfiguration.GENERATION_S,ExperimentConfiguration.TOBESET1_S,ExperimentConfiguration.TOBESET2_S};
	private String[] geneticAlgorithmAttributeList = new String[] {ExperimentConfiguration.EXPERIMENTS_S, ExperimentConfiguration.GENERATION_S,ExperimentConfiguration.MUTATIONPROBABILITY_S,ExperimentConfiguration.CROSSOVERPROBABILITY_S,ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S};
	
	//option mapping for attribute options
	private final Map<String,String[]> attributeListMap = new HashMap<String, String[]>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 9024689169600758025L;
	{
		put(ExperimentConfiguration.HILLCLIMBING_S,hillClimbingAttributeList);
		put(ExperimentConfiguration.GENETICALGORITHM_S,geneticAlgorithmAttributeList);
		put(ExperimentConfiguration.PARTICLESWARMOPTIMISATION_S,particleSwarmOptimisationAttributeList);
	}};
	
	
	/**
	 * default attribute values
	 */
	private Map<String,Number> defaultAttributeValueMap = new HashMap<String, Number>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2355214272600633041L;
	{
		put(ExperimentConfiguration.EXPERIMENTS_S,1);
		put(ExperimentConfiguration.GENERATION_S,1000);
		put(ExperimentConfiguration.ALPHABETA_S,0.5);
		put(ExperimentConfiguration.MUTATIONPROBABILITY_S,0.5);
		put(ExperimentConfiguration.CROSSOVERPROBABILITY_S,0.2);
		put(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S,100);
		put(ExperimentConfiguration.TOBESET1_S,5);
		put(ExperimentConfiguration.TOBESET2_S,10.0);
	}};
	
	/**
	 * fitness values
	 */
	private Map<String,Number> attributeOptionMap = new HashMap<String, Number>();
	
	
	public MetaHeuristicAttributes(String key){
		String[] options = getAttributes(key);
		updateMapToDefault(options);
	}
	
	public void updateMapToDefault(String[] options){
		
		attributeOptionMap = new HashMap<String, Number>();
		
		for(String key : options){
			Number value = this.defaultAttributeValueMap.get(key);
			this.attributeOptionMap.put(key,value);
		}
	}
	
	public void updateMap(String[] options) {
		
		Map<String,Number> map = new HashMap<String, Number>();
		
		for(String key : options){
			if(!this.attributeOptionMap.containsKey(key)){
				Number value = this.defaultAttributeValueMap.get(key);
				map.put(key,value);
			} else {
				Number value = this.attributeOptionMap.get(key);
				map.put(key,value);
			}
		}
		
		this.attributeOptionMap = map;
		
	}
	
	public String[] getMapKeys(){
		return this.attributeOptionMap.keySet().toArray(new String[0]);
	}
	
	public String getMapValue(String key){
		
		Number number;
		
		if(attributeOptionMap.containsKey(key)){
			number = this.attributeOptionMap.get(key);
		} else {
			number = this.defaultAttributeValueMap.get(key);
		}
		
		if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
			return "" + (Integer) number;
		} else {
			return "" + (Double) number;
		}
	}
	
	public Map<String,Number> getDefaultValueMap(){
		return defaultAttributeValueMap;
	}
	
	public String getValueType(String key){
		return "" + ExperimentConfiguration.defaultOptionTypeMap.get(key);
	}

	@Override
	public Map<String, Number> getMap() {
		return this.attributeOptionMap;
	}
	
	public String[] getAttributes(String key){
		return this.attributeListMap.get(key);
	}
	
}