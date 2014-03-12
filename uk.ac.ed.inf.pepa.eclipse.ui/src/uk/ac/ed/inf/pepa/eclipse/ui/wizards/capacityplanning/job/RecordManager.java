package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.RecorderCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Results;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

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
	private HashMap<String,Double> bestPerformanceMap;
	private RecordParameters recordManagerParameters;
	protected PriorityQueue<Candidate> queue;
	protected int queueSize;
	private ArrayList<Candidate> finals;
	
	public RecordManager(RecordParameters recordManagerParameters){
		
		this.recorders = new ArrayList<Recorder>();
		this.bestPerformanceMap = new HashMap<String,Double>();
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
		
		for(int i = 0; i < this.recorders.size(); i++){
			this.recorders.get(i).finalise();
		}
		
	}
	
	/*
	 * create an arraylist of the top ten results from previous experiment
	 * why? so we can then evaluate higher up the success of this lab
	 */
	public void finalise(){
		
		for(int i = 0; i < finals.size(); i++){
			this.queue.add((Candidate) new RecorderCandidate(finals.get(i).getFitness(),
					finals.get(i).getName(),
					((Candidate) finals.get(i)).getCreatedAt(),
					((Candidate) this.recorders.get(i).getTop()).getPerformanceResultMap()));
		}
		
		int x = queue.size();
		
		this.finals = new ArrayList<Candidate>();
		
		for(int i = 0; i < x; i++){
			this.finals.add(queue.poll());
		} 
		
	}

	/* get top result from experiments
	 * evaluate mean and standard dev
	 * creates a top ten queue of best results from experiments
	 */
	public void evaluateAll() {
		
		double[] tempDiff = new double[this.recorders.size()];
		this.mean = 0.0;
		this.totalMeanTimeTaken = 0.0;
		this.bestFitness = 10000000.0;
		
		
		for(int i = 0; i < this.recorders.size(); i++){
			double tempFitness = this.recorders.get(i).getTop().getFitness();
			this.mean += tempFitness;
			this.totalMeanTimeTaken += this.recorders.get(i).getLastFinished();
			tempDiff[i] = tempFitness;
			if(tempFitness < this.bestFitness){
				this.bestFitness = tempFitness;
				this.bestName = this.recorders.get(i).getTop().getName();
				this.bestPerformanceMap = Tool.copyHashMap(((RecorderCandidate) this.recorders.get(i).getTop()).getPerformanceResultMap());
			}
			
			this.queue.add((Candidate) new RecorderCandidate(this.recorders.get(i).getTop().getFitness(),
					this.recorders.get(i).getTop().getName(),
					((Candidate) this.recorders.get(i).getTop()).getCreatedAt(),
					((Candidate) this.recorders.get(i).getTop()).getPerformanceResultMap()));
			
			if(this.queue.size() > this.queueSize){
				this.queue.poll();
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
	 * System.out version
	 */
	public void outputResults() {
		for(int i = 0; i < this.finals.size(); i++){
			System.out.println(this.finals.get(i).getName() + "@" + this.finals.get(i).getFitness());
		}
		System.out.println("------");
		System.out.println("top:");
		System.out.println(this.bestFitness);
		System.out.println(this.bestName);
		System.out.println("------");
		System.out.println("mean: ");
		System.out.println(this.mean);
		System.out.println("Standard deviation: ");
		System.out.println(this.standardDeviation);
		System.out.println("response time:");
		System.out.println(this.totalMeanTimeTaken);
		System.out.println("------");
		
	}
	
	/**
	 * viewPart version
	 */
	public ArrayList<Results> outputResultsForView() {

		ArrayList<Results> temp = new ArrayList<Results>();
		
		temp.add(new Results(Config.VTOPFITNESS,this.bestFitness.toString()+",\n"));
		if(this.bestName.contains("@")){
			String[] tempStringArray = this.bestName.toString().split("@");
			temp.add(new Results(Config.VOVERALLFITTEST + "{",tempStringArray[1] + "},\n"));
			temp.add(new Results(Config.VOVERALLFITTESTL+ "{",tempStringArray[0] + "},\n"));
			temp.add(new Results("\"Best system equation fitness\":" + "{",tempStringArray[2] + "},\n"));
		} else {
			temp.add(new Results(Config.VOVERALLFITTEST + "{",this.bestName.toString()+ "},\n"));
		}
		
		String tempString = "";
		for(Map.Entry<String, Double> entry : bestPerformanceMap.entrySet()){
			tempString += "\""+entry.getKey() + "\":" + entry.getValue() + ","; 
		}
		
		tempString = tempString.substring(0,tempString.length() - 1);
		
		temp.add(new Results(Config.VLABBESTPERFORMANCE + "{", tempString + "},\n"));
		if(!this.totalMeanTimeTaken.toString().equals("0.0")){
			temp.add(new Results(Config.VLABMEANFITNESS, this.mean.toString() + ",\n"));
			temp.add(new Results(Config.VSTANDARDDEV, this.standardDeviation.toString() + ",\n"));
			temp.add(new Results(Config.VLABMEANRESPONSETIME,this.totalMeanTimeTaken.toString() + ",\n"));
		}
		
		temp.add(new Results("\"Top ten results\":{\n",""));
		
		for(int i = this.finals.size() - 1; i >= 1 ; i--){
			if(i == this.finals.size() - 1){
				temp.add(new Results("\"1st best experiment\":","{"));
			} else if ( i == this.finals.size() - 2){
				temp.add(new Results("\"2nd best experiment\":","{"));
			} else if ( i == this.finals.size() - 3){
				temp.add(new Results("\"3rd best experiment\":","{"));
			} else {
				temp.add(new Results("\"" + (this.finals.size() - i) +"th best experiment\":","{"));
			}
			if(this.finals.get(i).getName().contains("@")){
				String[] tempStringArray = this.finals.get(i).getName().split("@");
				temp.add(new Results("\"system equation\":{",tempStringArray[1] + "},\n"));
				temp.add(new Results("\"lab candidate\":{",tempStringArray[0] + "},\n"));
			} else {
				temp.add(new Results("\"system equation\":{",this.finals.get(i).getName() + "},\n"));
			}
			
			temp.add(new Results("\"fitness\":" , "" + this.finals.get(i).getFitness() + ",\n"));
			
			tempString = "";
			for(Map.Entry<String, Double> entry : this.finals.get(i).getPerformanceResultMap().entrySet()){
				tempString += "\""+entry.getKey() + "\":" + entry.getValue() + ","; 
			}
			
			tempString = tempString.substring(0,tempString.length() - 1);
			
			temp.add(new Results("\"performance\":{" , "" + tempString + "}"));
			
			temp.add(new Results("","},\n"));
		}
		
		temp.add(new Results("\"" + (this.finals.size()) +"th best experiment\":","{"));
		
		if(this.finals.get(0).getName().contains("@")){
			String[] tempStringArray = this.finals.get(0).getName().split("@");
			temp.add(new Results("\"system equation\":{",tempStringArray[1] + "},\n"));
			temp.add(new Results("\"lab candidate\":{",tempStringArray[0] + "},\n"));
		} else {
			temp.add(new Results("\"system equation\":{",this.finals.get(0).getName() + "},\n"));
		}
		
		temp.add(new Results("\"fitness\":" , "" + this.finals.get(0).getFitness() + ",\n"));
		
		tempString = "";
		for(Map.Entry<String, Double> entry : this.finals.get(0).getPerformanceResultMap().entrySet()){
			tempString += "\""+entry.getKey() + "\":" + entry.getValue() + ","; 
		}
		
		tempString = tempString.substring(0,tempString.length() - 1);
		
		temp.add(new Results("\"performance\":{" , "" + tempString + "}"));
		
		temp.add(new Results("","}\n"));
		
		temp.add(new Results("","}"));
		
		temp.add(new Results("","}"));
		
		return temp;
		
	}
	
	public Double[] getResults(){
		Double[] temp = {this.mean,this.standardDeviation,this.totalMeanTimeTaken,this.bestFitness};
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
