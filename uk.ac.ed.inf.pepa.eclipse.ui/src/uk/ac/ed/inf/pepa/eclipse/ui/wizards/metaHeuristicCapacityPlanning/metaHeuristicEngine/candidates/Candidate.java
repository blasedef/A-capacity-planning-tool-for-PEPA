package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.Map;

public abstract class Candidate implements Runnable {
	
	public abstract Double getTotalFitness();
	
	public abstract Map<String, Double> getPerformanceFitness();
	
	public abstract Double[] getPopulationFitness();
	
	public abstract void mutate();
	
	public abstract Candidate Crossover(Candidate candidate);
	
	public abstract void newVector();
	
	public abstract void initialise();

	public abstract void updateFitness();
	
}