package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.SystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class SystemEquationCandidate extends Candidate {

	HashMap<String,Double> performanceResultsMap;
	
	public SystemEquationCandidate(int i,
			FitnessFunction fitnessFunction,
			HashMap<String,Double> minimumPopulation, 
			HashMap<String,Double> maximumPopulation){
		super();
		
		this.performanceResultsMap = new HashMap<String,Double>();
		this.fitnessFunction = fitnessFunction;
		this.candidateMap = new HashMap<String, Double>();
		updateCandidateMapFromAST();
		this.updateHashCode();
		this.createdAt = System.currentTimeMillis() - Tool.startTime;
		this.generation = i;
		this.fitness = 100000000.0;
		this.minimumPopulation = minimumPopulation;
		this.maximumPopulation = maximumPopulation;
	}
	
	public SystemEquationCandidate(){
		super();
	}
	
	public void updateHashCode(){
		
		String name = "";
		
		for(Map.Entry<String, Double> entry : candidateMap.entrySet()){
			name += entry.getKey() + ":" + entry.getValue() + ","; 
		}
		
		this.hashCode = name.hashCode();
		
	}
	
	@Override
	public String toString(){
		
		String temp = "";
		
		for(Entry<String, Double> entry : performanceResultsMap.entrySet()){
			temp += "\""+ entry.getKey() + "$\":" + entry.getValue() + ",";
		}
		
		
		return super.getName()  + 
		",\"fitness@\" : " + this.fitness + 
		"," + 
		"\"generation@\" : " + generation + 
		"," + 
		"\"created@\" : " + createdAt  + 
		"," + 
		temp;
		
	}
	
	public HashMap<String,Double> getPerformanceResultMap(){
		return this.performanceResultsMap;
	}
	
	@Override
	public void updateFitness(){
		this.fitness = fitnessFunction.getFitness(candidateMap);
		this.performanceResultsMap = ((SystemEquationFitnessFunction) this.fitnessFunction).getPerformanceResultsMap();
	}
	
	public void setPerformanceResultMap(HashMap<String,Double> performanceResultsMap){
		this.performanceResultsMap = performanceResultsMap;
	}

	@Override
	public void crossOver(Candidate candidate, double probability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new SystemEquationCandidate(this.generation,
				this.fitnessFunction.copySelf(),
				Tool.copyHashMap(minimumPopulation),
				Tool.copyHashMap(maximumPopulation));
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		((SystemEquationCandidate) temp).setPerformanceResultMap(performanceResultsMap);
		temp.updateCreatedTime();
		return temp;
	}
	
	@Override
	public void setCandidateMap(HashMap<String, Double> candidateMap) {
		this.candidateMap = candidateMap;
		
	}

	@Override
	public void updateCandidateMapFromAST() {
		candidateMap = ((SystemEquationFitnessFunction) this.fitnessFunction).getCandidate();
	}

	@Override
	public void mutate(double probability) {
		System.out.println("No mutate setup for this candidate");
		
	}

	@Override
	public void scatter() {
		//'10%' of user system equations make it into the original population
		if(true){ //Tool.rollDice(0.9)){
			for(Entry<String, Double> entry : candidateMap.entrySet()){
				
				Double min = minimumPopulation.get(entry.getKey()).doubleValue();
				Double max = maximumPopulation.get(entry.getKey()).doubleValue();
				
				Double d = Tool.returnRandomInRange(min, max, Config.NATURAL);
				candidateMap.put(entry.getKey(),d);
			}
		}
		
		
	}

	@Override
	public void setVelocity(Candidate globalBest,
			double originalVelocityWeight, double personalBestVelocityWeight,
			double globalBestVelocityWeight) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int compare(Candidate arg0, Candidate arg1) {
		
		if(arg0.getFitness() > arg1.getFitness()){
			return -1;
		}
		if(arg0.getFitness() < arg1.getFitness()){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int compareTo(Candidate arg0) {
		
		if(this.fitness > arg0.getFitness()){
			return -1;
		}
		if(this.fitness < arg0.getFitness()){
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Candidate){
			Candidate candidate = (Candidate) obj;
			if(super.getHashCode() == candidate.getHashCode()){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
}
