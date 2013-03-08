package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;


import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;


public class TargetSetupPage extends WizardPage {
	
	protected TableViewer viewer;
	protected Text filterText;

	protected TargetSetupPage() {
		
		super("Stochastic Search Optimisation");
		setTitle("Target values setup");
	    setDescription("set target values for each element:");
	    
	}

	@Override
	public void createControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		createTargets(composite);
		setControl(composite);
	}

	private void createTargets(Composite composite) {
		
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		System.out.println(CapacityPlanningAnalysisParameters.labels);
		ArrayList<Label> labels = new ArrayList<Label>(CapacityPlanningAnalysisParameters.labels.length);
		ArrayList<Text> inputs = new ArrayList<Text>(CapacityPlanningAnalysisParameters.labels.length);
		
		for(int i = 0; i < CapacityPlanningAnalysisParameters.labels.length; i++){
			
			/* Maximum Agent Population */
			Label tempLabel = new Label(composite, textStyle);
			tempLabel.setText(CapacityPlanningAnalysisParameters.labels[i]);
		    labels.add(tempLabel);
		    
		    Text tempText = new Text(composite, textStyle);
		    tempText.setLayoutData(createDefaultGridData());
		    tempText.addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
				}
			});
		    inputs.add(tempText);
			
		}
	}
	
	  private GridData createDefaultGridData() {
			/* ...with grabbing horizontal space */
			return new GridData(SWT.FILL, SWT.CENTER, true, false);
		  }
	
}
