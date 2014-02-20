package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.RecordManager;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.Metaheuristic;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public abstract class Lab {
	
	public int experiments;
	protected RecordManager recordManager;
	protected int totalWork;
	protected ConfigurationModel configurationModel;
	protected HashMap<String,Double> systemEquationPopulationRanges;
	protected IProgressMonitor monitor;
	protected Path resultsFolder;
	
	public Lab(ConfigurationModel configurationModel,
			int totalWork, 
			HashMap<String,Double> systemEquationPopulationRanges,
			int experiments,
			Path resultsFolder) {
		
		this.experiments = experiments;
		this.configurationModel = configurationModel;
		this.totalWork = totalWork;
		this.systemEquationPopulationRanges = systemEquationPopulationRanges;
		this.recordManager = new RecordManager(configurationModel, resultsFolder);
		
		
	}

	public IStatus startExperiments(IProgressMonitor monitor) {
		
		IStatus status;
		this.monitor = monitor;

		try {
			
			monitor.beginTask("Experiment(s) started", this.totalWork);
			
			status = search();
			
			
		} finally {
			monitor.done();
		}	
		
		
		return status;
	}
	
	public abstract Metaheuristic setupLab(IProgressMonitor monitor, HashMap<String,Double> parameters);
	
	public IStatus search(){
		
		IStatus status = Status.OK_STATUS;
		
		HashMap<String,Double> parameters = Tool.copyHashMap(configurationModel.metaheuristicParametersRoot.getLeftMap());
		
		for(int i = 0; i < this.experiments; i++){
			
			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			
			Metaheuristic temp = setupLab(monitor, parameters);
			
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
		
		//temporary output of results
		this.recordManager.outputResults();
		
		//for evaluation, leave alone
		this.recordManager.writeRecordersToDisk();
		
	}
}
