package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;


public class HillClimbing extends Metaheuristic {

	public HillClimbing(IProgressMonitor monitor, int processors) {
		super(monitor, processors);
		// TODO Auto-generated constructor stub
	}
	
	public void search(){
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S).intValue(); i++){
			this.candidatePopulation.add(new SystemEquation(monitor));
			
		}
		
		for(Candidate c : this.candidatePopulation){
			c.initialise();
			c.updateFitness();
		}
		
		for(Candidate c : this.candidatePopulation){
			executor.execute(c);
		}
		
		executor.shutdown();
		while(!executor.isTerminated()){
		}
		
	}
	
}