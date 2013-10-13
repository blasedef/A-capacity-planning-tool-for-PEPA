package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.ParticleSwarmOptimisationSystemEquationCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class ParticleSwarmOptimisation extends Metaheuristic {
	
	protected PriorityQueue<Candidate> globalBestQueue;
	protected Candidate globalBest;
	protected double originalVelocityWeight;
	protected double personalBestVelocityWeight;
	protected double globalBestVelocityWeight;

	public ParticleSwarmOptimisation(HashMap<String, Double> parameters, Candidate candidate, IProgressMonitor monitor, Recorder recorder) {
		super(parameters, candidate, monitor, recorder);
		
		this.globalBestQueue = new PriorityQueue<Candidate>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean offer(Candidate e) {
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}

			@Override
			public boolean add(Candidate e) {
				
				
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
		};
		
		this.originalVelocityWeight = parameters.get(Config.ORIGINALVELO); 
		this.personalBestVelocityWeight = parameters.get(Config.PERSONALBEST); 
		this.globalBestVelocityWeight = parameters.get(Config.GLOBALBEST); 
		
	}

	@Override
	public IStatus search() {
		
		recorder.startTimer();
		
		for(Candidate c : candidatePopulation){
			c.updateFitness();
			recorder.addNewCandidate(c, 0);
			this.globalBestQueue.add(c.copySelf());
			
		}
		
		this.globalBest = ((ParticleSwarmOptimisationSystemEquationCandidate) this.globalBestQueue.poll()).copySelf();
		this.globalBestQueue.clear();
		
		for(int i = 1; i < generationSize; i++){
			for(Candidate c : candidatePopulation){
				
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				
				c.setVelocity(this.globalBest, this.originalVelocityWeight, this.personalBestVelocityWeight, globalBestVelocityWeight);
				c.updateFitness();
				this.globalBestQueue.add(c.copySelf());
				recorder.addNewCandidate(c, i);
			}
			
			this.globalBest = ((ParticleSwarmOptimisationSystemEquationCandidate) this.globalBestQueue.poll()).copySelf();
			this.globalBestQueue.clear();
		}
		
		recorder.stopTimer();
		
		return Status.OK_STATUS;
	}


	

}
