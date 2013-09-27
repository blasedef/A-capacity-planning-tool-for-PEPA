package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;


public class Targets extends MetaHeuristicConfigurations {
	
	/**
	 * target values
	 */
	private Map<String,Number> targetOptionMap = new HashMap<String, Number>();
	
	public Targets(){
		this.targetOptionMap.put("no target",0.0);
	}

	@Override
	public Map<String, Number> getMap() {
		return this.targetOptionMap;
	}

	public void updateTargetMap() {
		
		String[] options = ExperimentConfiguration.oDEConfig.getLabels();
		
		Map<String,Number> map = new HashMap<String, Number>();
		
		for(String key : options){
			if(!this.targetOptionMap.containsKey(key)){
				Number value = 1.0;
				map.put(key,value);
			} else {
				Number value = this.targetOptionMap.get(key);
				map.put(key,value);
			}
		}
		
		this.targetOptionMap = map;
		
	}

	public String getMapValue(String key) {
		
		Number number;
		
		if(targetOptionMap.containsKey(key)){
			number = this.targetOptionMap.get(key);
		} else {
			number = 0.0;
		}
		return "" + (Double) number;
	}

}