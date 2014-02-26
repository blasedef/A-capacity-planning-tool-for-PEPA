package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;

public class RecorderCandidate extends Candidate {
	
	public String name;
	
	public RecorderCandidate(double fitness, String name, double createdAt){
		
		this.fitness = fitness;
		this.name = name;
		this.createdAt = createdAt;
		updateHashCode();
		
	}

	@Override
	public Candidate copySelf() {
		return new RecorderCandidate(fitness,name,createdAt);
	}

	@Override
	public void crossOver(Candidate candidate, double probability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mutate(double probability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scatter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCandidateMap(HashMap<String,Double> map){
		this.candidateMap = Tool.copyHashMap(map);
	}

	@Override
	public void setVelocity(Candidate globalBestCandidate,
			double originalVelocityWeight, double personalBestVelocityWeight,
			double globalBestVelocityWeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public void updateCandidateMapFromAST() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHashCode() {
		this.hashCode = this.name.hashCode();
		
	}
	
	public void setGeneration(int i){
		this.generation = i;
	}
	
	public String getName(){
		return this.name;
	}

	public HashMap<String, Double> getPerformanceResultMap() {
		return this.candidateMap;
	}
	


}
