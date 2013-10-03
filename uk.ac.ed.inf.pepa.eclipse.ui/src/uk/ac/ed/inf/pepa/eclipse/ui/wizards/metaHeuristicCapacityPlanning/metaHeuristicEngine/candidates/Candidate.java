package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.Map;

public abstract class Candidate {
	
	public abstract Double getTotalFitness();
	
	public abstract Map<String, Double> getPerformanceFitness();
	
	public abstract Map<String, Double> getPopulationFitness();
	
	public abstract void mutate();
	
	public abstract Candidate Crossover(Candidate candidate);
	
	public abstract void newVector();
	
	public abstract void initialise();

	public abstract void updateFitness();

	public abstract String getAttributeString();

	public abstract void setTotalPerformanceFitness();

	public abstract Double getTotalPerformanceFitness();

	public abstract void mutate(boolean isHillClimbing);

	public Double getTotalPopulationFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Double getCreatedTime();
		
	
}