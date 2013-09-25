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

	public void updatePopulationMap() {
		
		String[] options = Models.oDEConfig.getSystemEquation();
		Integer[] values = Models.oDEConfig.getInitialPopulation();
		
		Map<String,Number> map = new HashMap<String, Number>();
		
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
		
		this.populationOptionMap = map;
		
	}

	public String getTargetMapValue(String option) {
		return "" + this.populationOptionMap.get(option);
	}

}
