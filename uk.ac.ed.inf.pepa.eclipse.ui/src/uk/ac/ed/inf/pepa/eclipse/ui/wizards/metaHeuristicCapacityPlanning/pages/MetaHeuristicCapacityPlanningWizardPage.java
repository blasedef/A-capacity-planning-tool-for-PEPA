package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import org.eclipse.draw2d.GridData;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public abstract class MetaHeuristicCapacityPlanningWizardPage extends WizardPage {
	
	private Composite container;
	
	protected final IValidationCallback parentCallBack = new IValidationCallback() {

		public void validate() {
			completePage();
		}
	};

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
		this.container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 10;
		layout.numColumns = 2;
		container.setLayout(layout);
		
		constructPage(container, this.parentCallBack);
		
		//minimum required for a wizard page 2/2
		setControl(container);
		
	}
	
	protected abstract void constructPage(Composite container, IValidationCallback cb);
	
	public abstract void completePage();
	
}
