package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters;


import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class LabParameters extends Parameters {
	
	private int experiments;
	private int totalWork;
	private int portionWork;
	private String primaryMetaheuristicType;
	private String secondaryMetaheuristicType; 
	
	public LabParameters(ConfigurationModel configurationModel, boolean root){
		if(root){
			this.setExperiments(configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
			setTotalWork(configurationModel);
			this.portionWork = this.totalWork/configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
		} else {
			this.setExperiments(configurationModel.labParametersCandidateLeaf.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
			setTotalWork(configurationModel);
			this.portionWork = this.totalWork/configurationModel.labParametersCandidateLeaf.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
			setTotalWork(this.totalWork);
			this.setSecondaryMetaheuristicType(configurationModel.secondDropDownListList.get(0).getValue());
		}
		
		this.setPrimaryMetaheuristicType(configurationModel.dropDownListList.get(1).getValue());
		
	}
	
	public void setTotalWork(ConfigurationModel configurationModel){
		
		this.totalWork = 1;
		
		if(!configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			totalWork = configurationModel.labParametersCandidateLeaf.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
			totalWork *= configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap().get(Config.GENERATION_S).intValue();
			totalWork *= 50;
		}
		
		
		//times by estimated search space size
		totalWork *= 1; 
		
		//times by experiments
		totalWork *= configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
		
		
		//worst case scenario, every generation created a new analysis job
		totalWork *= configurationModel.metaheuristicParametersRoot.getLeftMap().get(Config.GENERATION_S).intValue();
		
		
		//worst case scenario, every candidate produces a new analysis job
		totalWork *= configurationModel.metaheuristicParametersRoot.getLeftMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		
	}

	public void setExperiments(int experiments) {
		this.experiments = experiments;
	}

	public int getExperiments() {
		return experiments;
	}
	
	public void setTotalWork(int work){
		this.totalWork = work;
	}
	
	public int getTotalWork(){
		return this.totalWork;
	}
	
	public int getTotalWorkPortion(){
		return this.portionWork;
	}

	public void setPrimaryMetaheuristicType(String primaryMetaheuristicType) {
		this.primaryMetaheuristicType = primaryMetaheuristicType;
	}

	public String getPrimaryMetaheuristicType() {
		return primaryMetaheuristicType;
	}

	public void setSecondaryMetaheuristicType(String secondaryMetaheuristicType) {
		this.secondaryMetaheuristicType = secondaryMetaheuristicType;
	}

	public String getSecondaryMetaheuristicType() {
		return secondaryMetaheuristicType;
	}

}
