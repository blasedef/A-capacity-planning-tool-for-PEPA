package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class AverageResponseTimeSetupPage extends WizardPage {

	protected AverageResponseTimeSetupPage() {
	    super("Stochastic Search Optimisation");
	    setTitle("Average Response Time setup");
	    setDescription("Setting up performance requirement... THIS OBVS DON'T WORK");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent,SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;
		setControl(composite);
	}

}
