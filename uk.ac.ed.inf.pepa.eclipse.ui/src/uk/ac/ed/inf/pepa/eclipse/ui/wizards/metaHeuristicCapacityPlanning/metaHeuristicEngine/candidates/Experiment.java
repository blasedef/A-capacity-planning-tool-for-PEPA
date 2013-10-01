package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;



public class Experiment extends Candidate{
	
	private IProgressMonitor monitor;
	private int totalWork;
	private int processors;
	private int experiments;

	public Experiment(IProgressMonitor monitor, int totalWork, int processors, boolean candidate) {
		this.monitor = monitor;
		this.totalWork = totalWork;
		this.processors = processors;
	}
	
	public IStatus startExperiments(){
		
		try {
			this.monitor.beginTask("Experiment started", this.totalWork);
			
			this.experiments = ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.EXPERIMENTS_S).intValue();
			
			for(int i = 0; i < this.experiments; i++){
				
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				
				((Metaheuristic) this.getNewMetaheuristic(this.monitor, this.processors)).search();
				
			}
			
		} finally {
			this.monitor.done();
		}
		
		return Status.OK_STATUS;
	}
	
	public Metaheuristic getNewMetaheuristic(IProgressMonitor monitor, int processors){
		if(ExperimentConfiguration.metaHeuristic.getValue().equals(ExperimentConfiguration.HILLCLIMBING_S)){
			return new HillClimbing(monitor, processors);
		} else if (ExperimentConfiguration.metaHeuristic.getValue().equals(ExperimentConfiguration.GENETICALGORITHM_S)){
			return new GeneticAlgorithm(monitor, processors);
		} else {
			return new ParticleSwarmOptimisation(monitor, processors);
		}
	}

	@Override
	public Candidate Crossover(Candidate candidate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double getTotalFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> getPerformanceFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double[] getPopulationFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newVector() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFitness() {
		// TODO Auto-generated method stub
		
	}
	
}