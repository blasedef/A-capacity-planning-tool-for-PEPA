package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;


public class Fitness extends MetaHeuristicConfigurations {
	
	/**
	 * default attribute values
	 */
	private Map<String,Number> defaultAttributeOptionMap = new HashMap<String, Number>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2355214272600633041L;
	{
		put(ExperimentConfiguration.ALPHABETA_S,0.5);
	}};
	
	/**
	 * fitness mapping
	 */
	private Map<String,Number> fitnessOptionMap = new HashMap<String, Number>();
	
	public Fitness(){
		this.fitnessOptionMap.put(ExperimentConfiguration.ALPHABETA_S,defaultAttributeOptionMap.get(ExperimentConfiguration.ALPHABETA_S));

	}

	@Override
	public Map<String, Number> getMap() {
		return this.fitnessOptionMap;
	}

	public void updateFitnessMap(String[] options) {
		
		Map<String,Number> map = new HashMap<String, Number>();
		
		for(String key : options){
			if(!this.fitnessOptionMap.containsKey(key) && (!key.equals(ExperimentConfiguration.ALPHABETA_S))){
				Number value = 0.0;
				map.put(key,value);
			} else {
				Number value = this.fitnessOptionMap.get(key);
				map.put(key,value);
			}
		}
		
		this.fitnessOptionMap = map;
		
	}

	public String getMapValue(String key) {
		
		Number number;
		
		if(fitnessOptionMap.containsKey(key)){
			number = this.fitnessOptionMap.get(key);
		} else {
			number = this.defaultAttributeOptionMap.get(key);
		}
		
		if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
			return "" + (Integer) number;
		} else { 
			return "" + (Double) number;
		}
	}

	public String[] getMapKeys() {
		return this.fitnessOptionMap.keySet().toArray(new String[0]);
	}

	public void reset() {
		this.fitnessOptionMap = new HashMap<String, Number>();
		this.fitnessOptionMap.put(ExperimentConfiguration.ALPHABETA_S,defaultAttributeOptionMap.get(ExperimentConfiguration.ALPHABETA_S));
	}

}