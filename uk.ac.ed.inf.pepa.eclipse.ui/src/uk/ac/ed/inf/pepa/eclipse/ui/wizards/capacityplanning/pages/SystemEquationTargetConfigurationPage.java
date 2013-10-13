package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithDoubleTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class SystemEquationTargetConfigurationPage extends
		CapacityPlanningWizardPage {
	
	private TextInputs fitness;
	private TextInputs targets;
	private TextInputs systemEquation;

	public SystemEquationTargetConfigurationPage(String s,
			TextInputs fitness, TextInputs targets, TextInputs systemEquation) {
		
		//copy title upwards
		super(s,"Performance target and System Equation population range",
				"Determine the performance targets and population ranges...");
		
		this.fitness = fitness;
		this.targets = targets;
		this.systemEquation = systemEquation;
		
	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		if(!bool){
			setErrorMessage("Invalid Configuration");
		} else {
			setErrorMessage(null);
		}
		setPageComplete(bool);

	}

	@Override
	protected void constructPage(IValidationCallback cb) {
		
		widgets.add(new BorderedCompositeWithTextWidget(this.fitness, parentCallBack, container, false));
		
		widgets.add(new BorderedCompositeWithDoubleTextWidget(this.targets, cb, container));
		
		widgets.add(new BorderedCompositeWithDoubleTextWidget(this.systemEquation, cb, container));

	}

}
