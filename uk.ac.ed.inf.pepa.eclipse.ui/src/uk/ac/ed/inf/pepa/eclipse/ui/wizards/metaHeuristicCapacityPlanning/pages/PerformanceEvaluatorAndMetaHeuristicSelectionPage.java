package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.MetaHeuristicCapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Models;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningComboWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class PerformanceEvaluatorAndMetaHeuristicSelectionPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public PerformanceEvaluatorAndMetaHeuristicSelectionPage(String s) {
		
		//copy title upwards
		super(s,"Performance evaluator and Metaheuristic selection page",
				"Choose performance evaluation and Metaheuristic type...");
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback parentCallBack) {
		
		//evaluator choice combo
		widgets.add(new CapacityPlanningComboWidget(container, Models.evaluator, parentCallBack));
		
		//metaheuristic type combo
		widgets.add(new CapacityPlanningComboWidget(container, Models.metaHeuristicType, parentCallBack));
		
		//network type combo
		widgets.add(new CapacityPlanningComboWidget(container, Models.metaHeuristicNetworkType, parentCallBack));
		
		//additional costs page combo
		widgets.add(new CapacityPlanningComboWidget(container, Models.additionalCosts, parentCallBack));
		

	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		((MetaHeuristicCapacityPlanningWizard) getWizard()).updateMHAndODEPages();
	}

}
