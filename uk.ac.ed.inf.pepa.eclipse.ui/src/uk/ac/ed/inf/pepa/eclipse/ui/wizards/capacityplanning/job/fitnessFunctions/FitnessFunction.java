package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;

public abstract class FitnessFunction {
	
	protected Double fitness;
	
	public abstract Double getFitness(HashMap<String,Double> candidate);
	
	public abstract FitnessFunction copySelf();
	
}
