package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.SystemEquationRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class ParticleSwarmOptimsationSystemEquationFitnessFunction extends SystemEquationFitnessFunction {
	
	public ParticleSwarmOptimsationSystemEquationFitnessFunction(FitnessFunctionParameters fitnessFunctionParameters,
			IProgressMonitor monitor,
			Recorder recorder){
		
		super(fitnessFunctionParameters,
				monitor,
				recorder);
		
	}
	
	@Override
	public FitnessFunction copySelf(){
		FitnessFunction fitnessFunction = (FitnessFunction) new ParticleSwarmOptimsationSystemEquationFitnessFunction(fitnessFunctionParameters,
				monitor,
				recorder);
		
		return fitnessFunction;
	}
	
	protected void setPopulationFitness(HashMap<String,Double> maxPopulation){
		
		this.populationResultsMap = new HashMap<String,Double>();
		
		for(Entry<String, Double> entry : candidate.entrySet()){
			String component = entry.getKey();
			Double value = entry.getValue();
			if(value > 0){
				value = value - minPopulation.get(component);
			} 
			if(value > maxPopulation.get(entry.getKey())){
				Double range = this.populationRanges.get(component);
				Double weight = this.populationWeights.get(component)/this.weightDenominator;
				value *= 10;
				this.populationResultsMap.put(component, ((value/range)*100)*weight);
			} else {
				Double range = this.populationRanges.get(component);
				Double weight = this.populationWeights.get(component)/this.weightDenominator;
				this.populationResultsMap.put(component, ((value/range)*100)*weight);
			}
		}
		
		this.populationFitness = 0.0;
		
		for(Entry<String, Double> entry : this.populationResultsMap.entrySet()){
			Double value = entry.getValue();
			this.populationFitness += value;
		}
		
	}
	
	public Double getFitness(HashMap<String,Double> candidate, HashMap<String,Double> maximumPopulation){
		this.candidate = candidate;
		this.setFitness(maximumPopulation);
		return fitness;
	}
	
	private void setFitness(HashMap<String,Double> maximumPopulation){
		if(((SystemEquationRecorder) recorder).getCandidateMapToFitnessHash().containsKey(getName(candidate))){
			this.fitness = ((SystemEquationRecorder) recorder).getCandidateMapToFitnessHash().get(getName(candidate));
			this.performanceResultsMap = Tool.copyHashMap(((SystemEquationRecorder) recorder).getNameToPerformanceResultsMapHash().get(getName(candidate)));
			monitor.worked(1);
		} else {
			setPerformanceFitness();
			setPopulationFitness(maximumPopulation);
			Double alpha = this.fitnessMap.get(Config.FITNESS_ALPHA_PERFORMANCE_S);
			Double beta = this.fitnessMap.get(Config.FITNESS_BETA_POPULATION_S);
			this.fitness = (alpha*this.performanceFitness) + (beta*this.populationFitness);
		}
	}
}
