package uk.ac.ed.inf.pepa.cpt.searchEngine.fitnessFunctions;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;

public class ModelConfigurationFitnessFunction implements FitnessFunction {

	private boolean higherIsGood;
	public Double performance, population;
	
	public ModelConfigurationFitnessFunction(){
		
		if(CPTAPI.getEvaluationControls().getValue().equals(Config.EVALARPT)){
			this.higherIsGood = false;
		} else {
			this.higherIsGood = true;
		}
		
	}
	
	public Double assessDomain(HashMap<String,Double> domainMap){
		
		HashMap<String,Double> workingMap = new HashMap<String, Double>();
		Double totalWeight = 0.0;
		Double componentPopulation, min, range, result, wei;
		String[] keys = domainMap.keySet().toArray(new String[workingMap.keySet().size()]);
		
		for(int i = 0; i < keys.length;i++){
			totalWeight += Double.parseDouble(CPTAPI.getPopulationControls().getValue(keys[i], Config.LABWEI));
			min = Double.parseDouble(CPTAPI.getPopulationControls().getValue(keys[i], Config.LABMIN));
			componentPopulation = domainMap.get(keys[i]) - min;
			range = Double.parseDouble(CPTAPI.getPopulationControls().getValue(keys[i], Config.LABRAN));
			workingMap.put(keys[i], componentPopulation/range);
		}
		
		result = 0.0;
		
		
		for(int i = 0; i < keys.length;i++){
			wei = Double.parseDouble(CPTAPI.getPopulationControls().getValue(keys[i], Config.LABWEI));
			result += workingMap.get(keys[i]) * wei/totalWeight;
		}
		
		this.population = result;
		return result;
	}
	
	public Double assessPerformance(HashMap<String,Double> performanceMap){
		
		HashMap<String,Double> workingMap = new HashMap<String, Double>();
		Double totalWeight = 0.0;
		Double actual, target, wei, result;
		String[] keys = performanceMap.keySet().toArray(new String[workingMap.keySet().size()]);
		
		
		for(int i = 0; i < keys.length;i++){
			
			totalWeight += Double.parseDouble(CPTAPI.getTargetControl().getValue(keys[i], Config.LABWEI));
			target = Double.parseDouble(CPTAPI.getTargetControl().getValue(keys[i], Config.LABTAR));
			actual = performanceMap.get(keys[i]);
			if(higherIsGood){
				if(actual >= target){
					actual = 0.0;
					workingMap.put(keys[i], actual);
				} else {
					actual = (target - actual);
					workingMap.put(keys[i], actual);
				}
			} else {
				if(actual <= target){
					actual = 0.0;
					workingMap.put(keys[i], actual);
				} else {
					actual = (target - actual);
					workingMap.put(keys[i], actual);
				}
			}
			
		}
		
		result = 0.0;
		
		for(int i = 0; i < keys.length;i++){
			wei = Double.parseDouble(CPTAPI.getTargetControl().getValue(keys[i], Config.LABWEI));
			result += workingMap.get(keys[i]) * wei/totalWeight;
		}
		
		this.performance = result;
		return result;
		
	}
	
	
	public Double assessFitness(HashMap<String,Double> domainMap, HashMap<String,Double> performanceMap){
		
		String[] keys = CPTAPI.getFitnessWeightControls().getKeys();
		Double resources, performance;
		Double totalWeight = 0.0;
		
		for(int i = 0; i < keys.length; i++){
			totalWeight += Double.parseDouble(CPTAPI.getFitnessWeightControls().getValue(keys[i]));
		}
		
		resources = Double.parseDouble(CPTAPI.getFitnessWeightControls().getValue(Config.FITRES))/totalWeight;
		performance = Double.parseDouble(CPTAPI.getFitnessWeightControls().getValue(Config.FITPER))/totalWeight;
		
		return (resources*assessDomain(domainMap))+(performance*assessPerformance(performanceMap));
		
	}

}
