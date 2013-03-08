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
	//protected ArrayList<Label> labels;
	protected ArrayList<Text> inputs;
	protected boolean[] validation;
	
	protected TargetSetupPage() {
		
		super("Stochastic Search Optimisation");
		setTitle("Target values setup");
	    setDescription("set target values for each element:");
	    
	}

	@Override
	public void createControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layoutTP = new GridLayout();
		layoutTP.marginWidth = 4;
		layoutTP.numColumns = 4;
		composite.setLayout(layoutTP);
		createTargets(composite);
		setControl(composite);
	}

	private void createTargets(Composite composite) {
		
		this.setErrorMessage(null);
		this.setPageComplete(false);
		
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		//this.labels = new ArrayList<Label>(CapacityPlanningAnalysisParameters.labels.length);
		this.inputs = new ArrayList<Text>(CapacityPlanningAnalysisParameters.labels.length);
		this.validation = new boolean[CapacityPlanningAnalysisParameters.labels.length];
		
		for(int i = 0; i < CapacityPlanningAnalysisParameters.labels.length; i++){
			
			final int j = i;
			
			/* Maximum Agent Population */
			Label tempLabel = new Label(composite, SWT.NONE);
			tempLabel.setText(CapacityPlanningAnalysisParameters.labels[i]);
			tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		    //labels.add(tempLabel);
		    
		    Text tempText = new Text(composite, textStyle);
		    tempText.setLayoutData(createDefaultGridData());
		    tempText.addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
					
					validate(j);
					
				}
			});
		    inputs.add(tempText);
			
		}
	}
	
	protected void validate(int i) {
		//check minimum population
		try{
			double miniPop = Double.valueOf(this.inputs.get(i).getText());
			if(miniPop < 1.0){
				this.validation[i] = false;
			} else {
				this.validation[i] = true;
			}
		} catch (NumberFormatException e) {
			this.validation[i] = false;
		} finally {
			if (!this.validation[i]) {
				setErrorMessage("You must set a target value for each element");
				return;
			}
		}
		
		if(allTargetsValid()){
			this.setPageComplete(true);
		  	setTargetParameters();
		}
	}
	
	protected boolean allTargetsValid(){
		boolean test = true;
		for(boolean v: this.validation){
			test = v && test;
		}
		return test;
	}
	  
	protected void setTargetParameters(){
		for(int i = 0; i < CapacityPlanningAnalysisParameters.labels.length; i++){
			CapacityPlanningAnalysisParameters.targetValues.put(CapacityPlanningAnalysisParameters.labels[i], Double.valueOf(this.inputs.get(i).getText()));
		}
	}
	  

	private GridData createDefaultGridData() {
			/* ...with grabbing horizontal space */
			return new GridData(SWT.FILL, SWT.CENTER, true, false);
		  }
	
}
