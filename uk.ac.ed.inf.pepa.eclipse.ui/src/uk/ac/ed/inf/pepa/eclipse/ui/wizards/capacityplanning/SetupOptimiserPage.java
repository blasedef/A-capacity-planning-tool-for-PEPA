package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Need to sort out this thing!! Needs to be more like TargetSetupPage and use arrays to make the options...
 * @author twig
 *
 */
public class SetupOptimiserPage extends WizardPage {
	//parameters
	//turn this into an array, and then I don't need to create individual sections for each variable...
	protected ArrayList<Text> inputs = new ArrayList<Text>(CapacityPlanningAnalysisParameters.mlabels.length);
	protected boolean[] validation = new boolean[CapacityPlanningAnalysisParameters.mlabels.length];
	
  
	  public SetupOptimiserPage() {
	    super("Stochastic Search Optimisation");
	    this.setErrorMessage(null);
		this.setPageComplete(false);
	    setTitle("Stochastic Search Optimisation");
	    setDescription("Setting up Search Optimisation");
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
	  
	  public void createInputs(Composite composite){
			
			int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
			
			for(int i = 0; i < CapacityPlanningAnalysisParameters.mlabels.length; i++){
				
				final int j = i;
				
				Label tempLabel = new Label(composite, SWT.NONE);
				tempLabel.setText(CapacityPlanningAnalysisParameters.mlabels[i]);
				tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			    
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
			
			this.setErrorMessage(null);
			this.setPageComplete(false);
			
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
			for(int i = 0; i < CapacityPlanningAnalysisParameters.mlabels.length; i++){
				CapacityPlanningAnalysisParameters.metaheuristicParameters.put(CapacityPlanningAnalysisParameters.mlabels[i]
						, Double.valueOf(this.inputs.get(i).getText()));
			}
		}
		  

		private GridData createDefaultGridData() {
				/* ...with grabbing horizontal space */
				return new GridData(SWT.FILL, SWT.CENTER, true, false);
		}
	  
} 