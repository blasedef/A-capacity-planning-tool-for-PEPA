package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Reporter;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.*;


public class HillClimbing extends Metaheuristic {
	
	Reporter reporter;
	

	public HillClimbing(IProgressMonitor monitor) {
		super(monitor);
		reporter = new Reporter();
	}
	
	public IStatus search(){
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S).intValue(); i++){
			
			candidatePopulation.add(new SystemEquation(monitor, this.reporter));
			
		}
		
		for(Candidate c : candidatePopulation){
			c.initialise();
		}
		
		System.out.println("Generation : System Equation : Population % of generation : Total fitness");
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.GENERATION_S).intValue(); i++){
			
			
			for(Candidate c : candidatePopulation){
				
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				
				c.updateFitness();
				
				this.reporter.addToGenerationMixture(i,c,c.getTotalFitness());
				
				c.mutate(true);
			}	
			
			reporter.reportGenerationMix(i);
			
		}
		
		for(int i = 0; i < 10; i++){
			reporter.reportSolutions();
		}
		
		return Status.OK_STATUS;
		
	}
	
}