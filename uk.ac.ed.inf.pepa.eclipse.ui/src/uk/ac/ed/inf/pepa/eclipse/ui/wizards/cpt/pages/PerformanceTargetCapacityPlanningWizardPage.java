package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeyDoubleValueWidgetForWeightAndTarget;

public class PerformanceTargetCapacityPlanningWizardPage extends
		CapacityPlanningWizardPage {

	public PerformanceTargetCapacityPlanningWizardPage(String pageName) {
		super();
		this.setDescription(pageName);
	}

	@Override
	protected void constructPage(IValidationCallback cb, Composite container) {
		
		Control control = CPTAPI.getTargetControl();
		String[] keys = control.getKeys();
		
		//left pad
		pad(container);
		
		Composite child = centerScroll(container);
		
		String[] titles = {"Action/State","Target", "Weight"};
		
		headerTarget(titles, child);
		
		for(int i = 0; i < keys.length; i++){
			String value1 = control.getValue(keys[i], Config.LABTAR);
			String value2 = control.getValue(keys[i], Config.LABWEI);
			widgets.add(new KeyDoubleValueWidgetForWeightAndTarget(cb, child, keys[i],
					value1,
					value2,
					control));
		}
		
		//left pad
		pad(container);
	}

}
