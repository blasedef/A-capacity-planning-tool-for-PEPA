package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;

public class RecorderCandidate extends Candidate {
	
	public String name;
	public HashMap<String,Double> performanceMap;
	
	public RecorderCandidate(double fitness, String name, double createdAt, HashMap<String,Double> performanceMap){
		
		this.fitness = fitness;
		this.name = name;
		this.createdAt = createdAt;
		this.performanceMap = Tool.copyHashMap(performanceMap);
		updateHashCode();
		
	}

	@Override
	public Candidate copySelf() {
		return new RecorderCandidate(fitness,name,createdAt, performanceMap);
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
	
	public String toStringWithPerformance() {
		
		String temp = ",\"fitness\":" + this.fitness;
		
		for(Map.Entry<String, Double> entry : performanceMap.entrySet()){
			temp += ",\""+entry.getKey() + "\":" + entry.getValue(); 
		}
		
		if(performanceMap == null){
			return this.name;
		} else {
			return this.name + temp;
		}
	}
	
	public void setMyPerformanceMap(HashMap<String,Double> performanceMap){
		if(performanceMap != null){
			this.performanceMap = Tool.copyHashMap(performanceMap);
		} else {
			this.performanceMap = null;
		}
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
		return this.performanceMap;
	}

	@Override
	public void setPerformanceResultMap(HashMap<String, Double> map) {
		this.performanceMap = Tool.copyHashMap(map);
		
	}
	


}
