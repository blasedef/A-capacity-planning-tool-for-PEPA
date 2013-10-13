package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;


import java.util.ArrayList;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class BorderedCompositeWithDoubleTextWidget extends BorderedCompositeLabelWidget{
	
	protected TextInputs textInput;
	protected ArrayList<CapacityPlanningWidget> widgetsLeft = new ArrayList<CapacityPlanningWidget>();
	protected ArrayList<CapacityPlanningWidget> widgetsRight = new ArrayList<CapacityPlanningWidget>();
	
	public BorderedCompositeWithDoubleTextWidget(TextInputs textInput,
			IValidationCallback cb,
			Composite container) {
		super(textInput.getKey(), null, null, cb, container, 4);
		
		this.textInput = textInput;
		
		Label labelLeft = new Label(this.composite,SWT.NONE | SWT.BOLD);
		GridData gridDataHeadingLeft = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingLeft.horizontalSpan = 2;
		labelLeft.setLayoutData(gridDataHeadingLeft);
		labelLeft.setText(textInput.getLeftHeading());
		
		Label labelRight = new Label(this.composite,SWT.NONE | SWT.BOLD);
		GridData gridDataHeadingRight = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingRight.horizontalSpan = 2;
		labelRight.setLayoutData(gridDataHeadingRight);
		labelRight.setText(textInput.getRightHeading());
		
		Composite left = new Composite(composite,SWT.NONE);
		GridData leftGridData = new GridData(GridData.FILL_HORIZONTAL);
		leftGridData.horizontalSpan = 2;
		left.setLayoutData(leftGridData);
		GridLayout leftLayout = new GridLayout();
		leftLayout.numColumns = 2;
		left.setLayout(leftLayout);
		
		String[] keys1 = textInput.getMapKeys(false);
		
		for(String key : keys1){
			widgetsLeft.add(new CapacityPlanningConfigurationTextWidget(left,
					key,
					textInput.getMapValueAsString(key, false),
					textInput.getMap(false),
					textInput.getType(key),
					cb));
		}
		
		Composite right = new Composite(composite,SWT.NONE);
		GridData rightGridData = new GridData(GridData.FILL_HORIZONTAL);
		rightGridData.horizontalSpan = 2;
		right.setLayoutData(rightGridData);
		GridLayout rightLayout = new GridLayout();
		rightLayout.numColumns = 2;
		right.setLayout(rightLayout);
		
		String[] keys2 = textInput.getMapKeys(true);
		
		for(String key : keys2){
			widgetsRight.add(new CapacityPlanningConfigurationTextWidget(right,
					key,
					textInput.getMapValueAsString(key, true),
					textInput.getMap(true),
					textInput.getType(key),
					cb));
		}
		
		
	}
	
	@Override
	public boolean isValid() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgetsLeft){
			bool = bool & w.isValid();
		}
		for (CapacityPlanningWidget w : widgetsRight){
			bool = bool & w.isValid();
		}
		if(bool){
			
			bool = textInput.isCorrect(false);
		}
		return bool;
	}
	
}