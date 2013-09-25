package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;

public class FitnessFunction extends MetaHeuristicConfigurations {
	
	/**
	 * default fitness values
	 */
	private Map<String,Number> defaultFitnessFunctionOptionMap = new HashMap<String, Number>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2355214272600633041L;
	{
		put(ModelType.GENERATION_S,1000);
		put(ModelType.ALPHABETA_S,0.5);
		put(ModelType.MUTATIONPROBABILITY_S,0.5);
		put(ModelType.CROSSOVERPROBABILITY_S,0.2);
		put(ModelType.INITIALCANDIDATEPOPULATION_S,100);
		put(ModelType.TOBESET1_S,5);
		put(ModelType.TOBESET2_S,10.0);
	}};
	
	/**
	 * fitness values
	 */
	private Map<String,Number> fitnessFunctionOptionMap = new HashMap<String, Number>();
	
	/**
	 * fitness value types
	 */
	private Map<String,String> fitnessFunctionTypeOptionMap = new HashMap<String, String>()
	{/**
		 * 
		 */
		private static final long serialVersionUID = -1942691622505736873L;

	{
		put(ModelType.GENERATION_S,ModelType.INTEGER);
		put(ModelType.ALPHABETA_S,ModelType.PERCENT);
		put(ModelType.MUTATIONPROBABILITY_S,ModelType.PERCENT);
		put(ModelType.CROSSOVERPROBABILITY_S,ModelType.PERCENT);
		put(ModelType.INITIALCANDIDATEPOPULATION_S,ModelType.INTEGER);
		put(ModelType.TOBESET1_S,ModelType.INTEGER);
		put(ModelType.TOBESET2_S,ModelType.PERCENT);
	}};
	
	public FitnessFunction(String[] defaultOptions){
		updateFitnessMapToDefault(defaultOptions);
	}
	
	public void updateFitnessMapToDefault(String[] options){
		
		fitnessFunctionOptionMap = new HashMap<String, Number>();
		
		for(String key : options){
			Number value = this.defaultFitnessFunctionOptionMap.get(key);
			this.fitnessFunctionOptionMap.put(key,value);
		}
	}
	
	public void updateFitnessMap(String[] options) {
		
		Map<String,Number> map = new HashMap<String, Number>();
		
		for(String key : options){
			if(!this.fitnessFunctionOptionMap.containsKey(key)){
				Number value = this.defaultFitnessFunctionOptionMap.get(key);
				map.put(key,value);
			} else {
				Number value = this.fitnessFunctionOptionMap.get(key);
				map.put(key,value);
			}
		}
		
		this.fitnessFunctionOptionMap = map;
		
	}
	
	public String[] getFitnessMapKeys(){
		return this.fitnessFunctionOptionMap.keySet().toArray(new String[0]);
	}
	
	public String getFitnessMapValue(String key){
		
		Number number;
		
		if(fitnessFunctionOptionMap.containsKey(key)){
			number = this.fitnessFunctionOptionMap.get(key);
		} else {
			number = this.defaultFitnessFunctionOptionMap.get(key);
		}
		
		if(fitnessFunctionTypeOptionMap.get(key).equals(ModelType.INTEGER)){
			return "" + (Integer) number;
		} else {
			return "" + (Double) number;
		}
	}
	
	public String getFitnessValueType(String key){
		return "" + this.fitnessFunctionTypeOptionMap.get(key);
	}

	@Override
	public Map<String, Number> getMap() {
		return this.fitnessFunctionOptionMap;
	}

	public Map<String, String> getTypeMap() {
		return this.fitnessFunctionTypeOptionMap;
	}
	
}