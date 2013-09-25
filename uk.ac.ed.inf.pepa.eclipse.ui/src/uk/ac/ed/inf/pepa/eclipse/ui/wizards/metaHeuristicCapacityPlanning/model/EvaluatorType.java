package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public class EvaluatorType extends ModelType{
	
	private static final String[] evaluatorList = new String[] {THROUGHPUT_S,AVERAGERESPONSETIME_S};
	
	public EvaluatorType(String defaultType){
		super(defaultType,evaluatorList, "Evaluator type");
	}

	@Override
	public void setOptions(String option) {
		super.options = evaluatorList;
		
	}
	
}