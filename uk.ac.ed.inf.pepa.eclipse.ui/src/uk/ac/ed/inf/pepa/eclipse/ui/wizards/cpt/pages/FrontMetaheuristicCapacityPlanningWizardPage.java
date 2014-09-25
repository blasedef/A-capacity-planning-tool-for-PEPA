package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeySingleValueWidget;

public class FrontMetaheuristicCapacityPlanningWizardPage extends CapacityPlanningWizardPage {

	public FrontMetaheuristicCapacityPlanningWizardPage(String pageName) {
		super();
		this.setDescription(pageName);
	}

	@Override
	protected void constructPage(IValidationCallback cb, Composite container) {
		
		Control control = CPTAPI.getMHParameterControls();
		String[] keys = control.getKeys();
		
		//left pad
		pad(container);
		
		Composite child = center(container);
		
		String[] titles = {"Setting","Value"};
		
		header(titles,child,4);
		
		
		for(int i = 0; i < keys.length; i++){
			widgets.add(new KeySingleValueWidget(cb, child,keys[i],control.getValue(keys[i]),control));
		}
		
		footer(child);
		
		//Left pad
		pad(container);
		

	}

}
