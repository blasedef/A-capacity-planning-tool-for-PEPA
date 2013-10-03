package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;


import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Reporter;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Tools;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.*;


public class GeneticAlgorithm extends Metaheuristic {
	
	Reporter reporter;
	

	public GeneticAlgorithm(IProgressMonitor monitor) {
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
				
			}	
			
			ArrayList<Candidate> newGeneration = new ArrayList<Candidate>();
			
			for(int j = 0; j < candidatePopulation.size()/2; j++){
			
				Candidate candidateA = tournamentSelection();
				Candidate candidateB = tournamentSelection();
			
				candidateA.Crossover(candidateB);
			
				candidateA.mutate(false);
				candidateB.mutate(false);
			
				newGeneration.add(candidateA);
				newGeneration.add(candidateB);
			}
			
			this.candidatePopulation = newGeneration;
			
			reporter.reportGenerationMix(i);
			
		}
		
		for(int i = 0; i < 10; i++){
			reporter.reportSolutions();
		}
		
		return Status.OK_STATUS;
		
	}
	
	public Candidate tournamentSelection(){
		
		int best = Tools.returnRandomInRange(0.0, ((Integer) candidatePopulation.size()).doubleValue() - 1, ExperimentConfiguration.INTEGER).intValue();
		int next = 0;
		
		for(int i = 0; i <= 2; i++){
			next = Tools.returnRandomInRange(0.0, ((Integer) candidatePopulation.size()).doubleValue() - 1, ExperimentConfiguration.INTEGER).intValue();
			if(candidatePopulation.get(next).compareTo(candidatePopulation.get(best)) < 0){
				best = next;
			}
		}
		
		return candidatePopulation.get(best);
	}
	
}