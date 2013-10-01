package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;


public class HillClimbing extends Metaheuristic {

	public HillClimbing(IProgressMonitor monitor) {
		super(monitor);
		// TODO Auto-generated constructor stub
	}
	
	public void search(){
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S).intValue(); i++){
			
			candidatePopulation.add(new SystemEquation(monitor));
			
		}
		
		for(Candidate c : candidatePopulation){
			c.initialise();
		}
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.GENERATION_S).intValue(); i++){
			
			for(Candidate c : candidatePopulation){
				executor.execute(c);
			}	
			
		}
		
		executor.shutdown();
		while(!executor.isTerminated()){
		}
		
	}
	
}