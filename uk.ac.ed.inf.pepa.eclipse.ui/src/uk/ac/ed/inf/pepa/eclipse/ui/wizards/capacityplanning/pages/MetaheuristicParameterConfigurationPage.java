package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeLabelWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningComboWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class MetaheuristicParameterConfigurationPage extends CapacityPlanningWizardPage {

	private TextInputs metaheuristicParameters;
	private TextInputs labParameters;
	private ArrayList<DropDownLists> dropDownListsList;
	private boolean chained;
	//private boolean pipelined;
	
	public MetaheuristicParameterConfigurationPage(String s, 
			TextInputs metaheuristicParameters,
			TextInputs labParameters,
			ArrayList<DropDownLists> dropDownListsList,
			boolean chained,
			boolean pipelined) {
		
		//copy title upwards
		super(s,"Lab setup page",
				"Set the parameters for the lab and metaheuristic algorithm...");
		
		this.metaheuristicParameters = metaheuristicParameters;
		this.labParameters = labParameters;
		this.dropDownListsList = dropDownListsList;
		this.chained = chained;
		//this.pipelined = pipelined;
	
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
		
//		if(!chained){
//			notChained();
//		} else {
//			chained(pipelined);
//		}
		
		widgets.add(new BorderedCompositeWithTextWidget(this.labParameters, parentCallBack, container, false));
		
		widgets.add(new BorderedCompositeWithTextWidget(this.metaheuristicParameters, parentCallBack, container, false));
		
		if(chained){
		
			for(DropDownLists d : dropDownListsList){
				new BorderedCompositeLabelWidget(d.getDescription(), null, null, parentCallBack, container, 2);
				super.widgets.add(new CapacityPlanningComboWidget(container, d, parentCallBack));
			}
		}
	}
	
	//keeping this just incase I need to go backwards
	
//	protected void notChained(){
//		
//		widgets.add(new BorderedCompositeWithTextWidget(this.labParameters, parentCallBack, container, false));
//		
//		widgets.add(new BorderedCompositeWithTextWidget(this.metaheuristicParameters, parentCallBack, container, false));
//		
//	}
//	
//	protected void chained(boolean pipelined){
//		
//		widgets.add(new BorderedCompositeWithTextWidget(this.labParameters, parentCallBack, container, false));
//		
//		widgets.add(new BorderedCompositeWithDoubleTextWidget(this.metaheuristicParameters, parentCallBack, container));
//		
//		if(pipelined){
//			
//			
//		} else {
//			
//			
//		}
//		
//		System.out.println(dropDownListsList.size());
//		
//		for(DropDownLists d : dropDownListsList){
//			new BorderedCompositeLabelWidget(d.getDescription(), null, null, parentCallBack, container, 2);
//			super.widgets.add(new CapacityPlanningComboWidget(container, d, parentCallBack));
//		}
//		
//	}

}
