package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.SystemEquationCandidate;

public class SystemEquationRecorder extends Recorder {
	
	protected HashMap<String,Double> candidateNameToFitnessHash;
	private HashMap<String,HashMap<String,Double>> nameToPerformanceResultsMapHash;
	
	public SystemEquationRecorder(){
		nameToPerformanceResultsMapHash = new HashMap<String,HashMap<String,Double>>();
		this.candidateNameToFitnessHash = new HashMap<String, Double>();
	}
	
	@Override
	public void addNewCandidate(Candidate c, int generation){
		
		Candidate d = (Candidate) c.copySelf();
		d.setCandidateMap(Tool.copyHashMap(c.getCandidateMap()));
		d.nullOut();
		d.setFitness(c.getFitness());
		d.setGeneration(generation);
		d.resetCreatedAt();
	
		if(this.queue.size() > this.queueSize){
			this.queue.poll();
		}
		this.queue.add(d);
		
		
		if(this.generation.size() <= generation){
			ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
			candidateList.add(d);
			this.generation.add(candidateList);
		} else {
			this.generation.get(((Integer) generation)).add(d);
		}
		
		String s = c.getName();
		Double e = c.getFitness();
		HashMap<String,Double> pm = ((SystemEquationCandidate) c).getPerformanceResultMap();
		
		this.candidateNameToFitnessHash.put(s, e);
		this.nameToPerformanceResultsMapHash.put(s, pm);
		
	}
	
	
	@Override
	public String getTopX(int x){
		String output;
		
		output = "";
		
		if(queue.size() < x)
			x = queue.size();
		
		//output = "system equation ; fitness ; generation ; time after start created ; performanceMap " ;
		
		for(int i = 0; i < x; i++){
			output += "\"rank_" + (x - i) + "\":{" + queue.poll().toString() + "\"junk\":0},\n";
		}
		
		output += "\"junk\":0";
		
		//output += "\n runtime ;" + this.time + "\n";
		
		return output;
	}
	
	public HashMap<String,Double> getCandidateMapToFitnessHash(){
		return candidateNameToFitnessHash;
	}
	
	public HashMap<String, HashMap<String, Double>> getNameToPerformanceResultsMapHash() {
		return nameToPerformanceResultsMapHash;
	}

}
