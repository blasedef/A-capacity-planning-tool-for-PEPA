package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeyDoubleValueWidget;

public class BackMetaheuristicCapacityPlanningWizardPage extends CapacityPlanningWizardPage {

	public BackMetaheuristicCapacityPlanningWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void completePage() {
		
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		setPageComplete(bool);
		System.out.println("gives: " + bool);
	}

	@Override
	protected void constructPage(IValidationCallback cb, Composite container) {
		
		String[] keys = CPTAPI.getPSORangeParameterControls().getKeys();
		
		for(int i = 0; i < keys.length; i++){
			String value1 = CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMIN);
			String value2 = CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMAX);
			widgets.add(new KeyDoubleValueWidget(cb, container, keys[i],
					value1,
					value2,
					CPTAPI.getPSORangeParameterControls()));
		}

	}

}
