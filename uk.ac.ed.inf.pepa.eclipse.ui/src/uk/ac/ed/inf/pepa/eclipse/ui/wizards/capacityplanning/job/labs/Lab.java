package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.RecordManager;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.Metaheuristic;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Results;

public abstract class Lab {
	
	protected RecordManager recordManager;
	protected LabParameters labParameters;
	protected FitnessFunctionParameters fitnessFunctionParameters;
	protected RecordParameters recordParameters;
	protected CandidateParameters candidateParameters;
	protected IProgressMonitor monitor;
	protected Path resultsFolder;
	protected MetaHeuristicParameters metaheuristicParameters;
	public ArrayList<Results> results;
	
	public Lab(LabParameters labParameters,
			RecordParameters recordParameters,
			MetaHeuristicParameters metaheuristicParameters,
			FitnessFunctionParameters fitnessFunctionParameters,
			CandidateParameters candidateParameters) {
		
		this.labParameters = labParameters;
		this.recordManager = new RecordManager(recordParameters);
		this.metaheuristicParameters = metaheuristicParameters;
		this.fitnessFunctionParameters = fitnessFunctionParameters;
		this.candidateParameters = candidateParameters;
		this.recordParameters = recordParameters;
		
		
		
	}

	public IStatus startExperiments(IProgressMonitor monitor, boolean root) {
		
		IStatus status;
		this.monitor = monitor;

		try {
			
			if(root){
				monitor.beginTask("Experiment(s) started", this.labParameters.getTotalWork());
			
				status = search();
			} else {
				status = search();
			}
			
			
		} finally {
			if(root){
				monitor.done();
			}
		}	
		
		
		return status;
	}
	
	public abstract Metaheuristic setupLab(IProgressMonitor monitor);
	
	public IStatus search(){
		
		IStatus status = Status.OK_STATUS;
		
		
		for(int i = 0; i < this.labParameters.getExperiments(); i++){
			
			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			
			Metaheuristic temp = setupLab(monitor);
			
			Tool.setStartTime();
			
			status = temp.search();
			
			this.recordManager.add(temp.recorder,i);
			
		}
		
		return status;
	}
	
	public void complete(){
		
		//put recorders into a state where they can be evaluated
		this.recordManager.finaliseAll();
		
		//evaluate recorders
		this.recordManager.evaluateAll();
		
		//for evaluation, leave alone
		this.recordManager.writeRecordersToDisk();
		
		//this.recordManager.outputResults();
		this.results = this.recordManager.outputResultsForView();
		
	}
	
	public Double[] getResults(){
		return this.recordManager.getResults();
	}
	
	public ArrayList<Candidate> getTop(){
		return this.recordManager.getTop();
	}

	public MetaHeuristicParameters getMetaheuristicParameters() {
		return this.metaheuristicParameters;
		
	}
}
