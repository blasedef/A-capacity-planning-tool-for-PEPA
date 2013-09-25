package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Models;

public class TargetConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	public TargetConfigurationPage(String title) {
		
		//copy title upwards
		super(title,null,"nan");
		
	}

	@Override
	public void completePage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		for(String s : Models.oDEConfig.getLabels()){
			(new Label(container,SWT.LEFT)).setText(s);
		}
		
	}
	

}
