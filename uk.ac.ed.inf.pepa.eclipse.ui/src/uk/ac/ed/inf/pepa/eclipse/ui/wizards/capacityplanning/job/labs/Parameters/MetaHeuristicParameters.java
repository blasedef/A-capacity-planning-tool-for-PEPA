package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.MetaheuristicParameters;

import java.util.HashMap;

public class MetaHeuristicParameters extends Parameters {
	
	private HashMap<String,Double> parameters;
	
	public MetaHeuristicParameters(ConfigurationModel configurationModel, boolean root){
		if(root){
			if(configurationModel.metaheuristicParametersRoot.getLeftMap().containsKey(Config.PERSONALBEST)){
				configurationModel.metaheuristicParametersRoot.getLeftMap().remove(Config.MUTATIONPROBABILITY_S);
			}
			this.setParameters(configurationModel.metaheuristicParametersRoot.getLeftMap());
		} else {
			if(!configurationModel.secondDropDownListList.get(0).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S))
				((MetaheuristicParameters) configurationModel.metaheuristicParametersCandidateLeaf).update(configurationModel.secondDropDownListList.get(0).getValue());
			if(configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap().containsKey(Config.PERSONALBEST)){
				configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap().remove(Config.MUTATIONPROBABILITY_S);
			}
			this.setParameters(configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap());
		}
	}
	
	public MetaHeuristicParameters copySelf(){
		return new MetaHeuristicParameters(this.parameters);
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
