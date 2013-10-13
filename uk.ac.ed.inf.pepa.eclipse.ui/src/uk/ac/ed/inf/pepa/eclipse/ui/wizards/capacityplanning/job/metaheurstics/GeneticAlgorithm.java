package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;


public class GeneticAlgorithm extends Metaheuristic {
	
	protected Double mutationProbability;
	protected Double crossOverProbability;

	public GeneticAlgorithm(HashMap<String, Double> parameters,
			Candidate candidate, IProgressMonitor monitor, Recorder recorder) {
		super(parameters, candidate, monitor, recorder);
		
		this.mutationProbability = myParameters.get(Config.MUTATIONPROBABILITY_S);
		this.crossOverProbability = myParameters.get(Config.CROSSOVERPROBABILITY_S);		
	}

	@Override
	public IStatus search() {
		
		recorder.startTimer();
		
		for(Candidate c : candidatePopulation){
			c.updateFitness();
			recorder.addNewCandidate(c, 0);
			
		}
		
		
		for(int i = 1; i < generationSize; i++){
			
			ArrayList<Candidate> newGeneration = new ArrayList<Candidate>();
			
			for(int j = 0; j < candidatePopulation.size()/2; j++){
				
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				
				Candidate candidateA = tournamentSelection().copySelf();
				Candidate candidateB = tournamentSelection().copySelf();
				
				candidateA.crossOver(candidateB, crossOverProbability);
				
				candidateA.mutate(mutationProbability);
				candidateB.mutate(mutationProbability);
				
				candidateA.updateFitness();
				candidateB.updateFitness();
				
				recorder.addNewCandidate(candidateA, i);
				recorder.addNewCandidate(candidateB, i);
				
				newGeneration.add(candidateA);
				newGeneration.add(candidateB);
				
				
			}
			
			this.candidatePopulation = newGeneration;
			
		}
		
		recorder.stopTimer();
		
		return Status.OK_STATUS;
		
	}
	
	public Candidate tournamentSelection(){

		int best = Tool.returnRandomInRange(0.0, ((Integer) candidatePopulation.size()).doubleValue() - 1, Config.INTEGER).intValue();
		int next = 0;

		for(int i = 0; i <= 2; i++){
			next = Tool.returnRandomInRange(0.0, ((Integer) candidatePopulation.size()).doubleValue() - 1, Config.INTEGER).intValue();
			if(candidatePopulation.get(next).compareTo(candidatePopulation.get(best)) < 0){
				best = next;
			}
		}

		return candidatePopulation.get(best);
	}

}
