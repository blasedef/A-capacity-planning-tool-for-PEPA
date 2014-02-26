package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

import java.util.HashMap;

public class MetaHeuristicParameters extends Parameters {
	
	private HashMap<String,Double> parameters;
	
	public MetaHeuristicParameters(ConfigurationModel configurationModel, boolean root){
		if(root){
			if(configurationModel.metaheuristicParametersRoot.getLeftMap().containsKey(Config.PERSONALBEST)){
				configurationModel.metaheuristicParametersRoot.getLeftMap().remove(Config.MUTATIONPROBABILITY_S);
			}
			this.setParameters(Tool.copyHashMap(configurationModel.metaheuristicParametersRoot.getLeftMap()));
		} else {
			if(configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap().containsKey(Config.PERSONALBEST)){
				configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap().remove(Config.MUTATIONPROBABILITY_S);
			}
			this.setParameters(Tool.copyHashMap(configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap()));
		}
	}
	
	public MetaHeuristicParameters(HashMap<String,Double> parameters){
		this.setParameters(Tool.copyHashMap(parameters));
	}

	public void setParameters(HashMap<String,Double> parameters) {
		this.parameters = Tool.copyHashMap(parameters);
	}

	public HashMap<String,Double> getParameters() {
		return parameters;
	}

}
