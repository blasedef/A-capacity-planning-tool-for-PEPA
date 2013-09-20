package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public abstract class MetaHeuristicCapacityPlanningWizardPage extends WizardPage {
	
	private Composite container;
	

	protected MetaHeuristicCapacityPlanningWizardPage(String s, String title, String description) {
		
		//copy title upwards
		super(s);
		
		//set page title
	    setTitle(title);
	    
	    //set description underneath
	    setDescription(description);
	}

	@Override
	public void createControl(Composite parent) {
		//minimum required for a wizard page 1/2
		this.container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		completePage(container);
		
		//minimum required for a wizard page 2/2
		setControl(container);
		
	}
	
	protected abstract void completePage(Composite container);
	
	public abstract void updateNextPage();

}
