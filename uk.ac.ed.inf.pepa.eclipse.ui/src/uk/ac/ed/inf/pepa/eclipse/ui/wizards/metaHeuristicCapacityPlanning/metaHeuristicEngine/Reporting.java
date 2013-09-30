package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.SystemEquation;

public class Reporting {
	
	private double startTime;
	private double stopTime; 
	private double totalTime;
	
	public Reporting(){
		this.startTime = System.currentTimeMillis();
		this.totalTime = 0.0;
	}
	
	private void now(){
		this.stopTime = System.currentTimeMillis();
		this.totalTime = (this.stopTime - this.startTime)/1000;
	}
	
	private Double getRunTime(){
		this.now();
		return this.totalTime;
	}
	
	public String reportRunTime(){
		return "Run time " + getRunTime() + " seconds";
	}
	
	public static void reportSystemEquation(SystemEquation s){
		//TODO
	}
	
}