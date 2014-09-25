package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.FooterCapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.HeaderCapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.HeaderTargetCapacityPlanningWidget;

public abstract class CapacityPlanningWizardPage extends WizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets;
	protected int fixedWidth = 600;
	
	protected final IValidationCallback parentCallBack = new IValidationCallback() {

		public void validate() {
			completePage();
		}
	};

	public CapacityPlanningWizardPage() {
		super(CPTAPI.getSearchControls().getValue());
		
		this.widgets = new ArrayList<CapacityPlanningWidget>();
		
		setTitle(CPTAPI.getSearchControls().getValue() + ": " + CPTAPI.getEvaluationControls().getValue());
	}
	
	@Override
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		
		Layout layout = new GridLayout(3,false);
		container.setLayout(layout);
		
		GridData data = new GridData();
		data.widthHint = fixedWidth;
		container.setLayoutData(data);
		
		constructPage(this.parentCallBack,container);
		setControl(container);
	}
	
	protected abstract void constructPage(IValidationCallback cb, Composite container);
	
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
	
	public void pad(Composite container){
		Label label = new Label(container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		label.setLayoutData(data);
	}
	
	public Composite center(Composite container){
		
		Composite child = new Composite(container, SWT.NONE );
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		child.setLayoutData(data);
		GridLayout layout = new GridLayout(16,false);
		child.setLayout(layout);
		
		return child;
	}
	
	public Composite centerVertical(Composite container){
		
		Composite child = new Composite(container, SWT.NONE );
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		child.setLayoutData(data);
		GridLayout layout = new GridLayout(16,false);
		child.setLayout(layout);
		
		return child;
	}
	
	public Composite centerScroll(Composite container){
		
		ScrolledComposite sc = new ScrolledComposite(container, SWT.V_SCROLL | SWT.H_SCROLL );
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		data.widthHint = 600;
		sc.setLayoutData(data);
		
		Composite child = new Composite(sc, SWT.NONE );
		data = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		data.widthHint = 600;
		child.setLayoutData(data);
		GridLayout layout = new GridLayout(16,false);
		child.setLayout(layout);
		
		sc.setContent(child);
		sc.setMinSize(600, 2000);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		return child;
	}
	
	public void header(String[] titles, Composite child, int padding){
		new HeaderCapacityPlanningWidget(titles, child, padding, fixedWidth);
	}
	
	public void headerTarget(String[] titles, Composite child){
		new HeaderTargetCapacityPlanningWidget(titles, child, fixedWidth);
	}
	
	public void footer(Composite child){
		new FooterCapacityPlanningWidget(child, fixedWidth);
	}
	
	
	public int getMiddle(){
		return this.fixedWidth - (getPadding() * 2);
	}
	
	public int getPadding(){
		return 25;
	}

}
