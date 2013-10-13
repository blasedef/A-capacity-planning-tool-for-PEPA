package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeLabelWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningComboWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class EvaluatorAndMetaheuristicSelectionPage extends CapacityPlanningWizardPage {
	
	private ArrayList<DropDownLists> dropDownListsList;

	public EvaluatorAndMetaheuristicSelectionPage(String s, 
			ArrayList<DropDownLists> dropDownListsList) { 
		
		//copy title upwards
		super(s,"Performance evaluator and Metaheuristic configuration page",
				"Choose performance evaluation and Metaheuristic configuration...");
	
		this.dropDownListsList = dropDownListsList;
	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		setPageComplete(bool);
	}

	@Override
	protected void constructPage(IValidationCallback cb) {
		for(DropDownLists d : dropDownListsList){
			new BorderedCompositeLabelWidget(d.getDescription(), null, null, parentCallBack, container, 2);
			super.widgets.add(new CapacityPlanningComboWidget(container, d, parentCallBack));
		}
	}

}
