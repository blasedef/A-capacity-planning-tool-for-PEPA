package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.SystemEquationRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class SingleNetworkParticleSwarmOptimisationLab extends SingleNetworkLab {

	public SingleNetworkParticleSwarmOptimisationLab(
			ConfigurationModel configurationModel, int totalWork,
			HashMap<String, Double> systemEquationPopulationRanges,
			int experiments) {
		super(configurationModel, totalWork, systemEquationPopulationRanges,
				experiments);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Metaheuristic setupLab(IProgressMonitor monitor, HashMap<String,Double> parameters){
		
		Recorder temp = new SystemEquationRecorder();
		
		return ((Metaheuristic) new ParticleSwarmOptimisation(parameters, 
				getSystemEquationCandidate(temp), 
				monitor,
				temp));
		
	}
	
	public Candidate getSystemEquationCandidate(Recorder recorder){
		return (Candidate) new ParticleSwarmOptimisationSystemEquationCandidate(0, 
				getSystemEquationFitnessFunction(recorder),
				Tool.copyHashMap(configurationModel.systemEquationCandidate.getLeftMap()),
				Tool.copyHashMap(configurationModel.systemEquationCandidate.getRightMap()));
	}
	
	@Override
	public FitnessFunction getSystemEquationFitnessFunction(Recorder recorder){
		
		FitnessFunction fitnessFunction = new ParticleSwarmOptimsationSystemEquationFitnessFunction(configurationModel.configPEPA, 
				configurationModel.configODE, 
				Tool.copyHashMap(configurationModel.performanceTargetsAndWeights.getLeftMap()), 
				Tool.copyHashMap(configurationModel.performanceTargetsAndWeights.getRightMap()), 
				Tool.copyHashMap(this.systemEquationPopulationRanges), 
				Tool.copyHashMap(configurationModel.systemEquationFitnessWeights.getLeftMap()), 
				Tool.copyHashMap(configurationModel.populationWeights.getLeftMap()),
				monitor,
				recorder);
		
		return fitnessFunction;
	}

}
