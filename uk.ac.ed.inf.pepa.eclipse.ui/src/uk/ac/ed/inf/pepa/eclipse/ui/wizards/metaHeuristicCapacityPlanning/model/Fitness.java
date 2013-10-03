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
	private Map<String,Number> fitnessOptionMap = new HashMap<String, Number>()	
	{

	/**
		 * 
		 */
		private static final long serialVersionUID = -7778843389993990902L;

	{
		put(ExperimentConfiguration.ALPHABETA_S,0.5);
	}};

	@Override
	public Map<String, Number> getMap() {
		return this.fitnessOptionMap;
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
	
}