package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.KeyDoubleValueWidgetForMinAndMax;

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
		
		container.setLayout(new FillLayout());
		
		String[] keys = CPTAPI.getTargetControl().getKeys();
		
		ScrolledComposite sc = new ScrolledComposite(container, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite child = new Composite(sc, SWT.NONE);
		GridLayout layout = new GridLayout(16,true);
		child.setSize(400,400);
		child.setLayout(layout);
		
		for(int i = 0; i < keys.length; i++){
			String value1 = CPTAPI.getTargetControl().getValue(keys[i], Config.LABTAR);
			String value2 = CPTAPI.getTargetControl().getValue(keys[i], Config.LABWEI);
			widgets.add(new KeyDoubleValueWidgetForMinAndMax(cb, child, keys[i],
					value1,
					value2,
					CPTAPI.getTargetControl()));
		}
		
		
		
		sc.setContent(child);
		sc.setMinSize(400,2500);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
	}

}
