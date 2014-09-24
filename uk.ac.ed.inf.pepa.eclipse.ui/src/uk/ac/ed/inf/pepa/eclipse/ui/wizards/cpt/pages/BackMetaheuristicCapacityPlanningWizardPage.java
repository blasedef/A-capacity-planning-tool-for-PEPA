package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeyDoubleValueWidgetForMinAndMax;

public class BackMetaheuristicCapacityPlanningWizardPage extends CapacityPlanningWizardPage {

	public BackMetaheuristicCapacityPlanningWizardPage(String pageName) {
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
		
		String[] keys = CPTAPI.getPSORangeParameterControls().getKeys();
		
		for(int i = 0; i < keys.length; i++){
			String value1 = CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMIN);
			String value2 = CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMAX);
			widgets.add(new KeyDoubleValueWidgetForMinAndMax(cb, container, keys[i],
					value1,
					value2,
					CPTAPI.getPSORangeParameterControls()));
		}

	}

}
