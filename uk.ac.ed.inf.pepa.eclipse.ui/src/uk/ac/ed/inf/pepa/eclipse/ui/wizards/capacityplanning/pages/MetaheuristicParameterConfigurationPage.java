package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithDoubleTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class MetaheuristicParameterConfigurationPage extends CapacityPlanningWizardPage {

	private TextInputs metaheuristicParameters;
	private TextInputs labParameters;
	private TextInputs fitness;
	private boolean chained;
	private boolean pipelined;
	
	public MetaheuristicParameterConfigurationPage(String s, 
			TextInputs metaheuristicParameters,
			TextInputs labParameters,
			TextInputs fitness,
			boolean chained,
			boolean pipelined) {
		
		//copy title upwards
		super(s,"Lab setup page",
				"Set the parameters for the lab and metaheuristic algorithm...");
		
		this.metaheuristicParameters = metaheuristicParameters;
		this.labParameters = labParameters;
		this.fitness = fitness;
		this.chained = chained;
		this.pipelined = pipelined;
	
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
		
		if(!chained){
			notChained();
		} else {
			chained(pipelined);
		}
	}
	
	protected void notChained(){
		
		widgets.add(new BorderedCompositeWithTextWidget(this.labParameters, parentCallBack, container, false));
		
		widgets.add(new BorderedCompositeWithTextWidget(this.metaheuristicParameters, parentCallBack, container, false));
		
	}
	
	protected void chained(boolean pipelined){
		
		if(pipelined){
		
			widgets.add(new BorderedCompositeWithTextWidget(this.labParameters, parentCallBack, container, false));
			
			widgets.add(new BorderedCompositeWithTextWidget(this.fitness, parentCallBack, container, true));
			
			widgets.add(new BorderedCompositeWithTextWidget(this.metaheuristicParameters, parentCallBack, container, false));
			
		} else {
			
			widgets.add(new BorderedCompositeWithTextWidget(this.labParameters, parentCallBack, container, false));
			
			widgets.add(new BorderedCompositeWithTextWidget(this.fitness, parentCallBack, container, true));
			
			widgets.add(new BorderedCompositeWithDoubleTextWidget(this.metaheuristicParameters, parentCallBack, container));
			
		}
		
	}

}
