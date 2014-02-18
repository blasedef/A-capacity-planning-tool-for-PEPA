package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.Metaheuristic;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public abstract class Lab {
	
	public int experiments;
//	public ArrayList<Metaheuristic> metaheuristics;
	public ArrayList<Recorder> recorders;
	protected int totalWork;
	protected ConfigurationModel configurationModel;
	protected HashMap<String,Double> systemEquationPopulationRanges;
	protected IProgressMonitor monitor;
	
	public Lab(ConfigurationModel configurationModel,
			int totalWork, 
			HashMap<String,Double> systemEquationPopulationRanges,
			int experiments) {
		
//		this.metaheuristics = new ArrayList<Metaheuristic>();
		this.recorders = new ArrayList<Recorder>();
		this.experiments = experiments;
		this.configurationModel = configurationModel;
		this.totalWork = totalWork;
		this.systemEquationPopulationRanges = systemEquationPopulationRanges;
		
	}

	public IStatus startExperiments(IProgressMonitor monitor) {
		
		IStatus status;
		this.monitor = monitor;

		try {
			
			monitor.beginTask("Experiment(s) started", this.totalWork);
			
//			setupLab(monitor);
			
			status = search();
			
		} finally {
			monitor.done();
		}	
		
		
		return status;
	}
	
//	public abstract IStatus setupLab(IProgressMonitor monitor);
	
	public abstract Metaheuristic setupLab(IProgressMonitor monitor, HashMap<String,Double> parameters);
	
	public IStatus search(){
		
		IStatus status = Status.OK_STATUS;
		
		HashMap<String,Double> parameters = Tool.copyHashMap(configurationModel.metaheuristicParameters.getLeftMap());
		
		for(int i = 0; i < this.experiments; i++){
			
			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			
			Metaheuristic temp = setupLab(monitor, parameters);
			
			Tool.setStartTime();
			
			status = temp.search();
			
			recorders.add(temp.recorder);
			
//			metaheuristics.add(temp);
			
//			status = metaheuristics.get(i).search();
		}
		
		return status;
	}
}
