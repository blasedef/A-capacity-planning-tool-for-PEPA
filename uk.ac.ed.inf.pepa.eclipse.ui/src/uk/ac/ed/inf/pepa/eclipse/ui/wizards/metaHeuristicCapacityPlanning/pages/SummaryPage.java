package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;

public class SummaryPage extends MetaHeuristicCapacityPlanningWizardPage {

	public SummaryPage(String s) {
		super(s, "Summary of options", "Options chosen through setup wizard");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void completePage() {
		

	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		Label evaluator = new Label(container,SWT.LEFT);
		Label metaheuristicNetwork = new Label(container,SWT.LEFT);
		Label metaheuristicType = new Label(container,SWT.LEFT);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		
		evaluator.setLayoutData(gridData);
		evaluator.setText(ExperimentConfiguration.evaluator.summary());
		
		metaheuristicNetwork.setLayoutData(gridData);
		metaheuristicNetwork.setText(ExperimentConfiguration.metaHeuristicNetworkType.summary());
		
		GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan = 1;
		
		metaheuristicType.setLayoutData(gridData1);
		boolean hasNetwork = !ExperimentConfiguration.metaHeuristicNetworkType.getValue().equals(ExperimentConfiguration.METAHEURISTICSINGLE_S);
		metaheuristicType.setText(ExperimentConfiguration.metaHeuristicPrimary.summary(hasNetwork));

	}

}
