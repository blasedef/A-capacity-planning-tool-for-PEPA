package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;

public abstract class CapacityPlanningWizardPage extends WizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets;
	
	protected final IValidationCallback parentCallBack = new IValidationCallback() {

		public void validate() {
			completePage();
		}
	};

	public CapacityPlanningWizardPage() {
		super(CPTAPI.getSearchControls().getValue());
		
		this.widgets = new ArrayList<CapacityPlanningWidget>();
		
		setTitle(CPTAPI.getSearchControls().getValue());
	}
	
	@Override
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(16,true);
		container.setLayout(layout);
		container.setSize(400,400);
		constructPage(this.parentCallBack,container);
		setControl(container);
	}
	
	protected abstract void constructPage(IValidationCallback cb, Composite container);
	
	public abstract void completePage();

}
