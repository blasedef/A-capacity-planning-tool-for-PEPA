package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.ArrayList;
import java.util.PriorityQueue;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;

import org.eclipse.core.runtime.Path;

public class RecordManager {
	
	public ArrayList<Recorder> recorders;
	protected Path resultsFolder;
	private Double mean;
	private Double variance;
	private Double standardDeviation;
	private Double bestFitness;
	private String bestName;
	private Double totalMeanTimeTaken;
	private RecordParameters recordManagerParameters;
	protected PriorityQueue<Candidate> queue;
	protected int queueSize;
	private ArrayList<Candidate> finals;
	
	public RecordManager(RecordParameters recordManagerParameters){
		
		this.recorders = new ArrayList<Recorder>();
		this.setRecordManagerParameters(recordManagerParameters);
		this.resultsFolder = recordManagerParameters.getResultsFolder();
		this.queueSize = 10;
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
		
		this.finals = new ArrayList<Candidate>();
		
	}
	
	public void writeRecordersToDisk() {
		
		for(int i = 0; i < this.recorders.size(); i++){
			this.recorders.get(i).writeToDisk(i);
			
		}
		
	}

	public void add(Recorder recorder, int i) {
		this.recorders.add(i, recorder);
		
	}

	public void finaliseAll() {
		
		System.out.println("queue size @ finalise all: " + this.recorders.size());
		
		for(int i = 0; i < this.recorders.size(); i++){
			this.recorders.get(i).finalise();
		}
		
	}
	
	/*
	 * create an arraylist of the top ten results from previous experiment
	 * why? so we can then evaluate higher up the success of this lab
	 */
	public void finalise(){
		
		int x = queue.size();
		
		this.finals = new ArrayList<Candidate>();
		
		for(int i = 0; i < x; i++){
			this.finals.add(queue.poll());
		} 
		
		System.out.println("---v---");
		
		for(int i = 0; i < this.finals.size(); i++){
			System.out.println(finals.get(i).getName() + " " + finals.get(i).getFitness());
		}
		
		System.out.println("---v---");
		
	}

	/* get top result from experiments
	 * evaluate mean and standard dev
	 * creates a top ten queue of best results from experiments
	 */
	public void evaluateAll() {
		
		Candidate tempCandidate;
		double[] tempDiff = new double[this.recorders.size()];
		this.mean = 0.0;
		this.totalMeanTimeTaken = 0.0;
		this.bestFitness = 10000000.0;
		
		for(int i = 0; i < this.recorders.size(); i++){
			tempCandidate = this.recorders.get(i).getTop();
			this.queue.add(tempCandidate);
			if(this.queue.size() > this.queueSize){
				this.queue.poll();
			}
			double tempFitness = tempCandidate.getFitness();
			this.mean += tempFitness;
			this.totalMeanTimeTaken += this.recorders.get(i).getLastFinished();
			tempDiff[i] = tempFitness;
			if(tempFitness < this.bestFitness){
				this.bestFitness = tempFitness;
				this.bestName = tempCandidate.getName();
			}
		}
		
		this.mean = this.mean/this.recorders.size();
		this.totalMeanTimeTaken = this.totalMeanTimeTaken/this.recorders.size();
		
		for(int i = 0; i < tempDiff.length; i++){
			tempDiff[i] = Math.pow((tempDiff[i] - this.mean),2);
		}
		
		this.variance = 0.0;
		
		for(int i = 0; i < tempDiff.length; i++){
			this.variance += tempDiff[i];
		}
		
		this.variance = this.variance/this.recorders.size();
		
		this.standardDeviation = Math.pow(this.variance,0.5);
		
		System.out.println("queue size @ evaluate all: " + queue.size());
		
		this.finalise();
		
	}
	
	public void evaluateSystemEquationAll(){
		
	}

	/**
	 * aka fitness
	 */
	public void outputResults() {
//		for(int i = 0; i < this.finals.size(); i++){
//			System.out.println(this.finals.get(i).getName() + "@" + this.finals.get(i).getFitness());
//		}
		System.out.println("------");
		System.out.println("top:");
		System.out.println(this.bestFitness);
		System.out.println(this.bestName);
		System.out.println("------");
		System.out.println("mean/standard dev:");
		System.out.println(this.mean);
		System.out.println(this.standardDeviation);
		System.out.println("response time:");
		System.out.println(this.totalMeanTimeTaken);
		System.out.println("------");
		
	}
	
	public Double[] getResults(){
		Double[] temp = {this.mean,this.standardDeviation,this.totalMeanTimeTaken};
		return temp;
	}
	
	public ArrayList<Candidate> getTop(){
		return this.finals;
	}

	public void setRecordManagerParameters(RecordParameters recordManagerParameters) {
		this.recordManagerParameters = recordManagerParameters;
	}

	public RecordParameters getRecordManagerParameters() {
		return recordManagerParameters;
	}

}
