package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.Candidate;

public abstract class Metaheuristic {
	
	protected IProgressMonitor monitor;
	protected ArrayList<Candidate> candidatePopulation;
	protected int processors;
	
	public Metaheuristic (IProgressMonitor monitor){
		this.monitor = monitor;
		this.processors = Runtime.getRuntime().availableProcessors();
		if(this.processors > 1){
			this.processors = this.processors - 1;
		}
		this.candidatePopulation = new ArrayList<Candidate>();
	}
	
	public abstract void search();
	
}