package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;


import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;


public class FitnessFunctionSetupPage extends SetupOptimiserPage {
	
	//GUI related
	protected TableViewer viewer;
	protected Text filterText;
	
	//MetaHeuristic Related
	protected ArrayList<Text[]> inputs;
	protected ArrayList<boolean[]> validation;
	protected int inputParameterLength;
	protected ArrayList<Text[]> inputsNonTarg;
	protected ArrayList<boolean[]> validationNonTarg;
	
	protected FitnessFunctionSetupPage() {
		
		this.setErrorMessage(null);
		this.setPageComplete(false);
		setTitle("Target values setup");
	    setDescription("set target values for each element:");
	    
	}


	protected void createInputs(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		composite.setLayout(layout);
		
		Label heading1 = new Label(composite,SWT.NONE);
		heading1.setText("MetaHeuristic performance target inputs:");

		this.updateArrays();
		
		this.createTargetInputs(composite);
		
		Label heading2 = new Label(composite,SWT.NONE);
		heading2.setText("MetaHeuristic non-Target inputs:");
		
		int rest = CapacityPlanningAnalysisParameters.allLabels.length - CapacityPlanningAnalysisParameters.targetLabels.length;
		
		if(rest > 0){
			this.createNonTargetInputs(composite);
		}
	}
	
	protected void updateArrays(){
		
		//targ
		this.inputs = new ArrayList<Text[]>();
		this.validation = new ArrayList<boolean[]>();
		//non targ
		this.inputsNonTarg = new ArrayList<Text[]>();
		this.validationNonTarg = new ArrayList<boolean[]>();
		
		//setup all targets with parameters
		for(int i = 0; i < CapacityPlanningAnalysisParameters.targetLabels.length; i++){
			this.inputs.add(new Text[CapacityPlanningAnalysisParameters.targetRelatedPerformanceLabels.length]);
		}
		
		//make all parameters false
		for(int i = 0; i < CapacityPlanningAnalysisParameters.targetLabels.length; i++){
			boolean[] temp = new boolean[CapacityPlanningAnalysisParameters.targetRelatedPerformanceLabels.length];
			for(int j = 0; j < CapacityPlanningAnalysisParameters.targetRelatedPerformanceLabels.length; j++){
				temp[j] = false;
			}
			this.validation.add(temp);
		}
		
		//setup all non targets with parameters (arraylist incase of future extension)
		this.inputsNonTarg.add(new Text[CapacityPlanningAnalysisParameters.nonTargetRelatedPerformanceLabels.length]);
		
		//make all parameters false
		boolean[] temp = new boolean[CapacityPlanningAnalysisParameters.nonTargetRelatedPerformanceLabels.length];
		for(int j = 0; j < CapacityPlanningAnalysisParameters.nonTargetRelatedPerformanceLabels.length; j++){
			temp[j] = false;
		}
		this.validationNonTarg.add(temp);
		
	}
	
	protected void createTargetInputs(Composite composite){
		
		Composite targetComposite = new Composite(composite, SWT.NONE | SWT.BORDER);
		GridLayout layout = new GridLayout(1,false);
		targetComposite.setLayout(layout);
		
		for(int i = 0; i < CapacityPlanningAnalysisParameters.targetLabels.length; i++){
			
			final int j = i;
			
			Label tempLabel = new Label(targetComposite, SWT.NONE);
			tempLabel.setText(CapacityPlanningAnalysisParameters.targetLabels[i]);
			tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		    
		    targetInternalLoop(targetComposite, j);
			
		}
		
		
	}
	
	/**
	 * Need to tidy this up!
	 * @param composite
	 */
	protected void createNonTargetInputs(Composite composite){
		
		Composite targetComposite = new Composite(composite, SWT.NONE | SWT.BORDER);
		GridLayout layout = new GridLayout(1,false);
		targetComposite.setLayout(layout);
		
		for(int i = 0; i < 1; i++){
			
			final int j = i;
			
			Label tempLabel = new Label(targetComposite, SWT.NONE);
			tempLabel.setText("Non target specific parameters");
			tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		    
		    targetInternalLoopNon(targetComposite, j);
			
		}
		
		
	}
	
