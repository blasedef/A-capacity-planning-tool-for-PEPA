package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;


import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.MetaHeuristicCapacityPlanningWizardPage.GetValue;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.MetaHeuristicCapacityPlanningWizardPage.TestValues;

public class BorderedCompositeWithDoubleTextWidget extends BorderedCompositeLabelWidget{
	
	String type;
	protected ArrayList<CapacityPlanningWidget> widgetsLeft = new ArrayList<CapacityPlanningWidget>();
	protected ArrayList<CapacityPlanningWidget> widgetsRight = new ArrayList<CapacityPlanningWidget>();
	TestValues test;
	
	public BorderedCompositeWithDoubleTextWidget(String heading, 
			String secondaryHeadingLeft, 
			String secondaryHeadingRight, 
			String[] keys1, 
			String[] keys2, 
			Map<String,Number> map1, 
			Map<String,Number> map2, 
			IValidationCallback cb,
			Composite container,
			GetValue getTarget1,
			GetValue getTarget2,
			String type1,
			String type2,
			TestValues test) {
		super(heading, null, null, cb, container, 4);
		
		this.test = test;
		
		Label labelLeft = new Label(this.composite,SWT.NONE | SWT.BOLD);
		GridData gridDataHeadingLeft = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingLeft.horizontalSpan = 2;
		labelLeft.setLayoutData(gridDataHeadingLeft);
		labelLeft.setText(secondaryHeadingLeft);
		
		Label labelRight = new Label(this.composite,SWT.NONE | SWT.BOLD);
		GridData gridDataHeadingRight = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingRight.horizontalSpan = 2;
		labelRight.setLayoutData(gridDataHeadingRight);
		labelRight.setText(secondaryHeadingRight);
		
		Composite left = new Composite(composite,SWT.NONE);
		GridData leftGridData = new GridData(GridData.FILL_HORIZONTAL);
		leftGridData.horizontalSpan = 2;
		left.setLayoutData(leftGridData);
		GridLayout leftLayout = new GridLayout();
		leftLayout.numColumns = 2;
		left.setLayout(leftLayout);
		
		for(String key : keys1){
			if(type1 == null){
				widgetsLeft.add(new CapacityPlanningConfigurationTextWidget(left,
						key,
						getTarget1.getMapValue(key),
						map1,
						ExperimentConfiguration.defaultOptionTypeMap.get(key),
						cb));
			} else {
				widgetsLeft.add(new CapacityPlanningConfigurationTextWidget(left,
						key,
						getTarget1.getMapValue(key),
						map1,
						type2,
						cb));
			}
		}
		
		Composite right = new Composite(composite,SWT.NONE);
		GridData rightGridData = new GridData(GridData.FILL_HORIZONTAL);
		rightGridData.horizontalSpan = 2;
		right.setLayoutData(rightGridData);
		GridLayout rightLayout = new GridLayout();
		rightLayout.numColumns = 2;
		right.setLayout(rightLayout);
		
		for(String key : keys2){
			if(type2 == null){
				widgetsRight.add(new CapacityPlanningConfigurationTextWidget(right,
						key,
						getTarget2.getMapValue(key),
						map2,
						ExperimentConfiguration.defaultOptionTypeMap.get(key),
						cb));
			} else {
				widgetsRight.add(new CapacityPlanningConfigurationTextWidget(right,
						key,
						getTarget2.getMapValue(key),
						map2,
						type2,
						cb));
			}
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
			
			Double[] x = new Double[widgetsLeft.size()];
			Double[] y = new Double[widgetsLeft.size()];
			
			for(int i = 0; i < widgetsLeft.size(); i++){
				x[i] = Double.parseDouble(widgetsLeft.get(i).value);
				y[i] = Double.parseDouble(widgetsRight.get(i).value);
			}
			bool = bool & this.test.test(x,y);
		}
		return bool;
	}
	
	@Override
	public void setValue() {
		//do nothing
	}
	
}