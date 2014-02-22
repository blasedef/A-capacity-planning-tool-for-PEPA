package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.SystemEquationFitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.SystemEquationRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;

public class SystemEquationFitnessFunction extends FitnessFunction{
	
	protected Double populationFitness;
	protected Double performanceFitness;
	protected HashMap <String,Double> performanceResultsMap;
	protected HashMap <String,Double> populationResultsMap;
	protected HashMap <String,Double> candidate;
	private NodeHandler nodeHandler;
	protected IProgressMonitor monitor;
	protected HashMap<String,Double> targets;
	protected HashMap<String,Double> targetWeights;
	protected HashMap<String,Double> minPopulation;
	protected HashMap<String,Double> populationRanges;
	protected HashMap<String,Double> fitnessMap;
	protected HashMap<String,Double> populationWeights;
	protected PEPAConfig configPEPA;
	protected ODEConfig configODE;
	protected Recorder recorder;
	protected Double weightDenominator;
	protected SystemEquationFitnessFunctionParameters fitnessFunctionParameters;
	
	public SystemEquationFitnessFunction(FitnessFunctionParameters fitnessFunctionParameters,
			IProgressMonitor monitor,
			Recorder recorder){
		
		this.fitnessFunctionParameters = (SystemEquationFitnessFunctionParameters) fitnessFunctionParameters;
		
		this.configPEPA = this.fitnessFunctionParameters.getConfigPEPA();
		this.configODE = this.fitnessFunctionParameters.getConfigODE();
		this.nodeHandler = new NodeHandler(configPEPA, configODE);
		this.targets = this.fitnessFunctionParameters.getTargets();
		this.targetWeights = this.fitnessFunctionParameters.getTargetWeights();
		this.minPopulation = this.fitnessFunctionParameters.getMinPopulation();
		this.populationRanges = this.fitnessFunctionParameters.getPopulationRanges();
		this.fitnessMap = this.fitnessFunctionParameters.getFitnessMap();
		this.populationWeights = this.fitnessFunctionParameters.getPopulationWeights();
		this.monitor = monitor;
		this.fitness = 1000000.0;
		
		this.performanceResultsMap = new HashMap<String,Double>();
		this.populationResultsMap = new HashMap<String,Double>();
		this.candidate = getCandidateMap();
		this.recorder = recorder;
		
		this.weightDenominator = 0.0;
		
		for(Entry<String, Double> entry : this.populationWeights.entrySet()){
			this.weightDenominator += entry.getValue();
		}
	}
	
	public int getMaxSearchSpace(){
		Double temp = 1.0;
		
		for(Entry<String, Double> entry : this.populationRanges.entrySet()){
			temp *= entry.getValue();
		}
		
		return temp.intValue();
		
	}
	
	public HashMap<String,Double> getCandidate(){
		return this.candidate;
	}
	
	private HashMap<String,Double> getCandidateMap(){
		return this.nodeHandler.getSystemEquation();
	}
	
	public FitnessFunction copySelf(){
		FitnessFunction fitnessFunction = (FitnessFunction) new SystemEquationFitnessFunction(this.fitnessFunctionParameters,
				monitor,
				recorder);
		
		return fitnessFunction;
	}
	
	private void setPerformanceResultsMap(){
		this.performanceResultsMap = nodeHandler.getPerformanceResultsMap(this.candidate, monitor);
	}
	
	protected void setPerformanceFitness(){
		this.performanceFitness = 0.0;
		setPerformanceResultsMap();
		
		for(Entry<String, Double> entry : this.performanceResultsMap.entrySet()){
			Double ode = entry.getValue();
			Double target = targets.get(entry.getKey()).doubleValue();
			Double targetWeight = targetWeights.get(entry.getKey()).doubleValue();
			this.performanceFitness += (Math.abs(100 -((ode/target)*100))) * targetWeight;
		}
	}
	
	protected void setPopulationFitness(){
		
		this.populationResultsMap = new HashMap<String,Double>();
		
		for(Entry<String, Double> entry : candidate.entrySet()){
			String component = entry.getKey();
			Double value = entry.getValue();
			if(value > 0){
				value = value - minPopulation.get(component);
			} 
			Double range = this.populationRanges.get(component);
			Double weight = this.populationWeights.get(component)/this.weightDenominator;
			this.populationResultsMap.put(component, ((value/range)*100)*weight);
		}
		
		this.populationFitness = 0.0;
		
		for(Entry<String, Double> entry : this.populationResultsMap.entrySet()){
			Double value = entry.getValue();
			this.populationFitness += value;
		}
		
	}
	
	protected String getName(HashMap <String,Double> candidate){
		
		String name = "";
		
		for(Map.Entry<String, Double> entry : candidate.entrySet()){
			name = name + entry.getKey() + "[" + entry.getValue() + "]"; 
		}
		
		return name;
	}
	
	private void setFitness(){
		if(((SystemEquationRecorder) recorder).getCandidateMapToFitnessHash().containsKey(getName(candidate))){
			this.fitness = ((SystemEquationRecorder) recorder).getCandidateMapToFitnessHash().get(getName(candidate));
			this.performanceResultsMap = Tool.copyHashMap(((SystemEquationRecorder) recorder).getNameToPerformanceResultsMapHash().get(getName(candidate)));
			monitor.worked(1);
		} else {
			setPerformanceFitness();
			setPopulationFitness();
			Double alpha = this.fitnessMap.get(Config.FITNESS_ALPHA_PERFORMANCE_S);
			Double beta = this.fitnessMap.get(Config.FITNESS_BETA_POPULATION_S);
			this.fitness = (alpha*this.performanceFitness) + (beta*this.populationFitness);
		}
	}
	
	
	public Double getFitness(HashMap<String,Double> candidate){
		this.candidate = candidate;
		setFitness();
		return fitness;
	}
	
	public HashMap<String,Double> getPerformanceResultsMap(){
		return this.performanceResultsMap;
	}
}
