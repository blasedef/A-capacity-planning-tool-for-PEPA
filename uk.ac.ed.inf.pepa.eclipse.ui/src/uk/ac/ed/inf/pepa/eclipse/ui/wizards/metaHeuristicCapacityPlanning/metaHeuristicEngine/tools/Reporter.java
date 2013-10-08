package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.SystemEquation;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Solutions;

public class Reporter {
	
	private double startTime;
	private double stopTime; 
	private double totalTime;
	private int generation;
	private ArrayList<HashMap<String,Integer>> generationMixture;
	private HashMap<String,Double> totalFitness;
	private int convergence;
	PriorityQueue<Solutions> queue = new PriorityQueue<Solutions>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean offer(Solutions e) {
			if(this.contains(e)){
				return false;
			} else {
				return super.offer(e);
			}
		}

		@Override
		public boolean add(Solutions e) {
			
			
			if(this.contains(e)){
				return false;
			} else {
				return super.offer(e);
			}
		}
		
		
	};

	
	public Reporter(){
		startTime = System.currentTimeMillis();
		totalTime = 0.0;
		generationMixture = new ArrayList<HashMap<String,Integer>>();
		totalFitness = new HashMap<String,Double>();
		convergence = 0;
		generation = 0;
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

	public void addToGenerationMixture(Integer j, 
			Candidate c) {
		
		/* tidy this up */
		
		int i;
		
		if(j < 0){
			i = this.generation;
		} else {
			this.generation = j;
			i = this.generation;
		}
		
		queue.add(new Solutions(c.getAttributeString(), 
				c.getTotalFitness(), 
				c.getTotalPerformanceFitness(), 
				c.getTotalPopulationFitness(),
				c.getNewMap(c.getPerformanceFitness()),
				c.getNewMap(c.getPopulationFitness()),
				createdTime(),
				i));
		
		System.out.println(c.getTotalFitness());
		
		if(this.generationMixture.size() == i + 1){
			if(this.generationMixture.get(i).containsKey(c.getAttributeString())){
				Integer k = this.generationMixture.get(i).get(c.getAttributeString());
				this.generationMixture.get(i).put(c.getAttributeString(), k++ );
			} else {
				this.generationMixture.get(i).put(c.getAttributeString(), 1);
			}
		}else {
			this.generationMixture.add(i,new HashMap<String,Integer>());
			this.generationMixture.get(i).put(c.getAttributeString(), 1);
		}
		
		this.totalFitness.put(c.getAttributeString(), c.getTotalFitness());
	}
	
	public void reportGenerationMix(Integer i){
		
		String[] options = this.generationMixture.get(i).keySet().toArray(new String[0]);
		Double size = ((Integer) options.length).doubleValue();
		
		for(String s : options){
			System.out.println(i + " : " + s 
					+ " : " 
					+ (this.generationMixture.get(i).get(s).doubleValue())
					+ "% :  "
					+ this.totalFitness.get(s));
		}
		
	}
	
	public HashMap<String,Double> getTotalFitness(){
		return this.totalFitness;
	}

	public void reportSolutions() {
		if(!queue.isEmpty()){
			System.out.println(queue.poll().summary());
		}
		
	}
	
	public boolean hasConverged(int i){
		if (this.generationMixture.get(i).size() == 1){
			this.convergence++;
		} else {
			if(this.convergence > 0) {
				this.convergence--;
			} 
		}
		return (this.convergence > 3);
	}
	
}