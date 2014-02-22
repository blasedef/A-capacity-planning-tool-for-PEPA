package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters;

import java.util.HashMap;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;

public class SystemEquationFitnessFunctionParameters extends FitnessFunctionParameters {
	
	private HashMap <String,Double> performanceResultsMap;
	private HashMap <String,Double> populationResultsMap;
	private HashMap<String,Double> targets;
	private HashMap<String,Double> targetWeights;
	private HashMap<String,Double> minPopulation;
	private HashMap<String,Double> populationRanges;
	private HashMap<String,Double> fitnessMap;
	private HashMap<String,Double> populationWeights;
	private PEPAConfig configPEPA;
	private ODEConfig configODE;

	public SystemEquationFitnessFunctionParameters(ConfigurationModel configurationModel){
		this.setConfigPEPA(configurationModel.configPEPA);
		this.setConfigODE(configurationModel.configODE);
		this.setTargets(Tool.copyHashMap(configurationModel.performanceTargetsAndWeights.getLeftMap()));
		this.setTargetWeights(Tool.copyHashMap(configurationModel.performanceTargetsAndWeights.getRightMap()));
		this.setMinPopulation(Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getLeftMap()));
		this.setPopulationRanges(Tool.copyHashMap(getPopulationRanges(configurationModel)));
		this.setFitnessMap(Tool.copyHashMap(configurationModel.systemEquationFitnessWeights.getLeftMap()));
		this.setPopulationWeights(Tool.copyHashMap(configurationModel.populationWeights.getLeftMap()));
	}
	
	public HashMap<String,Double> getPopulationRanges(ConfigurationModel configurationModel){
		
		HashMap<String,Double> systemEquationPopulationRanges = new HashMap<String,Double>();
		
		HashMap<String,Double> minPopulationRange = Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getLeftMap());
		HashMap<String,Double> maxPopulationRange = Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getRightMap());
		
		for(Entry<String, Double> entry : minPopulationRange.entrySet()){
			double range = (maxPopulationRange.get(entry.getKey()) - minPopulationRange.get(entry.getKey())) + 1;
			systemEquationPopulationRanges.put(entry.getKey(), range);
		}
		
		return systemEquationPopulationRanges;
	}

	public void setPerformanceResultsMap(HashMap <String,Double> performanceResultsMap) {
		this.performanceResultsMap = performanceResultsMap;
	}

	public HashMap <String,Double> getPerformanceResultsMap() {
		return performanceResultsMap;
	}

	public void setPopulationResultsMap(HashMap <String,Double> populationResultsMap) {
		this.populationResultsMap = populationResultsMap;
	}

	public HashMap <String,Double> getPopulationResultsMap() {
		return populationResultsMap;
	}

	public void setTargets(HashMap<String,Double> targets) {
		this.targets = targets;
	}

	public HashMap<String,Double> getTargets() {
		return targets;
	}

	public void setTargetWeights(HashMap<String,Double> targetWeights) {
		this.targetWeights = targetWeights;
	}

	public HashMap<String,Double> getTargetWeights() {
		return targetWeights;
	}

	public void setMinPopulation(HashMap<String,Double> minPopulation) {
		this.minPopulation = minPopulation;
	}

	public HashMap<String,Double> getMinPopulation() {
		return minPopulation;
	}

	public void setPopulationRanges(HashMap<String,Double> populationRanges) {
		this.populationRanges = populationRanges;
	}

	public HashMap<String,Double> getPopulationRanges() {
		return populationRanges;
	}

	public void setFitnessMap(HashMap<String,Double> fitnessMap) {
		this.fitnessMap = fitnessMap;
	}

	public HashMap<String,Double> getFitnessMap() {
		return fitnessMap;
	}

	public void setPopulationWeights(HashMap<String,Double> populationWeights) {
		this.populationWeights = populationWeights;
	}

	public HashMap<String,Double> getPopulationWeights() {
		return populationWeights;
	}

	public void setConfigPEPA(PEPAConfig configPEPA) {
		this.configPEPA = configPEPA;
	}

	public PEPAConfig getConfigPEPA() {
		return configPEPA;
	}

	public void setConfigODE(ODEConfig configODE) {
		this.configODE = configODE;
	}

	public ODEConfig getConfigODE() {
		return configODE;
	}
	
}
