package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.Candidate;

public abstract class Metaheuristic {
	
	protected IProgressMonitor monitor;
	protected ArrayList<Candidate> candidatePopulation;
	protected int processors;
	public static ExecutorService executor;
	
	public Metaheuristic (IProgressMonitor monitor){
		this.monitor = monitor;
		this.processors = Runtime.getRuntime().availableProcessors();
		if(this.processors > 1){
			this.processors = this.processors - 1;
		}
		this.candidatePopulation = new ArrayList<Candidate>();
		Metaheuristic.executor = Executors.newFixedThreadPool(1);
	}
	
	public abstract void search();
	
}