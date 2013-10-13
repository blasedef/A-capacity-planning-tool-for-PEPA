package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public class MetaHeuristicNetworkType extends Configuration{
	
	public MetaHeuristicNetworkType(String key, String value, String[] options) {
		super(key, value, options);
	}
	
	public String getDescriptionForPage(){
		if(value.equals(ExperimentConfiguration.NETWORKDRIVEN_S)){
			return "driving";
		} else if (value.equals(ExperimentConfiguration.NETWORKPIPELINE_S)){
			return "primary";
		} else {
			return "single";
		}
	}

	@Override
	public void setOptions(String option) {
		
	}

	@Override
	public String getDescription() {
		return this.getKey() + ExperimentConfiguration.NETWORKDESCRIPTION; 
	}

}