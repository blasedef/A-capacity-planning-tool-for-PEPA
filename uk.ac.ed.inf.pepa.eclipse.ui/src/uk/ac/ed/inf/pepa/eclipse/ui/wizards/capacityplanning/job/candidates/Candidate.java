package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;

public abstract class Candidate implements Comparator<Candidate>, Comparable<Candidate> {
	
	protected double fitness;
	protected String name;
	protected double createdAt;
	protected int generation;
	protected HashMap<String,Double> candidateMap;
	protected Recorder recorder;
	protected FitnessFunction fitnessFunction;
	protected HashMap<String,Double> minimumPopulation;
	protected HashMap<String,Double> maximumPopulation;
	
	public Candidate(int i, FitnessFunction fitnessFunction, 
			HashMap<String,Double> minimumPopulation, 
			HashMap<String,Double> maximumPopulation){
		
		this.fitnessFunction = fitnessFunction;
		this.candidateMap = new HashMap<String, Double>();
		updateCandidateMapFromAST();
		this.updateName();
		
		this.createdAt = System.currentTimeMillis() - Tool.startTime;
		this.generation = i;
		this.fitness = 100000000.0;
		this.minimumPopulation = minimumPopulation;
		this.maximumPopulation = maximumPopulation;
		
	}
	
	public void nullOut(){
		this.fitnessFunction = null;
		this.minimumPopulation = null;
		this.maximumPopulation = null;
	}
	
	@Override
	public String toString(){
		return name + "; " + this.fitness + "; " + generation + "; " + createdAt ;
		
	}
	
	public void updateCreatedTime(){
		this.createdAt = System.currentTimeMillis() - Tool.startTime;
	}
	
	protected void updateName(){
		
		this.name = "";
		
		for(Map.Entry<String, Double> entry : candidateMap.entrySet()){
			name = name + entry.getKey() + "[" + entry.getValue() + "]"; 
		}
		
	}
	
	public int getGeneration(){
		return this.generation;
	}
	
	public void setGeneration(int i){
		this.generation = i;
	}
	
	public double getCreatedTime(){
		return createdAt;
	}
	
	@Override
	public int compare(Candidate arg0, Candidate arg1) {
		
		if(arg0.getFitness() < arg1.getFitness()){
			return -1;
		}
		if(arg0.getFitness() > arg1.getFitness()){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int compareTo(Candidate arg0) {
		
		if(this.fitness < arg0.getFitness()){
			return -1;
		}
		if(this.fitness > arg0.getFitness()){
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Candidate){
			Candidate candidate = (Candidate) obj;
			if(this.name.equals(candidate.getName())){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getFitness(){
		return this.fitness;
	}
	
	/**
	 * set the candidate map (used for copying)
	 */
	public abstract void setCandidateMap(HashMap<String,Double> candidateMap);
	
	/**
	 * get the candidate map from the AST
	 */
	public abstract void updateCandidateMapFromAST();
	
	/**
	 * 10% chance of keeping the original seed candidate elements in the population,
	 * otherwise scatter randomly around the population range.
	 */
	public abstract void scatter();
	
	public void updateFitness(){
		this.fitness = fitnessFunction.getFitness(candidateMap);
	}
	
	public abstract void mutate(double probability);
	
	public abstract void crossOver(Candidate candidate, double probability);
	
	public abstract void setVelocity(Candidate globalBestCandidate, 
			double originalVelocityWeight, 
			double personalBestVelocityWeight,
			double globalBestVelocityWeight);
	
	public abstract Candidate copySelf();
	
	public void setRecorder(Recorder recorder){
		this.recorder = recorder;
	}
	
	public HashMap<String,Double> getCandidateMap(){
		return this.candidateMap;
	}
	
	public void setFitness(Double fitness){
		this.fitness = fitness;
	}
}
