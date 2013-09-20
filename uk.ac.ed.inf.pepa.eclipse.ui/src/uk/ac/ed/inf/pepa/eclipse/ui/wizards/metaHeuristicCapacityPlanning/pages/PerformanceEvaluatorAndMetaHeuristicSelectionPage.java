package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.CapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.CapacityPlanningOptionsMap;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.MetaHeuristicCapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningComboWidget;

public class PerformanceEvaluatorAndMetaHeuristicSelectionPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	public PerformanceEvaluatorAndMetaHeuristicSelectionPage(String s) {
		
		//copy title upwards
		super(s,"Performance evaluator and Metaheuristic selection page",
				"Choose performance evaluation and Metaheuristic type...");
	}

	@Override
	protected void completePage(Composite container) {
		
		
		@SuppressWarnings("unused")
		CapacityPlanningComboWidget evaluatorTypeCombo = new CapacityPlanningComboWidget(container, CapacityPlanningOptionsMap.EVALUATORTYPE_S, 
				CapacityPlanningOptionsMap.evaluatorList, this);
		
		
		@SuppressWarnings("unused")
		CapacityPlanningComboWidget metaheuristicTypeCombo = new CapacityPlanningComboWidget(container, CapacityPlanningOptionsMap.METAHEURISTICTYPE1_S, 
				CapacityPlanningOptionsMap.metaHeuristicTypeList, this);
		

		@SuppressWarnings("unused")
		CapacityPlanningComboWidget metaheuristicNetworkCombo = new CapacityPlanningComboWidget(container, CapacityPlanningOptionsMap.METAHEURISTICNETWORKTYPE_S, 
				CapacityPlanningOptionsMap.metaHeuristicNetworkList, this);
		

		@SuppressWarnings("unused")
		CapacityPlanningComboWidget additionalCostsCombo = new CapacityPlanningComboWidget(container, CapacityPlanningOptionsMap.ADDITIONALCOSTS_S, 
				CapacityPlanningOptionsMap.additionalCostsList, this);
		

	}

	@Override
	public void updateNextPage() {
		((MetaHeuristicCapacityPlanningWizard) getWizard()).updateMetaHeuristicConfigurationPage();
	}

}
