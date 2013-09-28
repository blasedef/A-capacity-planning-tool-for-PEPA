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
	private PopulationRanges secondExperimentMaxRange;
	private PopulationRanges secondExperimentMinRange;
	private String second;
	
	public MetaHeuristicType(String key, String value, String[] options, String name){
		super(key, value, options);
		this.attributes = new MetaHeuristicAttributes(value);
		this.targets = new Targets();
		this.minPopulationRanges = new PopulationRanges();
		this.maxPopulationRanges = new PopulationRanges();
		this.fitness = new Fitness();
		this.name = name;
		this.secondExperimentMaxRange = new PopulationRanges();
		this.secondExperimentMinRange = new PopulationRanges();
	}
	
	public void updateNetwork(String networkType){
		
		this.second = this.getValue();
		this.setValue(ExperimentConfiguration.HILLCLIMBING_S);
		getFitnessMap().put(ExperimentConfiguration.DELTASIGMA_S, 0.5);
		
		if(networkType.equals(ExperimentConfiguration.METAHEURISTICDRIVEN_S)){
			this.setSecondExperimentMinRangesToDrivenMode(this.second);
			this.setSecondExperimentMaxRangesToDrivenMode(this.second);
		} else {
			this.setSecondExperimentRangesToPipeLineMode();
		}
	}
	
	private void setSecondExperimentRangesToPipeLineMode() {
		
		String[] keys = ExperimentConfiguration.pEPAConfig.getSystemEquation();
		Number[] maxRange = new Number[keys.length];
		for(int i = 0; i < maxRange.length; i++){
			int distance;
			if(maxPopulationRanges.getMap().containsKey(keys[i]) && minPopulationRanges.getMap().containsKey(keys[i])){
				distance = maxPopulationRanges.getMap().get(keys[i]).intValue() - minPopulationRanges.getMap().get(keys[i]).intValue();
				maxRange[i] = Math.ceil(distance * 0.10);
			} else {
				maxRange[i] = Math.ceil(ExperimentConfiguration.pEPAConfig.getInitialPopulation()[i].doubleValue() * 0.10);
			}
		};
		
		this.secondExperimentMinRange.updatePopulationMap(keys,ExperimentConfiguration.pEPAConfig.getInitialPopulation(),true);
		this.secondExperimentMaxRange.updatePopulationMap(keys,maxRange,false);
		
	}
	

	public String getSecondaryType(){
		return this.second;
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
	
	public void setSecondExperimentMaxRangesToDrivenMode(String type){
		
		String[] options = attributes.getAttributes(type);
		
		Map<String,Number> map = new HashMap<String, Number>();
		Map<String,Number> fitness = this.secondExperimentMaxRange.getMap();
		Map<String,Number> defaults = this.attributes.getDefaultValueMap();
		Map<String,String> types = ExperimentConfiguration.defaultOptionTypeMap;
		
		for(String key : options){
			if(!fitness.containsKey(key)){
				Number value;
				if(types.get(key).equals(ExperimentConfiguration.PERCENT)){
					value = 1.0;
				} else if (types.get(key).equals(ExperimentConfiguration.DOUBLE)){
					value = (Number) defaults.get(key);
				} else if (types.get(key).equals(ExperimentConfiguration.EVEN)){
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
		
		this.secondExperimentMaxRange.setMap(map);
	}
	
	public void setSecondExperimentMinRangesToDrivenMode(String type){
		
		String[] options = attributes.getAttributes(type);
		
		Map<String,Number> map = new HashMap<String, Number>();
		Map<String,Number> fitness = this.secondExperimentMinRange.getMap();
		Map<String,String> types = ExperimentConfiguration.defaultOptionTypeMap;
		
		for(String key : options){
			if(!fitness.containsKey(key)){
				Number value;
				if(types.get(key).equals(ExperimentConfiguration.PERCENT)){
					value = 0.0;
				} else if (types.get(key).equals(ExperimentConfiguration.DOUBLE)){
					value = 1;
				} else if (types.get(key).equals(ExperimentConfiguration.EVEN)){
					value = 2;
				} else {
					value = 1;
				}
				map.put(key,value);
			} else {
				Number value = fitness.get(key);
				map.put(key,value);
			}
		}
		
		this.secondExperimentMinRange.setMap(map);
	}
	
	public String[] getExperimentMinPopulationOptions(){
		return this.secondExperimentMinRange.getMapKeys();
	}
	
	public String[] getExperimentMaxPopulationOptions(){
		return this.secondExperimentMaxRange.getMapKeys();
	}
	
	public Map<String,Number> getExperimentMinPopulationMap(){
		return this.secondExperimentMinRange.getMap();
	}
	
	public Map<String,Number> getExperimentMaxPopulationMap(){
		return this.secondExperimentMaxRange.getMap();
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
	
	public String getSecondExperimentMinPopMapValue(String key){
		Number number;
		
		if(this.secondExperimentMinRange.getMap().containsKey(key)){
			number = this.secondExperimentMinRange.getMap().get(key);
		} else {
			number = attributes.getDefaultValueMap().get(key);
		}
		
		if(ExperimentConfiguration.defaultOptionTypeMap.containsKey(key)){
			if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
				return "" + number.intValue();
			} else if (ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.EVEN)) {
				return "" + number.intValue();
			} else {
				return "" + (Double) number;
			}
		} else {
			return "" + number.intValue();
		}
		
	}

	public String getSecondExperimentMaxPopMapValue(String key){
		Number number;
		
		if(this.secondExperimentMaxRange.getMap().containsKey(key)){
			number = this.secondExperimentMaxRange.getMap().get(key);
			
		} else {
			number = attributes.getDefaultValueMap().get(key);
		}
		
		if(ExperimentConfiguration.defaultOptionTypeMap.containsKey(key)){
			if(ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.INTEGER)){
				return "" + number.intValue();
			} else if (ExperimentConfiguration.defaultOptionTypeMap.get(key).equals(ExperimentConfiguration.EVEN)) {
				return "" + number.intValue();
			} else {
				return "" + (Double) number;
			}
		} else {
			return "" + number.intValue();
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
		
		
		
		if(!ExperimentConfiguration.metaHeuristicNetworkType.getValue().equals(ExperimentConfiguration.METAHEURISTICSINGLE_S)){
			output = "Search the metaheuristic space using: " + this.value + "\n";
			output += "Search the system equation space using: " + this.second + "\n \n";
		} else {
			output = "" + this.key + " : " + this.value + "\n \n";
		}
		
		output += this.name + " Metaheuristic attributes: \n";
		output += this.attributes.summary() + "\n";
		
		output += "Performance target values: \n";
		output += this.targets.summary() + "\n";

		output += "Minimum / Maximum population: \n";
		String[] options = this.minPopulationRanges.getMapKeys();
		for(String s : options){
			output += s + ": " + this.minPopulationRanges.getMapValue(s) + " / " + this.maxPopulationRanges.getMapValue(s) + "\n";
		}
		
		System.out.println(output);
		
		output += "\n";
			
		if(hasSecondary){
			output += "Minimum / Maximum values for secondary Metaheuristic: \n";
			
			options = this.secondExperimentMinRange.getMapKeys();
			for(String s : options){
				output += s + ": " + this.secondExperimentMinRange.getMapValueOfDifferentTypes(s) + " / " + this.secondExperimentMaxRange.getMapValueOfDifferentTypes(s) + "\n";
			}
		}
		
		return output;
	}

	public void resetFitnessMap() {
		this.fitness.reset();
		
	}
	
}