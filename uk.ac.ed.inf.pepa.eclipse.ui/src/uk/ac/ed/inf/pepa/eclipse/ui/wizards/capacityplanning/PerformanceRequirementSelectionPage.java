package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.sun.org.apache.regexp.internal.recompile;

public class PerformanceRequirementSelectionPage extends WizardPage {

	private boolean throughputOrAverageReponse;
	
	protected PerformanceRequirementSelectionPage() {
		super("Stochastic Search Optimisation");
	    setTitle("Performance Choice");
	    setDescription("Choose performance requirement...");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		composite.setLayout(layout);
		setControl(composite);
		
		Button radio1 = new Button(composite, SWT.RADIO);
		radio1.setText("Throughput...");
		radio1.setSelection(true);
		radio1.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				Widget widget = event.widget;
				if(widget instanceof Button){
					Button button = (Button) widget;
					if(button.getSelection()){
						throughputOrAverageReponse = false;

					}
				}
			}
		});
		
		Button radio2 = new Button(composite, SWT.RADIO);
		radio2.setText("Average Response time... (doesn't work for now)");
		radio2.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				Widget widget = event.widget;
				if(widget instanceof Button){
					Button button = (Button) widget;
					if(button.getSelection()){
						throughputOrAverageReponse = true;
					}
				}
			}
		});
	}
	
	public boolean getPerformanceRequirement(){
		return throughputOrAverageReponse;
	}

}
