package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeySingleValueWidget;

public class FrontMetaheuristicCapacityPlanningWizardPage extends CapacityPlanningWizardPage {

	public FrontMetaheuristicCapacityPlanningWizardPage(String pageName) {
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
		
		String[] keys = CPTAPI.getMHParameterControls().getKeys();
		
		for(int i = 0; i < keys.length; i++){
			widgets.add(new KeySingleValueWidget(cb, container,keys[i],CPTAPI.getMHParameterControls().getValue(keys[i]),CPTAPI.getMHParameterControls()));
		}

	}

}
