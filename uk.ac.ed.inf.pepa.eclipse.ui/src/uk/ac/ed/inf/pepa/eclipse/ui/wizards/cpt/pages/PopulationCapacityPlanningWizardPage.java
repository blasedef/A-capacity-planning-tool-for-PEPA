package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeyTripleValueWidgetForMinimumMaximumAndWeight;

public class PopulationCapacityPlanningWizardPage extends
		CapacityPlanningWizardPage {

	public PopulationCapacityPlanningWizardPage(String pageName) {
		super();
		this.setDescription(pageName);
	}


	@Override
	protected void constructPage(IValidationCallback cb, Composite container) {
		
		Control control = CPTAPI.getPopulationControls();
		String[] keys = control.getKeys();
		
		//left pad
		pad(container);
		
		Composite child = centerScroll(container);
		
		String[] titles = {"Component","Minimum","Maximum", "Weight"};
		
		header(titles,child,0);
		
		for(int i = 0; i < keys.length; i++){
			String value1 = control.getValue(keys[i], Config.LABMIN);
			String value2 = control.getValue(keys[i], Config.LABMAX);
			String value3 = control.getValue(keys[i], Config.LABWEI);
			widgets.add(new KeyTripleValueWidgetForMinimumMaximumAndWeight(cb, child, keys[i],
					value1,
					value2,
					value3,
					control));
		}
		
		//left pad
		pad(container);

	}

}
