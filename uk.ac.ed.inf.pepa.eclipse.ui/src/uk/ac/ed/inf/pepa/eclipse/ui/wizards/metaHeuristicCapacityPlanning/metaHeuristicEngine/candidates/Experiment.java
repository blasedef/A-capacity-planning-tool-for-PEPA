package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;



public class Experiment extends Candidate{
	
	private IProgressMonitor monitor;
	private int totalWork;
	private int experiments;
	private IStatus status;

	public Experiment(IProgressMonitor monitor, int totalWork, boolean candidate) {
		this.monitor = monitor;
		this.totalWork = totalWork;
	}
	
	public IStatus startExperiments(){
		
		try {
			this.monitor.beginTask("Experiment started", this.totalWork);
			
			this.experiments = ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.EXPERIMENTS_S).intValue();
			
			for(int i = 0; i < this.experiments; i++){
				
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				
				this.status = ((Metaheuristic) this.getNewMetaheuristic(this.monitor)).search();
				
			}
			
		} finally {
			this.monitor.done();
		}
		
		return this.status;
	}
	
	public Metaheuristic getNewMetaheuristic(IProgressMonitor monitor){
		if(ExperimentConfiguration.metaHeuristic.getValue().equals(ExperimentConfiguration.HILLCLIMBING_S)){
			return new HillClimbing(monitor);
		} else if (ExperimentConfiguration.metaHeuristic.getValue().equals(ExperimentConfiguration.GENETICALGORITHM_S)){
			return new GeneticAlgorithm(monitor);
		} else {
			return new ParticleSwarmOptimisation(monitor);
		}
	}

	@Override
	public void Crossover(Candidate candidate) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getAttributeString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getCreatedTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Double> getPopulationFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTotalFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTotalPerformanceFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mutate(boolean isHillClimbing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newVector() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTotalPerformanceFitness() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFitness() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compare(Candidate arg0, Candidate arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Candidate arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HashMap<String, Double> getPerformanceFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}