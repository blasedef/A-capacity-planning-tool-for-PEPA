package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.ParticleSwarmOptimisationCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class ParticleSwarmOptimisation extends Metaheuristic {
	
	protected Candidate globalBest;
	protected double originalVelocityWeight;
	protected double personalBestVelocityWeight;
	protected double globalBestVelocityWeight;

	public ParticleSwarmOptimisation(MetaHeuristicParameters metaheuristicParameters, 
			Candidate candidate, 
			IProgressMonitor monitor, 
			Recorder recorder) {
		super(metaheuristicParameters, 
				candidate, 
				monitor, 
				recorder);
		
			
		
		this.originalVelocityWeight = metaheuristicParameters.getParameters().get(Config.ORIGINALVELO); 
		this.personalBestVelocityWeight = metaheuristicParameters.getParameters().get(Config.PERSONALBEST); 
		this.globalBestVelocityWeight = metaheuristicParameters.getParameters().get(Config.GLOBALBEST); 
		
	}

	@Override
	public IStatus search() {
		
		this.globalBest = new ParticleSwarmOptimisationCandidate();
		this.globalBest.setFitness(100000.0);
		
		for(Candidate c : candidatePopulation){
			c.updateFitness();
			recorder.addNewCandidate(c, 0);
			if(c.getFitness() < this.globalBest.getFitness())
				this.globalBest = c.copySelf();
			
		}
		
		
		for(int i = 1; i < generationSize; i++){
			for(Candidate c : candidatePopulation){
				
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				
				c.setVelocity(this.globalBest, this.originalVelocityWeight, this.personalBestVelocityWeight, globalBestVelocityWeight);
				c.updateFitness();
				if(c.getFitness() < this.globalBest.getFitness())
					this.globalBest = c.copySelf();
				recorder.addNewCandidate(c, i);
			}
			
		}
		
		
		
		//recorder.stopTimer();
		
		return Status.OK_STATUS;
	}


	

}
