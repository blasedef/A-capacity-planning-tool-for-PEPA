package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public class MetaHeuristicNetworkType extends ModelType{

	private static final String[] metaHeuristicNetworkList = new String[] {METAHEURISTICSINGLE_S, METAHEURISTICDRIVEN_S, METAHEURISTICPIPELINE_S};
	
	public MetaHeuristicNetworkType(String defaultType) {
		super(defaultType,metaHeuristicNetworkList,"Metaheuristic Network type");
	}

	@Override
	public void setOptions(String option) {
		super.options = metaHeuristicNetworkList;
		
	}

}