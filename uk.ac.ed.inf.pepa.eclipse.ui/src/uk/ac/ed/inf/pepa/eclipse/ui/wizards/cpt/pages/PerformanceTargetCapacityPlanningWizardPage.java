package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeyDoubleValueWidgetForWeightAndTarget;

public class PerformanceTargetCapacityPlanningWizardPage extends
		CapacityPlanningWizardPage {

	public PerformanceTargetCapacityPlanningWizardPage(String pageName) {
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
		
		widgets.add(new KeyDoubleValueWidgetForWeightAndTarget(cb, container, CPTAPI.getTargetControl()));

	}

}
