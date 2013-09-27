package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public class MetaHeuristicNetworkType extends Configuration{
	
	public MetaHeuristicNetworkType(String key, String value, String[] options) {
		super(key, value, options);
	}
	
	public String getDescription(){
		if(value.equals(ExperimentConfiguration.METAHEURISTICDRIVEN_S)){
			return "driving";
		} else if (value.equals(ExperimentConfiguration.METAHEURISTICPIPELINE_S)){
			return "primary";
		} else {
			return "single";
		}
	}

	@Override
	public void setOptions(String option) {
		
	}

	public String getDescriptionSecondary() {
		return "secondary";
	}

}