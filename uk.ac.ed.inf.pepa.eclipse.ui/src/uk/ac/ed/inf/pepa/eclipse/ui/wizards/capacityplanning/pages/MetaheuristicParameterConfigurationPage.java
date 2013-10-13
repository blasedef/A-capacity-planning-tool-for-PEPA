package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithDoubleTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class MetaheuristicParameterConfigurationPage extends CapacityPlanningWizardPage {

	private TextInputs metaheuristicParameters;
	private TextInputs fitness;
	private boolean asRange;
	
	public MetaheuristicParameterConfigurationPage(String s, 
			TextInputs metaheuristicParameters,
			TextInputs fitness,
			boolean asRange) {
		
		//copy title upwards
		super(s,"Metaheuristic parameter setup page",
				"Set the parameters for the first metaheuristic...");
		
		this.metaheuristicParameters = metaheuristicParameters;
		this.fitness = fitness;
		this.asRange = asRange;
	
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
		
		if(asRange){
			range();
		} else {
			noRange();
		}
	}
	
	protected void noRange(){
		
		widgets.add(new BorderedCompositeWithTextWidget(this.metaheuristicParameters, parentCallBack, container, false));
		
	}
	
	protected void range(){
		
		widgets.add(new BorderedCompositeWithTextWidget(this.fitness, parentCallBack, container, true));
		
		widgets.add(new BorderedCompositeWithDoubleTextWidget(this.metaheuristicParameters, parentCallBack, container));
		
	}

}
