package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Targets extends MetaHeuristicConfigurations {
	
	/**
	 * target values
	 */
	private Map<String,Number> targetOptionMap = new HashMap<String, Number>();
	
	/**
	 * target weighting
	 */
	private Map<String,Number> targetWeightOptionMap = new HashMap<String, Number>();
	
	public Targets(){
		this.targetOptionMap.put("no target",0.0);
		this.targetWeightOptionMap.put("no target",1.0);
	}

	@Override
	public Map<String, Number> getMap() {
		return this.targetOptionMap;
	}
	
	public Map<String, Number> getWeightMap() {
		return this.targetWeightOptionMap;
	}

	public void updateTargetMap() {
		
		String[] options = ExperimentConfiguration.oDEConfig.getLabels();
		
		Map<String,Number> map = new HashMap<String, Number>();
		Map<String,Number> weightMap = new HashMap<String, Number>();
		
		for(String key : options){
			if(!this.targetOptionMap.containsKey(key)){
				Number value = 1.0;
				map.put(key,value);
			} else {
				Number value = this.targetOptionMap.get(key);
				map.put(key,value);
			}
		}
		
		for(String key : options){
			Number value = 1.0 / options.length;
			weightMap.put(key,value);
		}
		
		this.targetOptionMap = map;
		this.targetWeightOptionMap = weightMap;
		
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
	
	public String getMapWeightValue(String key) {
		
		Number number;
		
		if(targetWeightOptionMap.containsKey(key)){
			number = this.targetWeightOptionMap.get(key);
		} else {
			number = 0.0;
		}
		return "" + (Double) number;
	}
	
	@Override
	public String summary(){
		
		Map<String,Number> map = getMap();
		String output = "";
		
		for (Entry<String, Number> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Number value = entry.getValue();
		    output += key + " : " + value + "\n";
		    
		}
		
		Map<String,Number> weightMap = getWeightMap();
		
		for (Entry<String, Number> entry : weightMap.entrySet()) {
		    String key = entry.getKey();
		    Number value = entry.getValue();
		    output += key + " weight : " + value + "\n";
		    
		}
		
		return output;
		
	}

}