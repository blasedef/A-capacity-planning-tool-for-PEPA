package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Candidate implements Comparator<Candidate>, Comparable<Candidate> {
	
	public abstract Double getTotalFitness();
	
	public abstract HashMap<String, Double> getPerformanceFitness();
	
	public abstract HashMap<String, Double> getPopulationFitness();
	
	public abstract void Crossover(Candidate candidate);
	
	public abstract void newVector();
	
	public abstract void initialise();

	public abstract void updateFitness();

	public abstract String getAttributeString();

	public abstract void setTotalPerformanceFitness();

	public abstract Double getTotalPerformanceFitness();

	public Double getTotalPopulationFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Double getCreatedTime();
	
	public abstract int compare(Candidate arg0, Candidate arg1);
	
	public abstract int compareTo(Candidate arg0);

	public abstract void mutate(boolean isHillClimbing);
	
	public HashMap<String, Double> getNewMap(HashMap<String, Double> map){
		
		HashMap<String, Double> newMap = new HashMap<String, Double>();
		
		for(Map.Entry<String, Double> entry : map.entrySet()){
			newMap.put(entry.getKey(), entry.getValue());
		}
		
		return newMap;
		
	}

	
}