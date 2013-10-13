package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;

public class Recorder {
	
	protected ArrayList<ArrayList<Candidate>> generation;
	protected PriorityQueue<Candidate> queue;
	protected HashMap<String,Double> candidateNameToFitnessHash;
	protected double time;
	
	public Recorder(){
		
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
		
		this.candidateNameToFitnessHash = new HashMap<String, Double>();
		
	}
	
	public void addNewCandidate(Candidate c, int generation){
		
		Candidate d = (Candidate) c.copySelf();
		d.setCandidateMap(Tool.copyHashMap(c.getCandidateMap()));
		d.setFitness(c.getFitness());
		d.setGeneration(generation);
		d.nullOut();
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
		
		this.candidateNameToFitnessHash.put(s, e);
		
	}
	
	public ArrayList<ArrayList<Candidate>> getGeneration(){
		return generation;
	}
	
	public PriorityQueue<Candidate> getQueue(){
		return queue;
	}
	
	public HashMap<String,Double> getCandidateMapToFitnessHash(){
		return candidateNameToFitnessHash;
	}

	public String thisGenerationsMix(int i){
		String output;
		
		HashMap<String,Double> names = new HashMap<String,Double>();
		
		ArrayList<Candidate> thisGeneration = this.generation.get(i);
		
		for(Candidate c : thisGeneration){
			if(names.containsKey(c.getName())){
				names.put(c.getName(), names.get(c.getName()) + 1);
			} else {
				names.put(c.getName(), 1.0);
			}
		}
		
		int generationSize = thisGeneration.size();

		output = "Generation;System Equation;percentage of current population;\n";
		
		for(String s : names.keySet().toArray(new String[0])){
			output += i + ";" + s + ";" + ((names.get(s)/generationSize) * 100) + "% \n"; 
		}
		
		return output;
	}
	
	public String getTopX(int x){
		String output;
		
		output = "";
		
		if(queue.size() < x)
			x = queue.size();
		
		output = "system equation ; fitness ; generation ; time after start created " ;
		
		for(int i = 0; i < x; i++){
			output = output + "\n" + queue.poll().toString();
		}
		
		output += "\n runtime ;" + this.time + "\n";
		
		return output;
	}

	public void stopTimer() {
		this.time = (System.currentTimeMillis() - this.time);
		
	}

	public void startTimer() {
		this.time = System.currentTimeMillis();
		
	}
	
}
