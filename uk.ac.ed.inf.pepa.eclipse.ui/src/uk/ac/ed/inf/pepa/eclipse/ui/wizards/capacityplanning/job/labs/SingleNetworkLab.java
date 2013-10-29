package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.SystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public abstract class SingleNetworkLab extends Lab {

	public SingleNetworkLab(ConfigurationModel configurationModel,
			int totalWork,
			HashMap<String, Double> systemEquationPopulationRanges,
			int experiments) {
		super(configurationModel, totalWork, systemEquationPopulationRanges,
				experiments);
	}
	
	public FitnessFunction getSystemEquationFitnessFunction(Recorder recorder){
		
		FitnessFunction fitnessFunction = new SystemEquationFitnessFunction(configurationModel.configPEPA, 
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
