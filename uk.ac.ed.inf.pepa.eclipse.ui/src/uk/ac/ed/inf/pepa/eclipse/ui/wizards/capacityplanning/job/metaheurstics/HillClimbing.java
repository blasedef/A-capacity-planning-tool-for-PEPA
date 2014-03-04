package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class HillClimbing extends Metaheuristic {
	
	protected Double mutationProbability;

	public HillClimbing(MetaHeuristicParameters metaheuristicParameters, 
			Candidate candidate, 
			IProgressMonitor monitor, 
			Recorder recorder) {
		super(metaheuristicParameters, 
				candidate, 
				monitor, 
				recorder);
		
		this.mutationProbability = myParameters.getParameters().get(Config.MUTATIONPROBABILITY_S);
	}

	@Override
	public IStatus search() {
		
		//recorder.startTimer();
		
		for(Candidate c : candidatePopulation){
			c.updateFitness();
			recorder.addNewCandidate(c, 0);
			
		}
		
		for(int i = 1; i < generationSize; i++){
		
			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			
			for(Candidate c : candidatePopulation){
				
				Candidate d;
				d = c.copySelf();
				c.mutate(this.mutationProbability);
				c.updateFitness();
				
				if(c.getFitness() >= d.getFitness()){
					c = d;
				}
				recorder.addNewCandidate(c, i);
			}
			
		
		}
		
		//recorder.stopTimer();
		
		
		return Status.OK_STATUS;
	}


	

}
