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
		
		IStatus status;
		
		createPopulation();
		
		System.out.println("Generation : System Equation : Population % of generation : Total fitness");
		
		status = cycleGenerations();
		
		for(int i = 0; i < 10; i++){
			reporter.reportSolutions();
		}
		
		return status;
		
	}
	
	public void createPopulation(){
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S).intValue(); i++){
			
			candidatePopulation.add(new SystemEquation(monitor, this.reporter));
			
		}
		
	}
	
	public IStatus cycleGenerations(){
		
		IStatus status = Status.OK_STATUS;
		
		for(int i = 0; i < ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.GENERATION_S).intValue(); i++){
			
			status = updateFitness();	
			
			tournamentCrossMutate(i);
			
			reporter.reportGenerationMix(i);
			
			if(reporter.hasConverged(i))
				break;
			
		}
		
		return status;
	}
	
	public void tournamentCrossMutate(int i) {
		
		ArrayList<Candidate> newGeneration = new ArrayList<Candidate>();
		
		for(int j = 0; j < candidatePopulation.size()/2; j++){
		
			Candidate candidateA = tournamentSelection();
			Candidate candidateB = tournamentSelection();
		
			//candidateA.Crossover(candidateB);
		
			candidateA.mutate(false);
			candidateB.mutate(false);
			
		
			newGeneration.add(candidateA);
			newGeneration.add(candidateB);
			
			this.reporter.addToGenerationMixture(i,candidateA);
			this.reporter.addToGenerationMixture(i,candidateB);
		}
		
		this.candidatePopulation = newGeneration;
		
	}
	
	public IStatus updateFitness(){
		
		for(Candidate c : candidatePopulation){
			
			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			
			c.updateFitness();
			
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