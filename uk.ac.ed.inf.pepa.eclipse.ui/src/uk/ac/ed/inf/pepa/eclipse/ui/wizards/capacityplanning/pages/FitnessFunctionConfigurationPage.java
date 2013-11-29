package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class FitnessFunctionConfigurationPage extends
		CapacityPlanningWizardPage {
	
	private TextInputs fitness;

	public FitnessFunctionConfigurationPage(String s,
			TextInputs fitness) {
		
		//copy title upwards
		super(s,"Fitness function configuration",
				"Determine the performance targets and fitness function weightings...");
		
		this.fitness = fitness;
		
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

	}

}
