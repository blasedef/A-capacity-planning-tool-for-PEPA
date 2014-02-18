package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;

public abstract class Recorder {
	
	protected ArrayList<ArrayList<Candidate>> generation;
	protected PriorityQueue<Candidate> queue;
	protected double time;
	protected int queueSize;
	
	public Recorder(){
		
		this.queueSize = 100;
		
		this.queue = new PriorityQueue<Candidate>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean offer(Candidate e) {
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}

			@Override
			public boolean add(Candidate e) {
				
				
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
		};
		
		this.generation = new ArrayList<ArrayList<Candidate>>();
		
	}
	
	public abstract void addNewCandidate(Candidate c, int generation);
	
	public ArrayList<ArrayList<Candidate>> getGeneration(){
		return generation;
	}
	
	public PriorityQueue<Candidate> getQueue(){
		return queue;
	}

	public String thisGenerationsMix(int i){
		String output;
		
		HashMap<String,Double> names = new HashMap<String,Double>();
		HashMap<String,Double> fitnesses =  new HashMap<String,Double>();
		HashMap<String,Double> times =  new HashMap<String,Double>();
		
		ArrayList<Candidate> thisGeneration = this.generation.get(i);
		
		for(Candidate c : thisGeneration){
			if(names.containsKey(c.getName())){
				names.put(c.getName(), names.get(c.getName()) + 1);
				fitnesses.put(c.getName(), c.getFitness());
				times.put(c.getName(), c.getCreatedAt());
			} else {
				names.put(c.getName(), 1.0);
				fitnesses.put(c.getName(), c.getFitness());
				times.put(c.getName(), c.getCreatedAt());
			}
		}
		
		int generationSize = thisGeneration.size();

		output = "{\n";
		
		int y = 0;
		
		for(String s : names.keySet().toArray(new String[0])){
			output += "\"SystemEquation_" + y 
			+ "\":{" + s + ",\"percentage of current population\":" 
			+ ((names.get(s)/generationSize) * 100) 
			+ ",\"fitness\":" + fitnesses.get(s)
			+ ",\"created\":" + times.get(s)
			+ "}" + ",\n";
			y++;
		}
		
		output += "\"junk\":0";
		
		output += "},\n";
		
		return output;
	}
	
	public abstract String getTopX(int x);

//	public void stopTimer() {
//		this.time = (System.currentTimeMillis() - this.time);
//		
//	}
//
//	public void startTimer() {
//		this.time = System.currentTimeMillis();
//		
//	}
	
}
