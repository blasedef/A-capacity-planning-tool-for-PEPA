package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;


import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class BorderedCompositeWithTextWidget extends BorderedCompositeLabelWidget{
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	protected TextInputs textInput;
	
	public BorderedCompositeWithTextWidget(TextInputs textInput, IValidationCallback cb,
			  Composite container,
			  boolean side) {
		super(textInput.getKey(), null, null, cb, container, 2);
	
		this.textInput = textInput;
		String[] keys = textInput.getMapKeys(side);
		
		for(String key : keys){
			widgets.add(new CapacityPlanningConfigurationTextWidget(this.composite,
					key,
					textInput.getMapValueAsString(key, side),
					textInput.getMap(side),
					textInput.getType(key),
					cb));
		}
	}
	
	@Override
	public boolean isValid() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		if(bool){
			bool = textInput.isCorrect(true);
		}
		
		return bool;
	}
	
}