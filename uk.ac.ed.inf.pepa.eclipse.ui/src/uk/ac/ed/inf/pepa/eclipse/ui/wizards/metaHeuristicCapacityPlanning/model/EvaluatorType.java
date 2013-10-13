package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public class EvaluatorType extends Configuration{
	
	public EvaluatorType(String key, String value, String[] options){
		super(key, value, options);
	}

	@Override
	public void setOptions(String option) {
	}

	@Override
	public String getDescription() {
		return this.getKey() + ExperimentConfiguration.EVALUATORDESCRIPTION; 
	}
	
}