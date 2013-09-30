package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.Map;

public abstract class Candidate {
	
	public abstract Map<String,Double> getFitness();
	
}