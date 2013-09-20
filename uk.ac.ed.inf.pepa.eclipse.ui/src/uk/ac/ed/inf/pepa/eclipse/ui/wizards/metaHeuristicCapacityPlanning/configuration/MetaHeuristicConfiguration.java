package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.configuration;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.CapacityPlanningOptionsMap;

/**
*
* MetaheuristicParameters:
* Used to provide values to the Wizard pages, and to also store variables for the Metaheuristic Job
* @author Christopher Williams
*
*/

public class MetaHeuristicConfiguration {
	

	//Metaheuristic type, 0 = Hill Climbing, 1 = Genetic Algorithm, 2 = Particle Swarm
	private int METAHEURISTICTYPE;

	//Hashmap to hold fitness function attributes and values
	public HashMap<String, Number> fitnessFunctionAttributesValuesHashMap = new HashMap<String, Number>();
	
	public MetaHeuristicConfiguration(String[] index){
		initialiseFitnessFunctionAttributesValues(index);
		
	}
	
	public void setType(int type){
		this.METAHEURISTICTYPE = type;
	}
	
	public int getType(){
		return this.METAHEURISTICTYPE;
	}
	
	/**
	 * 
	 * Initial configuration to Hill Climbing
	 * 
	 * @param attributes Fitness function attributes
	 * @param values Fitness function values
	 */
	public void initialiseFitnessFunctionAttributesValues(String[] index){
		Integer type;
		Number value;
		String attribute;
		
		for(int i = 0; i < index.length; i++){
			
			attribute = index[0];
			type = CapacityPlanningOptionsMap.fitnessFunctionAttributeTypesHashMap.get(attribute);
			value = CapacityPlanningOptionsMap.defaultAttributeValues.get(type);
			this.fitnessFunctionAttributesValuesHashMap.put(attribute, value);
		}
	}
	
	public void putAttributeValue(String attribute, Number value){
		this.fitnessFunctionAttributesValuesHashMap.put(attribute, value);
	}

}