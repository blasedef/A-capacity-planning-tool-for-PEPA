package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeySingleValueWidget;

public class CostFunctionCapacityPlanningWizardPage extends
		CapacityPlanningWizardPage {

	public CostFunctionCapacityPlanningWizardPage(String pageName) {
		super();
		this.setDescription(pageName);
	}

	@Override
	public void completePage() {
		
		String inputError = "";
		
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid().valid;
			String temp = w.isValid().complaint;
			if(temp.length() > 0)
				inputError = temp;
		}
		
		if(inputError.length() > 0){
			setErrorMessage(inputError);
		} else {
			setErrorMessage(null);
		}
		
		setPageComplete(bool);
	}

	@Override
	protected void constructPage(IValidationCallback cb, Composite container) {
		
		String[] keys = CPTAPI.getCostFunctionControls().getKeys();
		
		for(int i = 0; i < keys.length; i++){
			widgets.add(new KeySingleValueWidget(cb, container,keys[i],CPTAPI.getCostFunctionControls().getValue(keys[i]),CPTAPI.getCostFunctionControls()));
		}

	}

}
