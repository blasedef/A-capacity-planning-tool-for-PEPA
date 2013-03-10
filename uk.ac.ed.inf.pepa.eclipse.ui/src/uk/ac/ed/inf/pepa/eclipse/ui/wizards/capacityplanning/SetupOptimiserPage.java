package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public abstract class SetupOptimiserPage extends WizardPage {
	
	//parameters
	//turn this into an array, and then I don't need to create individual sections for each variable...
	protected ArrayList<Text> inputs;
	protected boolean[] validation;

	protected SetupOptimiserPage() {
		super("Stochastic Search Optimisation");
	}

	@Override
	public void createControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layoutTP = new GridLayout();
		layoutTP.marginWidth = 4;
		layoutTP.numColumns = 4;
		composite.setLayout(layoutTP);
		createInputs(composite);
		setControl(composite);
		
	}
	
	protected abstract void createInputs(Composite composite);

	protected GridData createDefaultGridData() {
		/* ...with grabbing horizontal space */
		return new GridData(SWT.FILL, SWT.CENTER, true, false);
	}
	
	protected void validate(int i) {
		
		this.setErrorMessage(null);
		
		try{
			this.validation[i] = CapacityPlanningAnalysisParameters.testValidation(this.inputs.get(i).getText(), 
					CapacityPlanningAnalysisParameters.mLabelsAndTypes.get(CapacityPlanningAnalysisParameters.mlabels[i]));
		} catch (NumberFormatException e) {
			this.validation[i] = false;
		} finally {
			if(!this.validation[i]){
				setErrorMessage("Incorrect value set on '" + CapacityPlanningAnalysisParameters.mlabels[i] + "' with " + this.inputs.get(i).getText());
			}
			
		}
		
		if(allTargetsValid()){
			this.setPageComplete(true);
		  	updateMetaHeuristicParameters();
		}
	}
	
	protected boolean allTargetsValid(){
		boolean test = true;
		for(boolean v: this.validation){
			test = v && test;
		}
		return test;
	}
	  
	protected abstract void updateMetaHeuristicParameters();

}
