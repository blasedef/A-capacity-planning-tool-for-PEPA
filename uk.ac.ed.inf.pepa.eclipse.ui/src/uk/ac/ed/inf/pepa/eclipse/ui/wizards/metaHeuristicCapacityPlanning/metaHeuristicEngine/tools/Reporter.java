package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.SystemEquation;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Solutions;

public class Reporter {
	
	private double startTime;
	private double stopTime; 
	private double totalTime;
	private ArrayList<HashMap<String,Integer>> generationMixture;
	private HashMap<String,Double> totalFitness;
	
	public Reporter(){
		startTime = System.currentTimeMillis();
		totalTime = 0.0;
		generationMixture = new ArrayList<HashMap<String,Integer>>();
		totalFitness = new HashMap<String,Double>();
	}
	
	private void now(){
		stopTime = System.currentTimeMillis();
		totalTime = (stopTime - startTime);
	}
	
	private Double getRunTime(){
		now();
		return totalTime;
	}
	
	public String reportRunTime(){
		return "Run time " + getRunTime() + " milli seconds";
	}
	
	public Double createdTime(){
		return getRunTime();
	}
	
	public void reportSystemEquation(SystemEquation se){
		
		String[] keys = se.getMap().keySet().toArray(new String[0]);
		System.out.println("Created " + se.getCreatedTime() + " seconds after start");
		
		for(String s : keys){
			System.out.println(s + " " + se.getMap().get(s));
		}
		
	}

	public void addToGenerationMixture(Integer i, 
			Candidate c, 
			Double f) {
		
		if(this.generationMixture.size() == i + 1){
			if(this.generationMixture.get(i).containsKey(c.getAttributeString())){
				Integer j = this.generationMixture.get(i).get(c.getAttributeString());
				this.generationMixture.get(i).put(c.getAttributeString(), j++ );
			} else {
				this.generationMixture.get(i).put(c.getAttributeString(), 1);
			}
		}else {
			this.generationMixture.add(i,new HashMap<String,Integer>());
			this.generationMixture.get(i).put(c.getAttributeString(), 1);
		}
		
		this.totalFitness.put(c.getAttributeString(), f);
	}
	
	public void reportGenerationMix(Integer i){
		
		String[] options = this.generationMixture.get(i).keySet().toArray(new String[0]);
		Double size = ((Integer) options.length).doubleValue();
		
		for(String s : options){
			System.out.println(i + " : " + s 
					+ " : " 
					+ (((Integer) this.generationMixture.get(i).get(s)).doubleValue()/size)*100 
					+ "% :  "
					+ this.totalFitness.get(s));
		}
		
	}
	
	public HashMap<String,Double> getTotalPerformanceFitness(){
		return this.totalFitness;
	}

	public void reportSolutions(Solutions poll) {
		System.out.println(poll.summary());
		
	}
	
}