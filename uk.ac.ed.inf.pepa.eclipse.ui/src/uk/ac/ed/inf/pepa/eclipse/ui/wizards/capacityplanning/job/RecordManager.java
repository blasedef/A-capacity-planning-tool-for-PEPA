package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.ArrayList;
import java.util.PriorityQueue;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.SystemEquationCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

import org.eclipse.core.runtime.Path;

public class RecordManager {
	
	public ArrayList<Recorder> recorders;
	protected ConfigurationModel configurationModel;
	protected Path resultsFolder;
	private Double mean;
	private Double variance;
	private Double standardDeviation;
	private Double bestFitness;
	private Double totalMeanTimeTaken;
	protected PriorityQueue<Candidate> queue;
	protected int queueSize;
	private ArrayList<Candidate> finals;
	
	public RecordManager(ConfigurationModel configurationModel, Path resultsFolder){
		
		this.recorders = new ArrayList<Recorder>();
		this.configurationModel = configurationModel;
		this.resultsFolder = resultsFolder;
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
			this.recorders.get(i).writeToDisk(resultsFolder, i);
			
		}
		
	}

	public void add(Recorder recorder, int i) {
		this.recorders.add(i, recorder);
		
	}

	public void finaliseAll() {
		for(int i = 0; i < this.recorders.size(); i++){
			this.recorders.get(i).finalise();
		}
		
	}
	
	public void finalise(){
		
		int x = queue.size();
		
		for(int i = 0; i < x; i++){
			this.finals.add((SystemEquationCandidate) queue.poll());
		} 
		
	}

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
		
		this.finalise();
		
	}

	/**
	 * aka fitness
	 */
	public void outputResults() {
		for(int i = 0; i < this.finals.size(); i++){
			System.out.println(this.finals.get(i).getName() + "@" + this.finals.get(i).getFitness());
		}
		System.out.println(this.mean);
		System.out.println(this.standardDeviation);
		System.out.println(this.totalMeanTimeTaken);
		
		
	}

}
