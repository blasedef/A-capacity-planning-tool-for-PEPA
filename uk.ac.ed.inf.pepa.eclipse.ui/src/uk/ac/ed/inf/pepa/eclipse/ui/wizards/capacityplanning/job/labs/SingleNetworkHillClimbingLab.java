package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.HillClimbing;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.Metaheuristic;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.SystemEquationRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class SingleNetworkHillClimbingLab extends SingleNetworkLab {

	public SingleNetworkHillClimbingLab(ConfigurationModel configurationModel,
			int totalWork,
			HashMap<String, Double> systemEquationPopulationRanges,
			int experiments,
			Path resultsFolder) {
		super(configurationModel, totalWork, systemEquationPopulationRanges,
				experiments, resultsFolder);
	}
	
	@Override
	public Metaheuristic setupLab(IProgressMonitor monitor, HashMap<String,Double> parameters){
		
		Recorder temp = new SystemEquationRecorder(configurationModel);
		
		return ((Metaheuristic) new HillClimbing(parameters, 
				getSystemEquationCandidate(temp), 
				monitor,
				temp));
		
	}
	
	public Candidate getSystemEquationCandidate(Recorder recorder){
		return (Candidate) new HillClimbingSystemEquationCandidate(0, 
				getSystemEquationFitnessFunction(recorder),
				Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getLeftMap()),
				Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getRightMap()));
	}

}
