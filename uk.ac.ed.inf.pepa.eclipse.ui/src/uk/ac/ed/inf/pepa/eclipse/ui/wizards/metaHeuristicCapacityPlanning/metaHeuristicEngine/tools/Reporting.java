package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.SystemEquation;

public class Reporting {
	
	private static double startTime;
	private static double stopTime; 
	private static double totalTime;
	
	public Reporting(){
		Reporting.startTime = System.currentTimeMillis();
		Reporting.totalTime = 0.0;
	}
	
	private static void now(){
		stopTime = System.currentTimeMillis();
		totalTime = (stopTime - startTime)/1000;
	}
	
	private static Double getRunTime(){
		now();
		return totalTime;
	}
	
	public String reportRunTime(){
		return "Run time " + getRunTime() + " seconds";
	}
	
	public static Double createdTime(){
		return getRunTime();
	}
	
	public static void reportSystemEquation(SystemEquation se){
		
		String[] keys = se.getMap().keySet().toArray(new String[0]);
		System.out.println("Created " + se.getCreated() + " seconds after start");
		
		for(String s : keys){
			System.out.println(s + " " + se.getMap().get(s));
		}
		
	}
	
}