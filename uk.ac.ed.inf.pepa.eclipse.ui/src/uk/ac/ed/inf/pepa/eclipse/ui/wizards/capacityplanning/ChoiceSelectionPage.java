package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

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


public class ChoiceSelectionPage extends WizardPage {

	/**
	 * Where the user decides what kind of performance value they want to evaluate, and what kind of metaheuristic they would like to use
	 */
	protected ChoiceSelectionPage() {
		super("Stochastic Search Optimisation");
	    setTitle("Choices");
	    setDescription("Choose Performance Requirement evaluation and MetaHeuristic type...");
	}

	/**
	 * Page creation
	 */
	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,true);
		composite.setLayout(layout);
		
		//performance
		Label pvHeading = new Label(composite, SWT.NONE);
		pvHeading.setText("Choose Performance Requirement type:");
		pvHeading.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.createPerformanceButtons(composite);
		
		//metaheuristic
		Label mhHeading = new Label(composite, SWT.NONE);
		mhHeading.setText("Choose Meta Heuristic type:");
		mhHeading.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.createMetaHeuristicButtons(composite);
		setControl(composite);
		
	}
	
	private void createPerformanceButtons(Composite composite){
		
		Composite pvComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		pvComposite.setLayout(layout);
		
		Button[] radios = new Button[2];
		
		radios[0] = new Button(pvComposite, SWT.RADIO);
		radios[0].setText("Throughput...");
		radios[0].setSelection(true);
		radios[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				Widget widget = event.widget;
				if(widget instanceof Button){
					Button button = (Button) widget;
					if(button.getSelection()){
						CPAParameters.performanceRequirementChoice = 0;
					}
				}
			}
		});
		
		radios[1] = new Button(pvComposite, SWT.RADIO);
		radios[1].setText("Average Response Time...");
		radios[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				Widget widget = event.widget;
				if(widget instanceof Button){
					Button button = (Button) widget;
					if(button.getSelection()){
						CPAParameters.performanceRequirementChoice = 1;
					}
				}
			}
		});
		
	}
	
	private void createMetaHeuristicButtons(Composite composite){
		
		Composite mhComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		mhComposite.setLayout(layout);
		
		Button[] radios = new Button[2];
		
		radios[0] = new Button(mhComposite, SWT.RADIO);
		radios[0].setText("Hill Climbing...");
		radios[0].setSelection(true);
		radios[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				Widget widget = event.widget;
				if(widget instanceof Button){
					Button button = (Button) widget;
					if(button.getSelection()){
						CPAParameters.metaHeuristicChoice = 0;
					}
				}
			}
		});
		
		radios[1] = new Button(mhComposite, SWT.RADIO);
		radios[1].setText("Genetic Algorithm...");
		radios[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				Widget widget = event.widget;
				if(widget instanceof Button){
					Button button = (Button) widget;
					if(button.getSelection()){
						CPAParameters.metaHeuristicChoice = 1;
					}
				}
			}
		});
	}

}
