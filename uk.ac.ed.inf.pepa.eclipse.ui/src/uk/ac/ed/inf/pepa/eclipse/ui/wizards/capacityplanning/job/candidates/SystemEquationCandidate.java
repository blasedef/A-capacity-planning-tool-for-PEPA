package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
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
		super(i, fitnessFunction, minimumPopulation, maximumPopulation);
		
		this.performanceResultsMap = new HashMap<String,Double>();
	}
	
	@Override
	public String toString(){
		
		String temp = "";
		
		for(Entry<String, Double> entry : performanceResultsMap.entrySet()){
			temp += entry.getKey() + ";" + entry.getValue() + ";";
		}
		
		
		return name + 
		/* ";" + 
		this.fitness + 
		";" + 
		generation + 
		";" + 
		createdAt  + 
		";" + */
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
		temp.updateName();
		return temp;
	}
	
	@Override
	public void setCandidateMap(HashMap<String, Double> candidateMap) {
		this.candidateMap = candidateMap;
		this.updateName();
		
	}

	@Override
	public void updateCandidateMapFromAST() {
		candidateMap = ((SystemEquationFitnessFunction) this.fitnessFunction).getCandidate();
		updateName();
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
		
		updateName();
		
	}

	@Override
	public void setVelocity(Candidate globalBest,
			double originalVelocityWeight, double personalBestVelocityWeight,
			double globalBestVelocityWeight) {
		// TODO Auto-generated method stub
		
	}
}
