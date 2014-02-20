package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.Path;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public abstract class Recorder {
	
	protected ArrayList<ArrayList<Candidate>> generation;
	protected PriorityQueue<Candidate> queue;
	protected double time;
	protected int queueSize;
	protected ConfigurationModel configurationModel;
	
	public Recorder(ConfigurationModel configurationModel){
		
		this.configurationModel = configurationModel;
		
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
	
	public abstract String getTopAsString();
	
	public abstract void finalise();
	
	public abstract void writeToDisk(Path resultsFolder, int generation);

	public abstract Candidate getTop();
	
	public abstract double getLastFinished();

}
