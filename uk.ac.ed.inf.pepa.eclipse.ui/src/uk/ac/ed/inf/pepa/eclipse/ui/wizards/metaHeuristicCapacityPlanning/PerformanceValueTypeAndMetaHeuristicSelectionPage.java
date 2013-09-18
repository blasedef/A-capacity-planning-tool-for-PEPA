package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

public class PerformanceValueTypeAndMetaHeuristicSelectionPage extends WizardPage {
	
	private Composite container;

	protected PerformanceValueTypeAndMetaHeuristicSelectionPage() {
		super("Stochastic Search Optimisation");
	    setTitle("Performance value type and MetaHeuristic selection page");
	    setDescription("Choose performance requirement evaluation and MetaHeuristic type...");
	}

	@Override
	public void createControl(Composite parent) {
		this.container = new Composite(parent, SWT.NONE);
		setControl(container);
		
	}

}
