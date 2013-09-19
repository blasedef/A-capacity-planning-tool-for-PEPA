package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters;

import java.util.HashMap;

/**
*
* MetaheuristicParameters:
* Used to provide values to the Wizard pages, and to also store variables for the Metaheuristic Job
* @author Christopher Williams
*
*/

public class MetaHeuristicParameters {
	
	public int id;

	//Metaheuristic type, 0 = Hill Climbing, 1 = Genetic Algorithm, 2 = Particle Swarm
	public int METAHEURISTICTYPE;

	//Hashmap to hold fitness function attributes and values
	public HashMap<String, Number> fitnessFunctionAttributesValuesHashMap = new HashMap<String, Number>();
	
	public MetaHeuristicParameters(int id, String[] s){
		this.id = id;
		initialiseFitnessFunctionAttributesValues(s);
		
	}
	
	/**
	 * 
	 * @param attributes Fitness function attributes
	 * @param values Fitness function values
	 */
	public void initialiseFitnessFunctionAttributesValues(String[] attributes){
		Integer type;
		Number value;
		for(String attribute : attributes){
			type = MetaHeuristicTypeParameters.fitnessFunctionAttributeTypesHashMap.get(attribute);
			value = MetaHeuristicTypeParameters.defaultFitnessFunctionAttributeValues.get(type);
			this.fitnessFunctionAttributesValuesHashMap.put(attribute, value);
		}
	}
	
	/**
	 * 
	 * NEED TO HANDLE THIS BETTER
	 * 
	 * 
	 * @param attributes Fitness function attributes
	 * @param values Fitness function values
	 */
	public void setFitnessFunctionAttributes(int i){
		String[] attributes;
		if(i == 0){
			attributes = MetaHeuristicTypeParameters.getAttributes(MetaHeuristicTypeParameters.hillClimbingFitnessFunctionAttributeIndex);
		} else if (i == 1) {
			attributes = MetaHeuristicTypeParameters.getAttributes(MetaHeuristicTypeParameters.geneticAlgorithmFitnessFunctionAttributeIndex);
		} else {
			attributes = MetaHeuristicTypeParameters.getAttributes(MetaHeuristicTypeParameters.particleSwarmOptimisationFitnessFunctionAttributeIndex);
		}
		Number value;
		for(String attribute : attributes){
			value = MetaHeuristicTypeParameters.fitnessFunctionAttributeTypesHashMap.get(attribute);
			this.fitnessFunctionAttributesValuesHashMap.put(attribute, value);
		}
	}
	
	/**
	 * All numbers end up as doubles, but are stored as Number so that we can output back to user as different number types.
	 * Its to avoid confusion for the user, when being told to enter a Whole number, but see a float in the text box.
	 * @param attribute
	 * @param input
	 */
	public void setFitnessFunctionAttributeValue(String attribute, String input){
		Double value = Double.parseDouble(input);
		this.fitnessFunctionAttributesValuesHashMap.put(attribute, value);
	}

}