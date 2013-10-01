package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.SystemEquation;

public abstract class Metaheuristic {
	
	protected IProgressMonitor monitor;
	protected ArrayList<Candidate> candidatePopulation;
	protected int processors;
	public static ExecutorService executor;
	
	public Metaheuristic (IProgressMonitor monitor, int processors){
		this.monitor = monitor;
		this.processors = processors;
		Metaheuristic.executor = Executors.newFixedThreadPool(this.processors - 1);
	}
	
	public abstract void search();
	
}