	protected void targetInternalLoop(Composite composite, final int j){
		
		Composite targetComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(4,true);
		targetComposite.setLayout(layout);
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		
		for(int k = 0; k < CapacityPlanningAnalysisParameters.targetRelatedPerformanceLabels.length; k++){
			
			final int l = k;
			
			Label tempLabel = new Label(targetComposite, SWT.NONE);
			tempLabel.setText(CapacityPlanningAnalysisParameters.targetRelatedPerformanceLabels[k]);
			tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			Text tempText = new Text(targetComposite, textStyle);
		    tempText.setLayoutData(createDefaultGridData());
		    
		    if(CapacityPlanningAnalysisParameters.targetLabels.length == 1 &&
		    		CapacityPlanningAnalysisParameters.targetRelatedPerformanceLabels[k].equals("Weight")){
		    	tempText.setText("1.0");
		    	tempText.setEnabled(false);
		    	this.inputs.get(j)[k] = tempText;
		    	this.validation.get(j)[k] = true;
		    }
		    
		    tempText.addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
					
					validateTarget(j,l);
				}
			});
		    this.inputs.get(j)[k] = tempText;
			
		}
		
	}
	
	protected void targetInternalLoopNon(Composite composite, final int j){
		
		Composite targetComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(4,true);
		targetComposite.setLayout(layout);
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		
		for(int k = 0; k < CapacityPlanningAnalysisParameters.nonTargetRelatedPerformanceLabels.length; k++){
			
			final int l = k;
			
			Label tempLabel = new Label(targetComposite, SWT.NONE);
			tempLabel.setText(CapacityPlanningAnalysisParameters.nonTargetRelatedPerformanceLabels[k]);
			tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Text tempText = new Text(targetComposite, textStyle);
		    tempText.setLayoutData(createDefaultGridData());
		    tempText.addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
					
					validateTargetNon(j,l);
				}
			});
		    inputsNonTarg.get(j)[k] = tempText;
			
		}
		
	}
	
	protected void validateTarget(int i, int j) {
		
		this.setErrorMessage(null);
		
		try{
			validation.get(i)[j] = CapacityPlanningAnalysisParameters.testValidation(this.inputs.get(i)[j].getText(),
					CapacityPlanningAnalysisParameters.targetRelatedValidationTypes[j]);
		} catch (NumberFormatException e) {
			validation.get(i)[j] = false;
		} finally {
			if(!this.validation.get(i)[j]){
				setErrorMessage("Incorrect value set on '" + CapacityPlanningAnalysisParameters.targetLabels[i] + "' " 
									+ CapacityPlanningAnalysisParameters.targetRelatedValidationTypes[j]);
			}
			
		}
		
		if(allTargetsValid()){
			((CapacityPlanningWizard) getWizard())
			.addSetupOptimiserPage();
			this.setPageComplete(true);
		  	setTargetParameters();
		  	
		}
	}

	protected void validateTargetNon(int i, int j) {
		
		this.setErrorMessage(null);
	
		try{
			validationNonTarg.get(i)[j] = CapacityPlanningAnalysisParameters.testValidation(this.inputsNonTarg.get(i)[j].getText(),
					CapacityPlanningAnalysisParameters.nonTargetRelatedValidationTypes[j]);
		} catch (NumberFormatException e) {
			validationNonTarg.get(i)[j] = false;
		} finally {
			if(!this.validationNonTarg.get(i)[j]){
				setErrorMessage("Incorrect value set on 'Non Target Specific Parameters' " 
									+ CapacityPlanningAnalysisParameters.nonTargetRelatedValidationTypes[j]);
			}
			
		}
		
		if(allTargetsValid()){
			((CapacityPlanningWizard) getWizard())
			.addSetupOptimiserPage();
			this.setPageComplete(true);
		  	setTargetParameters();
		  	
		}
	}
	
	protected boolean allTargetsValid(){
		boolean test = true;
		for(boolean[] vs: this.validation){
			for(boolean v : vs){
				test = test && v;
			}
		}
		for(boolean[] vs: this.validationNonTarg){
			for(boolean v : vs){
				test = test && v;
			}
		}
		return test;
	}
	  
	protected void setTargetParameters(){
		for(int i = 0; i < CapacityPlanningAnalysisParameters.targetLabels.length; i++){
			CapacityPlanningAnalysisParameters.pvTargetValues.put(CapacityPlanningAnalysisParameters.targetLabels[i], 
					Double.valueOf(this.inputs.get(i)[0].getText()));
			
			CapacityPlanningAnalysisParameters.pvWeightingValues.put(CapacityPlanningAnalysisParameters.targetLabels[i], 
					Double.valueOf(this.inputs.get(i)[1].getText()));
		}
		
		CapacityPlanningAnalysisParameters.metaheuristicParametersMinimumPopulation = Double.valueOf(this.inputsNonTarg.get(0)[0].getText());
		CapacityPlanningAnalysisParameters.metaheuristicParametersMaximumPopulation = Double.valueOf(this.inputsNonTarg.get(0)[1].getText());
	}


	@Override
	protected void updateMetaHeuristicParameters() {
	}
	
}
