package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;

public class PopulationRanges extends MetaHeuristicConfigurations {
	
	/**
	 * target values
	 */
	private Map<String,Number> populationOptionMap = new HashMap<String, Number>();
	
	public PopulationRanges(){
		this.populationOptionMap.put("no target",0.0);
	}

	@Override
	public Map<String, Number> getMap() {
		return this.populationOptionMap;
	}

	public void updatePopulationMap(String[] options, Number[] values, boolean min) {
		
		Map<String,Number> map = new HashMap<String, Number>();
		
		if(min){
			
			for(int i = 0; i < options.length; i++){
				
				String key = options[i];
				
				if(!this.populationOptionMap.containsKey(key)){
					Number value = 1;
					map.put(key,value);
				} else {
					Number value = this.populationOptionMap.get(key);
					map.put(key,value);
				}
			}
		} else {
			
			for(int i = 0; i < options.length; i++){
				
				String key = options[i];
				
				if(!this.populationOptionMap.containsKey(key)){
					Number value = values[i];
					map.put(key,value);
				} else {
					Number value = this.populationOptionMap.get(key);
					map.put(key,value);
				}
			}
		}
		
		this.populationOptionMap = map;
		
	}

	public String getMapValue(String key) {
		
		Number number;
		
		if(populationOptionMap.containsKey(key)){
			number = this.populationOptionMap.get(key);
		} else {
			number = 0;
		}
		return "" + number.intValue();
	}
	
	public String getMapValueOfDifferentTypes(String key) {
		
		Number number;
		
		if(populationOptionMap.containsKey(key)){
			if(ExperimentConfiguration.defaultOptionTypeMap.containsKey(key)){
				if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
					number = this.populationOptionMap.get(key).intValue();
				} else if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.EVEN)){
					number = this.populationOptionMap.get(key).intValue();
				} else {
					number = this.populationOptionMap.get(key).doubleValue();
				}
			} else {
				number = this.populationOptionMap.get(key).intValue();
			}
		} else {
			number = 0;
		}
		return "" + number;
	}

	public void setMap(Map<String, Number> map) {
		this.populationOptionMap = map;
		
	}
	
	public String[] getMapKeys() {
		return this.populationOptionMap.keySet().toArray(new String[0]);
	}

}
