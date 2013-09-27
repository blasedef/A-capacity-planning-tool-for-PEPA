package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.HashMap;
import java.util.Map;


public class MetaHeuristicType extends Configuration{
	
	//sub classes
	private MetaHeuristicAttributes attributes;
	private Targets targets;
	private PopulationRanges minPopulationRanges;
	private PopulationRanges maxPopulationRanges;
	private Fitness fitness;
	private String name;
	private PopulationRanges metaheuristicMax;
	private PopulationRanges metaheuristicMin;
	
	public MetaHeuristicType(String key, String value, String[] options, String name){
		super(key, value, options);
		this.attributes = new MetaHeuristicAttributes(value);
		this.targets = new Targets();
		this.minPopulationRanges = new PopulationRanges();
		this.maxPopulationRanges = new PopulationRanges();
		this.fitness = new Fitness();
		this.name = name;
		this.metaheuristicMax = new PopulationRanges();
		this.metaheuristicMin = new PopulationRanges();
	}
	
	public void updateAttributeValues(){
		this.attributes.updateMap(attributes.getAttributes(this.value));
	}
	
	
	public void resetAttributeValues(){
		this.attributes.updateMapToDefault(attributes.getAttributes(this.value));
	}
	
	public void updateTargetValues(){
		this.targets.updateTargetMap();
	}
	
	public void updateMinPopulationRanges(){
		this.minPopulationRanges.updatePopulationMap(ExperimentConfiguration.pEPAConfig.getSystemEquation(),ExperimentConfiguration.pEPAConfig.getInitialPopulation(),true);
	}
	
	public void updateMaxPopulationRanges(){
		this.maxPopulationRanges.updatePopulationMap(ExperimentConfiguration.pEPAConfig.getSystemEquation(),ExperimentConfiguration.pEPAConfig.getInitialPopulation(),false);
	}

	@Override
	public void setOptions(String option) {
		super.options = attributes.getAttributes(option);
		
	}
	
	@Override
	public void setValue(String type){
		this.value = type;
		this.attributes.updateMap(attributes.getAttributes(type));
	}
	
	public Map<String,Number> getTargetMap(){
		return this.targets.getMap();
	}
	
	public String getTargetMapValue(String key) {
		return this.targets.getMapValue(key);
	}
	
	public Map<String,Number> getFitnessMap(){
		return this.fitness.getMap();
	}
	
	public String getFitnessMapValue(String key) {
		return this.fitness.getMapValue(key);
	}
	
	public String[] getFitnessMapKeys(){
		return this.fitness.getMapKeys();
	}
	
	public void setFitnessMaxPopulation(String type){
		
		String[] options = attributes.getAttributes(type);
		
		Map<String,Number> map = new HashMap<String, Number>();
		Map<String,Number> fitness = this.metaheuristicMax.getMap();
		Map<String,Number> defaults = this.attributes.getDefaultValueMap();
		Map<String,String> types = ExperimentConfiguration.defaultOptionTypeMap;
		
		for(String key : options){
			if(!fitness.containsKey(key)){
				Number value;
				if(types.get(key).equals(ExperimentConfiguration.PERCENT)){
					value = 1.0;
				} else if (types.get(key).equals(ExperimentConfiguration.DOUBLE)){
					value = (Number) defaults.get(key);
				} else {
					value = (Number) defaults.get(key);
				}
				map.put(key,value);
			} else {
				Number value = fitness.get(key);
				map.put(key,value);
			}
		}
		
		this.metaheuristicMax.setMap(map);
	}
	
	public void setFitnessMinPopulation(String type){
		
		String[] options = attributes.getAttributes(type);
		
		Map<String,Number> map = new HashMap<String, Number>();
		Map<String,Number> fitness = this.metaheuristicMin.getMap();
		Map<String,String> types = ExperimentConfiguration.defaultOptionTypeMap;
		
		for(String key : options){
			if(!fitness.containsKey(key)){
				Number value;
				if(types.get(key).equals(ExperimentConfiguration.PERCENT)){
					value = 0.0;
				} else if (types.get(key).equals(ExperimentConfiguration.DOUBLE)){
					value = 1;
				} else {
					value = 1;
				}
				map.put(key,value);
			} else {
				Number value = fitness.get(key);
				map.put(key,value);
			}
		}
		
		this.metaheuristicMin.setMap(map);
	}
	
	public String[] getFitnessMinPopulationOptions(){
		return this.metaheuristicMin.getMapKeys();
	}
	
	public String[] getFitnessMaxPopulationOptions(){
		return this.metaheuristicMax.getMapKeys();
	}
	
	public Map<String,Number> getFitnessMinPopulationMap(){
		return this.metaheuristicMin.getMap();
	}
	
	public Map<String,Number> getFitnessMaxPopulationMap(){
		return this.metaheuristicMax.getMap();
	}
	
	public Map<String,Number> getMinPopMap(){
		return this.minPopulationRanges.getMap();
	}
	
	public Map<String,Number> getMaxPopMap(){
		return this.maxPopulationRanges.getMap();
	}
	
	public String getMinPopMapValue(String key) {
		return this.minPopulationRanges.getMapValue(key);
	}
	
	public String getMaxPopMapValue(String key) {
		return this.maxPopulationRanges.getMapValue(key);
	}
	
	public String getFitnessMinPopMapValue(String key){
		Number number;
		
		if(this.metaheuristicMin.getMap().containsKey(key)){
			number = this.metaheuristicMin.getMap().get(key);
		} else {
			number = attributes.getDefaultValueMap().get(key);
		}
		
		if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
			return "" + number.intValue();
		} else {
			return "" + (Double) number;
		}
		
	}

	public String getFitnessMaxPopMapValue(String key){
		Number number;
		
		if(this.metaheuristicMax.getMap().containsKey(key)){
			number = this.metaheuristicMax.getMap().get(key);
		} else {
			number = attributes.getDefaultValueMap().get(key);
		}
		
		if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
			return "" + number.intValue();
		} else {
			return "" + (Double) number;
		}
	}
	
	public String[] getAttributeMapKeys(){
		return this.attributes.getMapKeys();
	}
	
	public Map<String, Number> getAttributeMap() {
		return this.attributes.getMap();
	}
	
	public String getAttributeMapValue(String key){
		return this.attributes.getMapValue(key);
	}
	
	public String summary(boolean hasSecondary){
		String output;
		
		output = "" + this.key + " : " + this.value + "\n \n";
		
		output += this.name + " Metaheuristic attributes: \n";
		output += this.attributes.summary() + "\n";
		
		output += "Performance target values: \n";
		output += this.targets.summary() + "\n";

		output += "Minimum / Maximum population: \n";
		String[] options = this.minPopulationRanges.getMapKeys();
		for(String s : options){
			output += s + ": " + this.minPopulationRanges.getMapValue(s) + " / " + this.maxPopulationRanges.getMapValue(s) + "\n";
		}
		
		output += "\n";
			
		if(hasSecondary){
			output += "Minimum / Maximum values for secondary Metaheuristic: \n";
			
			options = this.metaheuristicMin.getMapKeys();
			for(String s : options){
				output += s + ": " + this.metaheuristicMin.getMapValueOfDifferentTypes(s) + " / " + this.metaheuristicMax.getMapValueOfDifferentTypes(s) + "\n";
			}
		}
		
		return output;
	}

	public void resetFitnessMap() {
		this.fitness.reset();
		
	}
	
}