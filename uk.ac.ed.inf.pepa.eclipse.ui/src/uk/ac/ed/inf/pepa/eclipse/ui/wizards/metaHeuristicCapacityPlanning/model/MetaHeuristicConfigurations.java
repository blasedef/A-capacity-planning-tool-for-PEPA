package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.Map;
import java.util.Map.Entry;

public abstract class MetaHeuristicConfigurations {
	
	public abstract Map<String,Number> getMap();
	
	public String summary(){
		
		Map<String,Number> map = getMap();
		String output = "";
		
		for (Entry<String, Number> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Number value = entry.getValue();
		    output += key + " : " + value + "\n";
		    
		}
		return output;
		
	}
	
}