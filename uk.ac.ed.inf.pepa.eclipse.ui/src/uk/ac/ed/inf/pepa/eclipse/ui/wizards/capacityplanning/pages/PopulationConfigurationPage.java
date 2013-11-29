package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithDoubleTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class PopulationConfigurationPage extends
		CapacityPlanningWizardPage {
	
	private TextInputs systemEquation;
	private TextInputs populationWeights;

	public PopulationConfigurationPage(String s,
			TextInputs systemEquation,
			TextInputs populationWeights) {
		
		//copy title upwards
		super(s,"Fitness function configuration",
				"Determine the performance targets and fitness function weightings...");
		
		this.systemEquation = systemEquation;
		this.populationWeights = populationWeights;
		
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
		
		widgets.add(new BorderedCompositeWithDoubleTextWidget(this.systemEquation, cb, container));
		
		widgets.add(new BorderedCompositeWithTextWidget(this.populationWeights, parentCallBack, container, false));

	}

}
