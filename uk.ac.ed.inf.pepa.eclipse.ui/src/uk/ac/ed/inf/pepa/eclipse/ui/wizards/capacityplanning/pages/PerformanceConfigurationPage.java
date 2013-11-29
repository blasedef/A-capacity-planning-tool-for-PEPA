package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithDoubleTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class PerformanceConfigurationPage extends
		CapacityPlanningWizardPage {
	
	private TextInputs targets;

	public PerformanceConfigurationPage(String s,
			TextInputs targets) {
		
		//copy title upwards
		super(s,"Fitness function configuration",
				"Determine the performance targets and fitness function weightings...");
		
		this.targets = targets;
		
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
		
		widgets.add(new BorderedCompositeWithDoubleTextWidget(this.targets, cb, container));
		
	}

}
