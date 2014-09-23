package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;

public abstract class CapacityPlanningWizardPage extends WizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets;
	
	protected final IValidationCallback parentCallBack = new IValidationCallback() {

		public void validate() {
			completePage();
		}
	};

	public CapacityPlanningWizardPage(String pageName) {
		super(pageName);
		
		this.widgets = new ArrayList<CapacityPlanningWidget>();
		
		setTitle(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout(4,false);
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(layout);
		constructPage(this.parentCallBack,container);
		setControl(container);
	}
	
	protected abstract void constructPage(IValidationCallback cb, Composite container);
	
	public abstract void completePage();

}
