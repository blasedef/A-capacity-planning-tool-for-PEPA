package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.GeneticAlgorithmSystemEquationCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.SystemEquationRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class SingleNetworkGeneticAlgorithmLab extends SingleNetworkLab {

	public SingleNetworkGeneticAlgorithmLab(
			ConfigurationModel configurationModel, int totalWork,
			HashMap<String, Double> systemEquationPopulationRanges,
			int experiments,
			Path resultsFolder) {
		super(configurationModel, totalWork, systemEquationPopulationRanges,
				experiments, resultsFolder);
	}
	
	@Override
	public Metaheuristic setupLab(IProgressMonitor monitor, HashMap<String,Double> parameters){
		
		Recorder temp = new SystemEquationRecorder(configurationModel);
		
		return ((Metaheuristic) new GeneticAlgorithm(parameters, 
				getSystemEquationCandidate(temp), 
				monitor,
				temp));
		
	}
	
	public Candidate getSystemEquationCandidate(Recorder recorder){
		return (Candidate) new GeneticAlgorithmSystemEquationCandidate(0, 
				getSystemEquationFitnessFunction(recorder),
				Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getLeftMap()),
				Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getRightMap()));
	}

}
