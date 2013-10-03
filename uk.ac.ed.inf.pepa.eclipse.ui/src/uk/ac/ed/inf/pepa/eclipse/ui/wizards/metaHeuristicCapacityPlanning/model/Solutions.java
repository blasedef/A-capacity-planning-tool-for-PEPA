package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.Comparator;
import java.util.Map;

public class Solutions implements Comparator<Solutions>, Comparable<Solutions> {
	
	private String systemEquation;
	public Double fitness;
	private Double performanceFitness;
	private Double populationFitness;
	private Map<String, Double> performanceFitnessMap;
	private Map<String, Double> populationFitnessMap;
	private Double created;
	private int generation;
	
	public Solutions(String systemEquation, 
			Double fitness, 
			Double performanceFitness, 
			Double populationFitness, 
			Map<String, Double> performanceFitnessMap, 
			Map<String, Double> populationFitnessMap,
			Double created, int generation){
		
		this.systemEquation = systemEquation;
		this.fitness = fitness;
		this.performanceFitness = performanceFitness;
		this.populationFitness = populationFitness;
		this.performanceFitnessMap = performanceFitnessMap;
		this.populationFitnessMap = populationFitnessMap;
		this.created = created;
		this.generation = generation;
		
	}

	@Override
	public int compare(Solutions arg0, Solutions arg1) {
		
		if(arg0.fitness.intValue() < arg1.fitness.intValue()){
			return -1;
		}
		if(arg0.fitness.intValue() > arg1.fitness.intValue()){
			return 1;
		}
		return 0;
	}
	
	public String summary(){
		return "" 
		+ this.systemEquation + " : " 
		+ this.fitness + " : " 
		+ this.performanceFitness + " : " 
		+ this.populationFitness + " : "
		+ this.performanceFitnessMap + " : "
		+ this.populationFitnessMap + " : "
		+ this.created + " : "
		+ this.generation;
	}

	@Override
	public int compareTo(Solutions arg0) {
		
		if(this.fitness.intValue() < arg0.fitness.intValue()){
			return -1;
		}
		if(this.fitness.intValue() > arg0.fitness.intValue()){
			return 1;
		}
		return 0;
	}

}
