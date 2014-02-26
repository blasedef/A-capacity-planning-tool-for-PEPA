package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Lab;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.SingleNetworkGeneticAlgorithmLab;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.SingleNetworkHillClimbingLab;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.SingleNetworkParticleSwarmOptimisationLab;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;


public class LabFitnessFunction extends FitnessFunction{
	
	private Lab lab;
	private MetaHeuristicParameters metaheuristicParameters;
	private ArrayList<Candidate> top;
	private Double[] fitnessArray; 
	private Recorder recorder;
	private LabParameters labParameters;
	private RecordParameters recordParameters;
	private FitnessFunctionParameters fitnessFunctionParameters;
	private CandidateParameters candidateParameters;
	protected IProgressMonitor monitor;

	public LabFitnessFunction(Recorder recorder, 
			LabParameters labParameters, 
			RecordParameters recordParameters, 
			MetaHeuristicParameters metaheuristicParameters,
			FitnessFunctionParameters fitnessFunctionParameters, 
			CandidateParameters candidateParameters,
			IProgressMonitor monitor){
		
		this.labParameters = labParameters;
		this.recordParameters = recordParameters;
		this.fitnessFunctionParameters = fitnessFunctionParameters;
		this.candidateParameters = candidateParameters;
		this.monitor = monitor;
		
		this.top = new ArrayList<Candidate>();
		this.fitnessArray = new Double[3];
		
		this.metaheuristicParameters = new MetaHeuristicParameters(metaheuristicParameters.getParameters());
		
		if(labParameters.getSecondaryMetaheuristicType().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S)){
			
			this.lab = new SingleNetworkHillClimbingLab(labParameters,
					recordParameters,
					this.metaheuristicParameters,
					fitnessFunctionParameters,
					candidateParameters);
			
		} else if (labParameters.getSecondaryMetaheuristicType().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)) {
			
			this.lab = new SingleNetworkGeneticAlgorithmLab(labParameters,
					recordParameters,
					this.metaheuristicParameters,
					fitnessFunctionParameters,
					candidateParameters);
					
		} else {
			
			this.lab = new SingleNetworkParticleSwarmOptimisationLab(labParameters,
					recordParameters,
					this.metaheuristicParameters,
					fitnessFunctionParameters,
					candidateParameters);
			
		}
		
	}
	
	@Override
	public FitnessFunction copySelf() {
		return new LabFitnessFunction(this.recorder,
				this.labParameters,
				this.recordParameters,
				new MetaHeuristicParameters(metaheuristicParameters.getParameters()),
				this.fitnessFunctionParameters,
				this.candidateParameters,
				this.monitor);
	}

	@Override
	public Double getFitness(HashMap<String, Double> candidate){
		this.lab.getMetaheuristicParameters().setParameters(candidate);
		this.lab.startExperiments(monitor, false);
		this.lab.complete();
		this.top = this.lab.getTop();
		System.out.println("inside labfitnessfunction:");
		System.out.println(this.top.get(top.size() - 1).getName());
		System.out.println(this.top.get(top.size() - 1).getFitness());
		for(Entry<String, Double> entry : candidate.entrySet()){
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		this.fitnessArray = this.lab.getResults();
		return (this.fitnessArray[0] * .8) + (this.fitnessArray[1] * .1) + (this.fitnessArray[2] * .1);
	}
	
	public ArrayList<Candidate> getTop(){
		return this.top;
	}
	
	public HashMap<String,Double> getCandidateMap(){
		return this.metaheuristicParameters.getParameters();
	}
	
	public void setCandidateMap(HashMap<String,Double> parameters){
		this.metaheuristicParameters.setParameters(parameters);
	}
	
}
