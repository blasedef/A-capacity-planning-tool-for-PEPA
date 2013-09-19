package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.*;

public class PerformanceValueTypeAndMetaHeuristicSelectionPage extends WizardPage {
	
	private Composite container;
	

	protected PerformanceValueTypeAndMetaHeuristicSelectionPage(String s) {
		
		//copy title upwards
		super(s);
		
		//set page title
	    setTitle("Performance evaluator type and Metaheuristic selection page");
	    
	    //set description underneath
	    setDescription("Choose performance evaluation and Metaheuristic type...");
	}

	@Override
	public void createControl(Composite parent) {
		//minimum required for a wizard page 1/2
		this.container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		//for PerformanceValueType...
		Label performanceValueTypeLabel = new Label(container,SWT.CENTER);
		performanceValueTypeLabel.setText("Performance value type:");
		final Combo performanceEvaluatorTypeCombo = new Combo(container, SWT.SIMPLE);
		performanceEvaluatorTypeCombo.setItems(PerformanceEvaluationParameters.performanceEvaluationTypes);
		performanceEvaluatorTypeCombo.setText(PerformanceEvaluationParameters.performanceEvaluationTypes[0]);
		
		//Set performance evaluator type
		performanceEvaluatorTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
					PerformanceEvaluationParameters.EVALUATORTYPE = performanceEvaluatorTypeCombo.getSelectionIndex();
			}
		});
		
		//for MetaheuristicType...
		Label metaheuristicTypeLabel = new Label(container,SWT.CENTER);
		metaheuristicTypeLabel.setText("Metaheuristic type:");
		final Combo metaheuristicTypeCombo = new Combo(container, SWT.SIMPLE);
		metaheuristicTypeCombo.setItems(MetaHeuristicTypeParameters.metaHeuristicTypes);
		metaheuristicTypeCombo.setText(MetaHeuristicTypeParameters.metaHeuristicTypes[0]);
		
		//Set first metaheuristic with value
		metaheuristicTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
					MetaHeuristicTypeParameters.metaheuristic2.METAHEURISTICTYPE = metaheuristicTypeCombo.getSelectionIndex();
					MetaHeuristicTypeParameters.metaheuristic2.setFitnessFunctionAttributes(metaheuristicTypeCombo.getSelectionIndex());
			}
		});		
		
		
		//for MetaheuristicNetwork
		Label metaheuristicNetworkLabel = new Label(container,SWT.CENTER);
		metaheuristicNetworkLabel.setText("Metaheuristic type:");
		final Combo metaheuristicNetworkCombo = new Combo(container, SWT.SIMPLE);
		metaheuristicNetworkCombo.setItems(MetaHeuristicTypeParameters.metaHeuristicNetworkTypes);
		metaheuristicNetworkCombo.setText(MetaHeuristicTypeParameters.metaHeuristicNetworkTypes[0]);
		
		//Set metaheuristic network
		metaheuristicNetworkCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
					MetaHeuristicTypeParameters.METAHEURISTICNETWORK = metaheuristicNetworkCombo.getSelectionIndex();
			}
		});
		
		
		//for additional costs
		Label additionalCostsLabel = new Label(container,SWT.CENTER);
		additionalCostsLabel.setText("Metaheuristic type:");
		final Combo additionalCostsCombo = new Combo(container, SWT.SIMPLE);
		additionalCostsCombo.setItems(PerformanceEvaluationParameters.additionalCosts);
		additionalCostsCombo.setText(PerformanceEvaluationParameters.additionalCosts[0]);
		
		//Set additional costs bool
		additionalCostsCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
					PerformanceEvaluationParameters.ADDITIONALCOSTS = additionalCostsCombo.getSelectionIndex();
			}
		});
		
		
		//minimum required for a wizard page 2/2
		setControl(container);
		
	}

}
