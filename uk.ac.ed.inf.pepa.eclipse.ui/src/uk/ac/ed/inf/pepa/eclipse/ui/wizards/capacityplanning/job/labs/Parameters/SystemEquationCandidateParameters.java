package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters;


import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class SystemEquationCandidateParameters extends CandidateParameters {
	
	private HashMap<String,Double> minimumPopulation;
	private HashMap<String,Double> maximumPopulation;

	public SystemEquationCandidateParameters(ConfigurationModel configurationModel) {
		super(configurationModel);
		this.setMinimumPopulation(configurationModel.systemEquationPopulationRanges.getLeftMap());
		this.setMaximumPopulation(configurationModel.systemEquationPopulationRanges.getRightMap());
	}
	
	public void setMinimumPopulation(HashMap<String,Double> minimumPopulation) {
		this.minimumPopulation = minimumPopulation;
	}

	public HashMap<String,Double> getMinimumPopulation() {
		return minimumPopulation;
	}

	public void setMaximumPopulation(HashMap<String,Double> maximumPopulation) {
		this.maximumPopulation = maximumPopulation;
	}

	public HashMap<String,Double> getMaximumPopulation() {
		return maximumPopulation;
	}
	
	

}
