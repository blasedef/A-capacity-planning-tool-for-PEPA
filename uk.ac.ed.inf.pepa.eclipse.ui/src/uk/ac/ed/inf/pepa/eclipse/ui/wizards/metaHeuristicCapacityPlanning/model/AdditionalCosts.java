package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

public class AdditionalCosts extends ModelType{

	private static final String[] additionalCostsList = new String[] {ADDITIONALCOSTSNO_S, ADDITIONALCOSTSYES_S};
	
	public AdditionalCosts(String defaultType) {
		super(defaultType, additionalCostsList, "Additional Costs");
	}

	@Override
	public void setOptions(String option) {
		super.options = additionalCostsList;
		
	}
}